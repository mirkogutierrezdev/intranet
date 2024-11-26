package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.DiasAdm;

@Repository
public class DiasAdmRepositoryImpl implements IDiasAdmRespository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public DiasAdmRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public DiasAdm consultaSaldo(Integer rut, Integer ident) {

        int currentYear = LocalDate.now().getYear();
        String sql = "ppediasadmin :ident ,:rut,:rut,:anio";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);
        params.addValue("anio", currentYear);
        params.addValue("ident", ident);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, _) -> mapRowToDiasAdm(rs));
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no result is found
        }
    }

    private DiasAdm mapRowToDiasAdm(ResultSet rs) throws SQLException {
        DiasAdm diasAdm = new DiasAdm();

        diasAdm.setMaximo(rs.getInt("maximodiasadm"));
        diasAdm.setUsados(rs.getDouble("diasusados"));
        diasAdm.setSaldo(diasAdm.getMaximo() - diasAdm.getUsados());
        return diasAdm;
    }
}
