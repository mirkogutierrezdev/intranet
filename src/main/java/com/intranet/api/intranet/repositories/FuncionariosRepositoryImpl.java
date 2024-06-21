package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Ausencias;
import com.intranet.api.intranet.models.entities.Contratos;
import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.Funcionario;

@Repository
public class FuncionariosRepositoryImpl implements IFuncionariossRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;

    public FuncionariosRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Departamentos> findAll() {
        String sql = "SELECT depto, nombre_departamento, jefe_departamento, cargo_jefe, login FROM departamentos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToDepartamentos(rs));
    }

    private Departamentos mapRowToDepartamentos(ResultSet rs) throws SQLException {
        Departamentos deptos = new Departamentos();
        deptos.setDepto(rs.getString("depto"));
        deptos.setNombre_departamento(rs.getString("nombre_departamento"));
        deptos.setJefe_departamento(rs.getString("jefe_departamento")); // Corregido el nombre del campo

        return deptos;
    }

    @Override
    public List<Funcionario> listFuncionarios() {

        String sql = "WITH CTE_jefesdepto AS ( " +
                "    SELECT departamentos.depto, departamentos.nombre_departamento, NOMBRE_USUARIO " +
                "    FROM departamentos " +
                "    INNER JOIN usuarios ON departamentos.login = usuarios.login " +
                "), " +
                "CTE_contratos AS ( " +
                "    SELECT rut, LINCONTRATO, FECHAINI, FECHAFIN, 'depto' = ( " +
                "        SELECT depto " +
                "        FROM recontratomes AS z " +
                "        WHERE RECONTRATOS.IDENT = z.IDENT AND RECONTRATOS.rut = z.RUT AND z.LINCONTRATO = RECONTRATOS.LINCONTRATO "
                +
                "          AND z.LINCONTRATO = ( " +
                "            SELECT max(lincontrato) " +
                "            FROM RECONTRATOMES x " +
                "            WHERE z.IDENT = z.IDENT AND z.RUT = x.RUT AND z.LINCONTRATO = x.LINCONTRATO " +
                "              AND z.ANOREMUN = x.ANOREMUN AND z.MESREMUN = z.MESREMUN AND z.ANOREMUN = year(getdate()) AND z.MESREMUN = 6) "
                +
                "    ) " +
                "    FROM RECONTRATOS " +
                "    WHERE FECHAINI <= CONVERT(date, getdate(), 104) " +
                "      AND (fechafin IS NULL OR fechafin >= CONVERT(date, getdate(), 104)) " +
                ") " +
                "SELECT CTE_jefesdepto.depto, NOMBRE_DEPARTAMENTO, NOMBRE_USUARIO AS nombre_jefe, CTE_contratos.rut, LINCONTRATO, FECHAINI, FECHAFIN, "
                +
                "       APELLIDOPATERNO, APELLIDOMATERNO, nombres, FECHA_NACIMIENTO " +
                "FROM CTE_jefesdepto " +
                "INNER JOIN CTE_contratos ON CTE_jefesdepto.DEPTO = CTE_contratos.depto " +
                "INNER JOIN personas ON CTE_contratos.RUT = PERSONAS.RUT";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToFuncionarios(rs));
    }

    private Funcionario mapRowToFuncionarios(ResultSet rs) throws SQLException {

        Contratos contrato = new Contratos();
        contrato.setFechainicio(rs.getDate("fechaini"));
        contrato.setFechatermino(rs.getDate("fechafin"));
        contrato.setLicontrato(rs.getInt("lincontrato"));

        Departamentos depto = new Departamentos();

        depto.setDepto(rs.getString("depto"));
        depto.setNombre_departamento(rs.getString("nombre_departamento"));
        depto.setJefe_departamento(rs.getString("nombre_jefe"));

        Funcionario funcionario = new Funcionario();
        funcionario.setRut(rs.getInt("rut"));
        funcionario.setNombres(rs.getString("nombres"));
        funcionario.setApellidopaterno(rs.getString("apellidopaterno"));
        funcionario.setApellidomaterno(rs.getString("apellidomaterno"));
        funcionario.setFecha_nac(rs.getDate("fecha_nacimiento"));
        funcionario.setDepartamento(depto);
        funcionario.setContrato(contrato);

        return funcionario;
    }

    @Override
    public Funcionario findByRut(Integer rut) {
        List<Funcionario> funcionarios = listFuncionarios();
        return funcionarios.stream()
                .filter(funcionario -> rut.equals(funcionario.getRut()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Ausencias> listAusencias(Integer rut) {
        String sql = "SELECT ident, DESCTIPOAUSENCIA, rut, LINAUSENCIA, resol, FECHARESOL, FECHAINICIO, FECHATERMINO " +
                "FROM PEAUSENCIAS " +
                "INNER JOIN PETIPOSAUSENCIA ON PEAUSENCIAS.CODTIPOAUSENCIA = PETIPOSAUSENCIA.CODTIPOAUSENCIA " +
                "WHERE PEAUSENCIAS.rut = :rut ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowToAusencias(rs));
    }

    private Ausencias mapRowToAusencias(ResultSet rs) throws SQLException {
        Ausencias ausencias = new Ausencias();
        ausencias.setIdent(rs.getInt("ident"));
        ausencias.setDescripcion(rs.getString("DESCTIPOAUSENCIA"));
        ausencias.setRut(rs.getInt("rut"));
        ausencias.setLinausencia(rs.getInt("LINAUSENCIA"));
        ausencias.setResol(rs.getString("resol"));
        ausencias.setFecha_resol(rs.getDate("FECHARESOL"));
        ausencias.setFecha_inicio(rs.getDate("FECHAINICIO"));
        ausencias.setFecha_termino(rs.getDate("FECHATERMINO"));
        return ausencias;
    }

}
