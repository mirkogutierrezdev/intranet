package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Funcionario;


@Repository
public class FuncionariosRepositoryImpl implements IFuncionariossRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SuppressWarnings("unused")
    private final JdbcTemplate jdbcTemplate;

    public FuncionariosRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Funcionario findByRut(Integer rut) {
        String sql = "SELECT " +
                     "    personas.rut, " +
                     "    APELLIDOPATERNO, " +
                     "    APELLIDOMATERNO, " +
                     "    nombres, " +
                     "    FECHA_NACIMIENTO, " +
                     "    ident, " +
                     "    (SELECT DENOMINACION FROM DOMINIOS WHERE COD_DOMINIO = 1 AND COD_SISTEMA = 're') AS area, " +
                     "    (SELECT vrut FROM CONTRIBUYENTES z WHERE z.RUT = personas.rut) AS vrut " +
                     "FROM " +
                     "    personas " +
                     "    INNER JOIN REFUNCIONARIOS ON personas.rut = REFUNCIONARIOS.rut " +
                     "WHERE " +
                     "    personas.rut = :rut AND " +
                     "    ident = 1";
    
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);
    
        List<Funcionario> funcionarios = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowToFuncionario(rs));
    
        return funcionarios.isEmpty() ? null : funcionarios.get(0);
    }

    private Funcionario mapRowToFuncionario(ResultSet rs) throws SQLException {

        Funcionario funcionario = new Funcionario();
        funcionario.setRut(rs.getInt("rut"));
        funcionario.setNombres(rs.getString("nombres"));
        funcionario.setApellidopaterno(rs.getString("APELLIDOPATERNO"));
        funcionario.setApellidomaterno(rs.getString("APELLIDOMATERNO"));
        funcionario.setFecha_nac(rs.getDate("FECHA_NACIMIENTO"));
        funcionario.setArea(rs.getString("area"));
        funcionario.setVrut(rs.getString("vrut"));
        return funcionario;
    }
}
