package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.DetalleLM;
import com.intranet.api.intranet.models.entities.LicienciaMedica;

@Repository
public class LicenciasMedicasRepositoryImpl implements ILicenciasMedicasRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private IDetalleLMRepository detalleLMRepository;


    @Override
    public List<LicienciaMedica> buscaLicencias(Integer rut) {
        String sql = "select  \r\n" +
            "NUMLIC,\r\n" +
            "FECHAINI,\r\n" +
            "isnull((select NOMBREISAPRE from REISAPRES z where z.CODISAPRE=LMLICENCIAS.CODISAPRE),'Fonasa') as prevision,\r\n" +
            "(select nombreafp from REAFP z where z.CODAFP=LMLICENCIAS.CODAFP) as AFP,\r\n" +
            "DIASLIC,\r\n" +
            "(select DESCTIPOLIC from LMTIPOLICENCIA y where y.CODTIPOLIC=LMLICENCIAS.CODTIPOLIC) as tipo_licencia,\r\n" +
            "FECHAEMISION,\r\n" +
            "FECHARECEPCION,\r\n" +
            "RUT_PROFESIONAL,\r\n" +
            "NOMBRE_PROFESIONAL\r\n" +
            "from LMLICENCIAS\r\n" +
            "where ident=1 and rut = :rut and LMLICENCIAS.FECHAINI >=\r\n" +
            "(select fechacorteflegal from PEINICIALES z where ident=1 and z.RUT=LMLICENCIAS.RUT)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowToLicenciasMedicas(rs));
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

        lm.setDetalleLM(detalleLic(lm));




        return lm;
    }

    private DetalleLM detalleLic(LicienciaMedica licienciaMedica){

        DetalleLM detalle = detalleLMRepository.buscaDetalleLm(licienciaMedica.getNumlic());

        return detalle;
    }
}
