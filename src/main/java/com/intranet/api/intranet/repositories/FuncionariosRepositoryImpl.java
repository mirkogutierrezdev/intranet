package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Ausencias;
import com.intranet.api.intranet.models.entities.Contratos;
import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.Feriados;
import com.intranet.api.intranet.models.entities.Funcionario;
import com.intranet.api.intranet.models.entities.LicienciaMedica;

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
        String sql = "SELECT personas.rut, APELLIDOPATERNO, APELLIDOMATERNO, nombres, FECHA_NACIMIENTO, ident, " +
                     "(SELECT DENOMINACION FROM DOMINIOS WHERE COD_DOMINIO = 1 AND COD_SISTEMA = 're') AS area " +
                     "FROM personas " +
                     "INNER JOIN REFUNCIONARIOS ON personas.rut = REFUNCIONARIOS.rut " +
                     "WHERE personas.rut = :rut AND ident = 1";
    
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

        Contratos contrato = buscaContrato(funcionario.getRut());

        funcionario.setContrato(contrato);

        Departamentos depto = buscaDepartamento(contrato.getDepto());

        funcionario.setDepartamento(depto);

        List<Ausencias> ausencias = buscaAusencias(funcionario.getRut());

        funcionario.setAusencias(ausencias);

        List<Feriados> feriado = buscaFeriados(funcionario.getRut());

        funcionario.setFeriados(feriado);

        List<LicienciaMedica> licencias = buscaLicencias(funcionario.getRut());

        funcionario.setLicencias(licencias);

        return funcionario;
    }


    public Contratos buscaContrato(Integer rut) {
        String sql = "SELECT contratos.rut,escala.grado ,contratos.LINCONTRATO, contratos.FECHAINI, contratos.fechafin, " +
                     "contratos.FECHARESOLCONTR, contratos.NUMRESOLCONTR, contratomes.depto, " +
                     "(SELECT nombretipocontrato FROM retiposcontrato z WHERE contratomes.IDENT = z.IDENT AND contratomes.CODTIPOCONTRATO = z.CODTIPOCONTRATO) AS tipo_contrato, " +
                     "(SELECT NOMBREESCALAFON FROM REESCALAFONES x WHERE x.IDENT = contratomes.IDENT AND x.CODESCALAFON = contratomes.CODESCALAFON) AS escalafon " +
                     "FROM RECONTRATOS AS contratos " +
                     "INNER JOIN RECONTRATOMES contratomes ON contratos.IDENT = contratomes.IDENT AND contratos.rut = contratomes.RUT AND contratos.LINCONTRATO = contratomes.LINCONTRATO " +
                     "INNER JOIN RECONTESCALA escala ON contratos.IDENT = escala.IDENT AND contratos.RUT = escala.RUT AND contratomes.ANOREMUN = escala.ANOREMUN AND contratomes.MESREMUN = escala.MESREMUN AND contratomes.LINCONTRATO = escala.LINCONTRATO " +
                     "WHERE contratomes.ANOREMUN = YEAR(GETDATE()) AND contratomes.MESREMUN = MONTH(DATEADD(month, -1, GETDATE())) " +
                     "AND contratomes.LINCONTRATO = (SELECT MAX(lincontrato) FROM recontratomes x WHERE contratomes.IDENT = x.IDENT AND contratomes.rut = x.rut AND contratomes.ANOREMUN = x.ANOREMUN AND contratomes.MESREMUN = x.MESREMUN AND contratomes.LINCONTRATO = x.LINCONTRATO) " +
                     "AND contratos.FECHAINI <= CONVERT(date, GETDATE(), 104) " +
                     "AND (contratos.fechafin IS NULL OR contratos.fechafin >= CONVERT(date, GETDATE(), 104)) " +
                     "AND contratos.RUT = :rut";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> mapRowToContrato(rs));
    }

    private Contratos mapRowToContrato(ResultSet rs) throws SQLException {
        Contratos contrato = new Contratos();
     
        contrato.setLicontrato(rs.getInt("LINCONTRATO"));
        contrato.setFechainicio(rs.getDate("FECHAINI"));
        contrato.setFechatermino(rs.getDate("fechafin"));
        contrato.setDepto(rs.getString("depto"));
        contrato.setEscalafon(rs.getString("escalafon"));
        contrato.setNombrecontrato(rs.getString("tipo_contrato"));
        contrato.setGrado(rs.getInt("grado"));
      
        return contrato;
    }

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

    public List<Feriados> buscaFeriados(Integer rut) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
    
        String sql = "exec ppefuncdiasferiados 1, :rut, :rut, :currentYear, :currentMonth";
    
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);
        params.addValue("currentYear", currentYear);
        params.addValue("currentMonth", currentMonth);
    
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowFeriado(rs));
    }

    private Feriados mapRowFeriado(ResultSet rs) throws SQLException {

        Feriados feriados = new Feriados();
        feriados.setAnio(rs.getInt("ano"));
        feriados.setCorresponde(rs.getInt("corresponde"));
        feriados.setAcumulado(rs.getInt("acumuladoant"));
        feriados.setTotalDias(rs.getInt("totaldias"));
        feriados.setDiasTomados(rs.getInt("diastomados"));
        feriados.setDiasPendientes(rs.getInt("diaspendientes"));
        feriados.setBaseCalculo(rs.getInt("base"));
        
      
        return feriados;
    }

        
    public List<Ausencias> buscaAusencias(Integer rut) {
        String sql = "SELECT " +
                "    ident, DESCTIPOAUSENCIA, rut, LINAUSENCIA, resol, FECHARESOL, FECHAINICIO, FECHATERMINO, " +
                "    PEAUSENCIAS.CODTIPOAUSENCIA, " +
                "    (SELECT COUNT(*) " +
                "     FROM FERIADOS " +
                "     WHERE YEAR(FERIADO) = YEAR(PEAUSENCIAS.FECHAINICIO) " +
                "       AND MONTH(FERIADO) BETWEEN MONTH(PEAUSENCIAS.FECHAINICIO) AND MONTH(PEAUSENCIAS.FECHATERMINO) "
                +
                "       AND DAY(FERIADO) BETWEEN DAY(PEAUSENCIAS.FECHAINICIO) AND DAY(PEAUSENCIAS.FECHATERMINO) " +
                "    ) as feriados_mes " +
                "FROM PEAUSENCIAS " +
                "INNER JOIN PETIPOSAUSENCIA ON PEAUSENCIAS.CODTIPOAUSENCIA = PETIPOSAUSENCIA.CODTIPOAUSENCIA " +
                "WHERE PEAUSENCIAS.rut = :rut order by fechainicio desc";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowToAusencias(rs));
    }

    private Ausencias mapRowToAusencias(ResultSet rs) throws SQLException {
        Ausencias ausencias = new Ausencias();
        ausencias.setIdent(rs.getInt("ident"));
        ausencias.setDescripcion(rs.getString("DESCTIPOAUSENCIA"));

        ausencias.setFecha_inicio(rs.getDate("FECHAINICIO"));
        ausencias.setFecha_termino(rs.getDate("FECHATERMINO"));
        ausencias.setDias_feriados(rs.getInt("feriados_mes"));

        

        ausencias.setDias_ausencia(
                ausencias.calcularDiasHabiles(ausencias.getFecha_inicio(), ausencias.getFecha_termino())
                        - ausencias.getDias_feriados());
        return ausencias;
    }

    public List<LicienciaMedica> buscaLicencias(Integer rut) {
        String sql = "select  \r\n" + //
                        "NUMLIC,\r\n" + //
                        "FECHAINI,\r\n" + //
                        " isnull( (select NOMBREISAPRE from REISAPRES z where z.CODISAPRE=LMLICENCIAS.CODISAPRE),'Fonasa' ) as prevision,\r\n" + //
                        " (select nombreafp from REAFP z where z.CODAFP=LMLICENCIAS.CODAFP) as AFP,\r\n" + //
                        " DIASLIC,\r\n" + //
                        " (select DESCTIPOLIC from LMTIPOLICENCIA y where y.CODTIPOLIC=LMLICENCIAS.CODTIPOLIC) as tipo_licencia,\r\n" + //
                        " FECHAEMISION,\r\n" + //
                        " FECHARECEPCION,\r\n" + //
                        " RUT_PROFESIONAL,\r\n" + //
                        " NOMBRE_PROFESIONAL from LMLICENCIAS\r\n" + //
                        "where ident=1 and rut = :rut ";
               

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> mapRowToLicenciasMedicas(rs));
    }

    private LicienciaMedica mapRowToLicenciasMedicas(ResultSet rs) throws SQLException {

        LicienciaMedica lm = new LicienciaMedica();
        lm.setNumlic(rs.getLong("numlic"));
        lm.setFechaInicio(rs.getDate("fechaini"));
        lm.setPrevision(rs.getString("prevision"));
        lm.setAfp(rs.getString("afp"));
        lm.setDiasLic(rs.getInt("diaslic"));
        lm.setTipoLicencia(rs.getString("tipo_licencia"));
        lm.setFechaEmision(rs.getDate("fechaemision"));
        lm.setFechaRecepcion(rs.getDate("fecharecepcion"));
        lm.setRutMedico(rs.getString("rut_profesional"));
        lm.setNombreProfesional(rs.getString("nombre_profesional"));
      
        return lm;
    }





}
