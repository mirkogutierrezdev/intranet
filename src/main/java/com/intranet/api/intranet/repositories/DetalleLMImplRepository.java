package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.DetalleLM;
import com.intranet.api.intranet.models.entities.LicienciaMedica;

@Repository
public class DetalleLMImplRepository implements IDetalleLMRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DetalleLMImplRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public DetalleLM buscaDetalleLm(LicienciaMedica licienciaMedica) {

        String sql = "exec PLMcalculo :ident, :numlic";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("numlic", licienciaMedica.getNumlic());
        params.addValue("ident", licienciaMedica.getIdent());

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> mapRowToDetalleLM(rs));
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no result is found
        }
    }

    private DetalleLM mapRowToDetalleLM(ResultSet rs) throws SQLException {

        DetalleLM lmDetalle = new DetalleLM();

        lmDetalle.setFechaInicio(rs.getDate("fechaini"));
        lmDetalle.setFechaFin(rs.getDate("fechafin"));
        lmDetalle.setDiasPago(rs.getInt("diasapagar"));
        lmDetalle.setImponiblePromedio(rs.getLong("imponibleprom"));
        lmDetalle.setImposicionesPromedio(rs.getLong("imposicionesprom"));
        lmDetalle.setSaludPromedio(rs.getLong("saludprom"));
        lmDetalle.setLiquidoPromedio(rs.getLong("rentaliqprom"));
        lmDetalle.setSubLiquido(rs.getLong("subliquido"));
        lmDetalle.setSubImposiciones(rs.getLong("subimposiciones"));
        lmDetalle.setSubSalud(rs.getLong("subsalud"));
        lmDetalle.setNumlic(rs.getLong("numlic"));

        return lmDetalle;
    }
}
