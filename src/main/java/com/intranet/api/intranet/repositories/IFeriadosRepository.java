package com.intranet.api.intranet.repositories;



import java.util.List;

import com.intranet.api.intranet.models.entities.Feriados;

public interface IFeriadosRepository {

    List<Feriados> buscaFeriados(Integer rut, Integer ident);


}
