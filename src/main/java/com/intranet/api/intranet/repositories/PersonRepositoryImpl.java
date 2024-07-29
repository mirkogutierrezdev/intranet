package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Persona;

@Repository
public class PersonRepositoryImpl implements IPersonaRepository{

   @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Persona findByRut(Integer rut) {
        String sql = "SELECT rut, nombres, apellidopaterno, apellidomaterno, FECHA_NACIMIENTO, " +
             "(SELECT vrut FROM CONTRIBUYENTES z WHERE z.rut = personas.rut) AS vrut, " +
             "(SELECT fono FROM CONTRIBUYENTES x WHERE x.rut = personas.rut) AS telefono " +
             "FROM personas " +
             "WHERE RUT = :rut";


            
    
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);
    
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> mapRowToPersona(rs));
    }
    

    private Persona mapRowToPersona(ResultSet rs) throws SQLException {
        
        Persona persona = new Persona();
        persona.setRut(rs.getInt("rut"));
        persona.setNombres(rs.getString("nombres"));
        persona.setApellidopaterno(rs.getString("apellidopaterno"));
        persona.setApellidomaterno(rs.getString("apellidomaterno"));
        persona.setFecha_nac(rs.getDate("fecha_nacimiento"));
        persona.setVrut(rs.getString("vrut"));
        persona.setTelefono(rs.getInt("telefono"));


        return persona;
    }

}
