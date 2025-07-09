package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.Departamentos;

@Repository
public class DepartamentosRepositoryImpl implements IDepartamentosRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartamentosRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Departamentos buscaDepartamento(String depto) {
        String sql = "SELECT depto, NOMBRE_DEPARTAMENTO, JEFE_DEPARTAMENTO, CARGO_JEFE, RUT FROM DEPARTAMENTOS " +
                "WHERE depto = :depto";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("depto", depto);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, _) -> mapRowToDepartamento(rs));
        } catch (EmptyResultDataAccessException _) {
            return null; // Return null if no result is found
        }
    }

    private Departamentos mapRowToDepartamento(ResultSet rs) throws SQLException {
        Departamentos depto = new Departamentos();
        depto.setDepto(rs.getString("depto"));
        depto.setNombreDepartamento(rs.getString("NOMBRE_DEPARTAMENTO"));
        depto.setJefeDepartamento(rs.getString("JEFE_DEPARTAMENTO"));
        depto.setCargoJefe(rs.getString("CARGO_JEFE"));
        depto.setRutJefe(rs.getString("rut"));
        return depto;
    }

    @Override
    public List<Departamentos> findAll() {

        String sql = "SELECT depto, NOMBRE_DEPARTAMENTO, JEFE_DEPARTAMENTO, CARGO_JEFE,RUT FROM DEPARTAMENTOS ";

        try {
            return namedParameterJdbcTemplate.query(sql, (rs, _) -> mapRowToDepartamento(rs));
        } catch (EmptyResultDataAccessException _) {
            return Collections.emptyList(); // Return null if no result is found
        }

    }

}
