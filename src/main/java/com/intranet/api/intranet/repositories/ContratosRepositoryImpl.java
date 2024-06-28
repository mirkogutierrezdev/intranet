package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Contratos;

@Repository
public class ContratosRepositoryImpl implements IContratosRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Contratos buscaContrato(Integer rut) {
        String sql = "SELECT contratos.rut,escala.grado ,contratos.LINCONTRATO, contratos.FECHAINI, contratos.fechafin, "
                +
                "contratos.FECHARESOLCONTR, contratos.NUMRESOLCONTR, contratomes.depto, " +
                "(SELECT nombretipocontrato FROM retiposcontrato z WHERE contratomes.IDENT = z.IDENT AND contratomes.CODTIPOCONTRATO = z.CODTIPOCONTRATO) AS tipo_contrato, "
                +
                "(SELECT NOMBREESCALAFON FROM REESCALAFONES x WHERE x.IDENT = contratomes.IDENT AND x.CODESCALAFON = contratomes.CODESCALAFON) AS escalafon "
                +
                "FROM RECONTRATOS AS contratos " +
                "INNER JOIN RECONTRATOMES contratomes ON contratos.IDENT = contratomes.IDENT AND contratos.rut = contratomes.RUT AND contratos.LINCONTRATO = contratomes.LINCONTRATO "
                +
                "INNER JOIN RECONTESCALA escala ON contratos.IDENT = escala.IDENT AND contratos.RUT = escala.RUT AND contratomes.ANOREMUN = escala.ANOREMUN AND contratomes.MESREMUN = escala.MESREMUN AND contratomes.LINCONTRATO = escala.LINCONTRATO "
                +
                "WHERE contratomes.ANOREMUN = YEAR(GETDATE()) AND contratomes.MESREMUN = MONTH(DATEADD(month, -1, GETDATE())) "
                +
                "AND contratomes.LINCONTRATO = (SELECT MAX(lincontrato) FROM recontratomes x WHERE contratomes.IDENT = x.IDENT AND contratomes.rut = x.rut AND contratomes.ANOREMUN = x.ANOREMUN AND contratomes.MESREMUN = x.MESREMUN AND contratomes.LINCONTRATO = x.LINCONTRATO) "
                +
                "AND contratos.FECHAINI <= CONVERT(date, GETDATE(), 104) " +
                "AND (contratos.fechafin IS NULL OR contratos.fechafin >= CONVERT(date, GETDATE(), 104)) " +
                "AND contratos.RUT = :rut";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> mapRowToContrato(rs));
    }

    private Contratos mapRowToContrato(ResultSet rs) throws SQLException {
        Contratos contrato = new Contratos();

        contrato.setLicontrato(rs.getInt("LINCONTRATO"));
        contrato.setFechainicio(rs.getDate("FECHAINI"));
        contrato.setFechatermino(rs.getDate("fechafin"));
        contrato.setDepto(rs.getString("depto"));
        contrato.setEscalafon(rs.getString("escalafon"));
        contrato.setNombrecontrato(rs.getString("tipo_contrato"));
        contrato.setGrado(rs.getInt("grado"));

        return contrato;
    }
}
