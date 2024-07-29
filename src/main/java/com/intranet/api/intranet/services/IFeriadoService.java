package com.intranet.api.intranet.services;

import java.sql.Date;
import java.util.List;

import com.intranet.api.intranet.models.entities.Feriado;

public interface IFeriadoService {

    List<Feriado> buscaFeriados(Date fechaInicio, Date fechaTermino);

}
