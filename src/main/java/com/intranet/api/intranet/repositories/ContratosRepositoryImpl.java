package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.dto.ContratosDto;
import com.intranet.api.intranet.models.entities.Contratos;

@Repository
public class ContratosRepositoryImpl implements IContratosRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ContratosRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Contratos buscaContrato(Integer rut) {
        String sql = "SELECT " +
                "contratos.ident, " +
                "contratos.rut, " +
                "escala.grado, " +
                "contratos.LINCONTRATO, " +
                "contratos.FECHAINI, " +
                "contratos.fechafin, " +
                "contratos.FECHARESOLCONTR, " +
                "contratos.NUMRESOLCONTR, " +
                "contratomes.depto, " +
                "(SELECT nombretipocontrato " +
                " FROM retiposcontrato z " +
                " WHERE contratomes.IDENT = z.IDENT " +
                "   AND contratomes.CODTIPOCONTRATO = z.CODTIPOCONTRATO) AS tipo_contrato, " +
                "(SELECT NOMBREESCALAFON " +
                " FROM REESCALAFONES x " +
                " WHERE x.IDENT = contratomes.IDENT " +
                "   AND x.CODESCALAFON = contratomes.CODESCALAFON) AS escalafon, " +
                "CASE " +
                "    WHEN EXISTS (SELECT 1 " +
                "                 FROM DEPARTAMENTOS y " +
                "                 WHERE y.DEPTO = contratomes.depto " +
                "                   AND y.rut = contratos.rut) THEN 1 " +
                "    ELSE 0 " +
                "END AS isJefe " +
                "FROM RECONTRATOS AS contratos " +
                "INNER JOIN RECONTRATOMES contratomes " +
                "    ON contratos.IDENT = contratomes.IDENT " +
                "   AND contratos.rut = contratomes.RUT " +
                "   AND contratos.LINCONTRATO = contratomes.LINCONTRATO " +
                "LEFT JOIN RECONTESCALA escala " +
                "    ON contratos.IDENT = escala.IDENT " +
                "   AND contratos.RUT = escala.RUT " +
                "   AND contratomes.ANOREMUN = escala.ANOREMUN " +
                "   AND contratomes.MESREMUN = escala.MESREMUN " +
                "   AND contratomes.LINCONTRATO = escala.LINCONTRATO " +
                "WHERE contratomes.ANOREMUN = YEAR(GETDATE())-1 " +
                "  AND contratomes.MESREMUN = MONTH(DATEADD(month, -1, GETDATE())) " +

                "  AND contratos.FECHAINI <= CONVERT(date, GETDATE(), 104) " +
                "  AND (contratos.fechafin IS NULL OR contratos.fechafin >= '31/12/2024') " +
                "  AND contratos.RUT = :rut;";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rut", rut);

        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, _) -> mapRowToContrato(rs));
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
        contrato.setIsJefe(rs.getInt("isJefe"));
        contrato.setIdent(rs.getInt("ident"));

        return contrato;
    }

    @Override
    public List<ContratosDto> findContratosByDepto(String depto) {

        String sql = "SELECT " +
                "contratos.rut, " +
                "(SELECT nombre FROM vcontribuyentes f WHERE f.rut = contratomes.rut) AS nombre, " +
                "escala.grado, " +
                "contratos.LINCONTRATO, " +
                "contratos.FECHAINI, " +
                "contratos.fechafin, " +
                "contratos.FECHARESOLCONTR, " +
                "contratos.NUMRESOLCONTR, " +
                "contratomes.depto, " +
                "(SELECT nombre_departamento FROM departamentos d WHERE d.depto = contratomes.depto) AS departamento, "
                +
                "(SELECT nombretipocontrato " +
                " FROM retiposcontrato z " +
                " WHERE contratomes.IDENT = z.IDENT " +
                "   AND contratomes.CODTIPOCONTRATO = z.CODTIPOCONTRATO) AS tipo_contrato, " +
                "(SELECT NOMBREESCALAFON " +
                " FROM REESCALAFONES x " +
                " WHERE x.IDENT = contratomes.IDENT " +
                "   AND x.CODESCALAFON = contratomes.CODESCALAFON) AS escalafon, " +
                "CASE " +
                "    WHEN EXISTS (SELECT 1 " +
                "                 FROM DEPARTAMENTOS y " +
                "                 WHERE y.DEPTO = contratomes.depto " +
                "                   AND y.rut = contratos.rut) THEN 1 " +
                "    ELSE 0 " +
                "END AS isJefe " +
                "FROM RECONTRATOS AS contratos " +
                "INNER JOIN RECONTRATOMES contratomes " +
                "    ON contratos.IDENT = contratomes.IDENT " +
                "   AND contratos.rut = contratomes.RUT " +
                "   AND contratos.LINCONTRATO = contratomes.LINCONTRATO " +
                "INNER JOIN RECONTESCALA escala " +
                "    ON contratos.IDENT = escala.IDENT " +
                "   AND contratos.RUT = escala.RUT " +
                "   AND contratomes.ANOREMUN = escala.ANOREMUN " +
                "   AND contratomes.MESREMUN = escala.MESREMUN " +
                "   AND contratomes.LINCONTRATO = escala.LINCONTRATO " +
                "WHERE contratomes.ANOREMUN = YEAR(GETDATE())-1 " +
                "  AND contratomes.MESREMUN = 10 " +
                "  AND contratos.FECHAINI <= CONVERT(date, GETDATE(), 104) " +
                "  AND (contratos.fechafin IS NULL OR contratos.fechafin >= '31/12/2024') " +
                "  AND contratomes.depto LIKE :depto";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("depto", depto + "%");
        return namedParameterJdbcTemplate.query(sql, params, (rs, _) -> mapRowToListContrato(rs));

    }

    private ContratosDto mapRowToListContrato(ResultSet rs) throws SQLException {

        ContratosDto contratosDto = new ContratosDto();

        contratosDto.setRut(rs.getInt("rut"));
        contratosDto.setNombre(rs.getString("nombre"));
        contratosDto.setNombreDepartamento(rs.getString("departamento"));
        contratosDto.setIsJefe(rs.getInt("isJefe"));

        return contratosDto;
    }
}
