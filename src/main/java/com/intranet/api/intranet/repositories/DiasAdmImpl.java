package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.DiasAdm;

import com.intranet.api.intranet.models.entities.mysql.IDiasAdmRespository;

@Repository
public class DiasAdmImpl implements IDiasAdmRespository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public DiasAdm buscDiasAdm(Integer rut) {

        int currentYear = LocalDate.now().getYear();
        String sql = "ppediasadmin 1,:rut,:rut,:currentYear ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);
        params.addValue("currentYear", currentYear);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> mapRowDiadAdm(rs));
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no result is found
        }
    }

    private DiasAdm mapRowDiadAdm(ResultSet rs) throws SQLException {

        DiasAdm diasAdm = new DiasAdm();

        diasAdm.setMaximo(rs.getInt("maximodiasadm"));
        diasAdm.setUsados(rs.getDouble("diasusados"));

        diasAdm.setSaldo(diasAdm.getMaximo()-diasAdm.getUsados());

        return diasAdm;
    }

}
