package com.intranet.api.intranet.models.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.intranet.api.intranet.repositories.IDepartamentosRepository;

public class DepartamentosRepositoryImpl implements IDepartamentosRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Departamentos buscaDepartamento(String depto) {
    String sql = "SELECT depto, NOMBRE_DEPARTAMENTO, JEFE_DEPARTAMENTO, CARGO_JEFE FROM DEPARTAMENTOS " +
                 "WHERE depto = :depto";

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("depto", depto);

    try {
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> mapRowToDepartamento(rs));
    } catch (EmptyResultDataAccessException e) {
        return null; // Return null if no result is found
    }
}
    private Departamentos mapRowToDepartamento(ResultSet rs) throws SQLException {
        Departamentos depto = new Departamentos();
        depto.setDepto(rs.getString("depto"));
        depto.setNombre_departamento(rs.getString("NOMBRE_DEPARTAMENTO"));
        depto.setJefe_departamento(rs.getString("JEFE_DEPARTAMENTO"));
        depto.setCargo_jefe(rs.getString("CARGO_JEFE"));
        return depto;
    }
}
