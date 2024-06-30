package com.intranet.api.intranet.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.Feriados;

@Repository
public class FeriadosRepositoryImpl implements IFeriadosRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
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
}
