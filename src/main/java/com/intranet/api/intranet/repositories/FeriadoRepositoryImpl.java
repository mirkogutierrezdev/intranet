package com.intranet.api.intranet.repositories;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.intranet.api.intranet.models.entities.Feriado;

@Service
public class FeriadoRepositoryImpl implements IFeriadoRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FeriadoRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Feriado> buscaFeriados(Date fechaInicio, Date fechaTermino) {

        // Verificar que las fechas no sean nulas
        if (fechaInicio == null || fechaTermino == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        String sql = "select feriado from FERIADOS " +
                "where FERIADO between :fechaInicio and :fechaTermino";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fechaInicio", fechaInicio);
        params.addValue("fechaTermino", fechaTermino);

        return namedParameterJdbcTemplate.query(sql, params, (rs, _) -> mapRowFeriado(rs));
    }

    private Feriado mapRowFeriado(ResultSet rs) throws SQLException {

        Feriado feriado = new Feriado();
        feriado.setFecha(rs.getDate("feriado"));

        return feriado;
    }

}
