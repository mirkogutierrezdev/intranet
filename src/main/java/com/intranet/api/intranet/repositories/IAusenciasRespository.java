package com.intranet.api.intranet.repositories;

import java.util.List;

import com.intranet.api.intranet.models.entities.Ausencias;

public interface IAusenciasRespository {

    List<Ausencias> buscaAusencias(Integer rut);

}
