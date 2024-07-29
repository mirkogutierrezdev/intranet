package com.intranet.api.intranet.repositories;

import java.sql.Date;
import java.util.List;

import com.intranet.api.intranet.models.entities.Feriado;

public interface IFeriadoRepository {

    List<Feriado> buscaFeriados(Date fechaInicio, Date fechaTermino);

}
