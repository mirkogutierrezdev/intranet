package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Funcionario;

@Repository
public class FuncionariosRepositoryImpl implements IFuncionariossRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FuncionariosRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Funcionario findByRut(Integer rut) {
        String sql = "SELECT " +
                "  personas.rut, " +
                "  APELLIDOPATERNO, " +
                "  APELLIDOMATERNO, " +
                "  nombres, " +
                "  FECHA_NACIMIENTO, " +
                "  REFUNCIONARIOS.ident, " +
                "  (SELECT DENOMINACION " +
                "   FROM DOMINIOS " +
                "   WHERE COD_DOMINIO = 1 AND COD_SISTEMA = 're') AS area, " +
                "  (SELECT vrut " +
                "   FROM CONTRIBUYENTES z " +
                "   WHERE z.RUT = personas.rut) AS vrut " +
                "FROM personas " +
                "INNER JOIN REFUNCIONARIOS " +
                "  ON personas.rut = REFUNCIONARIOS.rut " +
                "INNER JOIN RECONTRATOS AS contratos " +
                "  ON REFUNCIONARIOS.IDENT = contratos.IDENT " +
                "  AND contratos.rut = REFUNCIONARIOS.rut " +
                "WHERE contratos.FECHAINI <= CONVERT(date, GETDATE(), 104) " +
                "  AND (contratos.fechafin IS NULL OR contratos.fechafin >= CONVERT(date, GETDATE(), 104)) " +
                "  AND personas.rut = :rut";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        List<Funcionario> funcionarios = namedParameterJdbcTemplate.query(sql, params,
                (rs, rowNum) -> mapRowToFuncionario(rs));

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
        funcionario.setIdent(rs.getInt("ident"));
        return funcionario;
    }

    @Override
    public Boolean esJefe(Integer rut) {
        String sql = "SELECT COUNT(*) FROM DEPARTAMENTOS " +
                "INNER JOIN PERSONAS ON DEPARTAMENTOS.RUT = PERSONAS.RUT " +
                "WHERE DEPARTAMENTOS.rut = :rut ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        try {
            // Ejecutar la consulta y obtener el resultado como un entero
            Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);

            // Si count es mayor que 0, la persona es jefe; de lo contrario, no lo es
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false; // Si no hay resultados, la persona no es jefe
        }
    }

}
