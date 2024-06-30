package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.DetalleLM;

@Repository
public class DetalleLMImplRepository implements IDetalleLMRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public DetalleLM buscaDetalleLm(Long numLic) {

        String sql = "exec PLMcalculo 1, :numlic";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("numlic", numLic);

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
