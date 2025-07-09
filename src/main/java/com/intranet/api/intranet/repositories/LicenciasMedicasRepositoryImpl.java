package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.DetalleLM;
import com.intranet.api.intranet.models.entities.Funcionario;
import com.intranet.api.intranet.models.entities.LicienciaMedica;

@Repository
public class LicenciasMedicasRepositoryImpl implements ILicenciasMedicasRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final IDetalleLMRepository detalleLMRepository;

    public LicenciasMedicasRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            IDetalleLMRepository detalleLMRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.detalleLMRepository = detalleLMRepository;
    }

    @Override
    public List<LicienciaMedica> buscaLicencias(Funcionario funcionario) {
        String sql = "select  " +
                "NUMLIC, " +
                "ident, " +
                "FECHAINI, " +
                "isnull((select NOMBREISAPRE from REISAPRES z where z.CODISAPRE=LMLICENCIAS.CODISAPRE),'Fonasa') as prevision, "+
                "(select nombreafp from REAFP z where z.CODAFP=LMLICENCIAS.CODAFP) as AFP,\r\n" +
                "DIASLIC, " +
                "(select DESCTIPOLIC from LMTIPOLICENCIA y where y.CODTIPOLIC=LMLICENCIAS.CODTIPOLIC) as tipo_licencia, " +
                "FECHAEMISION, " +
                "FECHARECEPCION, " +
                "RUT_PROFESIONAL, " +
                "NOMBRE_PROFESIONAL " +
                "from LMLICENCIAS " +
                "where ident=:ident and rut = :rut and LMLICENCIAS.FECHAINI >= " +
                "(select fechacorteflegal from PEINICIALES z where ident=1 and z.RUT=LMLICENCIAS.RUT)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", funcionario.getRut());
        params.addValue("ident", funcionario.getContrato().getIdent());

        return namedParameterJdbcTemplate.query(sql, params, (rs, _) -> mapRowToLicenciasMedicas(rs));
    }

    private LicienciaMedica mapRowToLicenciasMedicas(ResultSet rs) throws SQLException {

        LicienciaMedica lm = new LicienciaMedica();
        lm.setNumlic(rs.getLong("numlic"));
        lm.setFechaInicio(rs.getDate("fechaini"));
        lm.setPrevision(rs.getString("prevision"));
        lm.setAfp(rs.getString("afp"));
        lm.setDiasLic(rs.getInt("diaslic"));
        lm.setTipoLicencia(rs.getString("tipo_licencia"));
        lm.setFechaEmision(rs.getDate("fechaemision"));
        lm.setFechaRecepcion(rs.getDate("fecharecepcion"));
        lm.setRutMedico(rs.getString("rut_profesional"));
        lm.setNombreProfesional(rs.getString("nombre_profesional"));
        lm.setIdent(rs.getInt("ident"));

        DetalleLM detalleLM = detalleLic(lm);

        lm.setDetalleLM(detalleLM);

        return lm;
    }

    private DetalleLM detalleLic(LicienciaMedica licienciaMedica) {

        return detalleLMRepository.buscaDetalleLm(licienciaMedica);
    }
}
