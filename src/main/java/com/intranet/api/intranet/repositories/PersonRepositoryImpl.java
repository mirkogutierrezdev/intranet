package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Persona;

@Repository
public class PersonRepositoryImpl implements IPersonaRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Persona findByRut(Integer rut) {
        String sql = "SELECT rut, nombres, apellidopaterno, apellidomaterno, FECHA_NACIMIENTO, " +
                "(SELECT vrut FROM CONTRIBUYENTES z WHERE z.rut = personas.rut) AS vrut, " +
                "(SELECT email FROM CONTRIBUYENTES y WHERE y.rut = personas.rut) as email, "+
                "(SELECT fono FROM CONTRIBUYENTES y WHERE y.rut = personas.rut) as fono "+
                "FROM personas " +
                "WHERE RUT = :rut";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, _) -> mapRowToPersona(rs));
    }

    private Persona mapRowToPersona(ResultSet rs) throws SQLException {

        Persona persona = new Persona();
        persona.setRut(rs.getInt("rut"));
        persona.setNombres(rs.getString("nombres"));
        persona.setApellidopaterno(rs.getString("apellidopaterno"));
        persona.setApellidomaterno(rs.getString("apellidomaterno"));
        persona.setFechaNac(rs.getDate("fecha_nacimiento"));
        persona.setVrut(rs.getString("vrut"));
        persona.setEmail(rs.getString("email"));
        persona.setTelefono(rs.getString("fono"));

        return persona;
    }

}
