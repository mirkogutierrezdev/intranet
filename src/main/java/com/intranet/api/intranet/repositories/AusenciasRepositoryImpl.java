package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.intranet.api.intranet.models.entities.Ausencias;

@Repository
public class AusenciasRepositoryImpl implements IAusenciasRespository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
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
                "WHERE PEAUSENCIAS.rut = :rut and FECHAINICIO >= (select FECHACORTEFLEGAL from PEINICIALES z where z.IDENT = PEAUSENCIAS.IDENT and z.RUT=PEAUSENCIAS.RUT ) order by fechainicio desc";

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
}
