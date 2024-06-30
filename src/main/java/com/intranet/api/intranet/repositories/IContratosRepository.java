package com.intranet.api.intranet.repositories;

import com.intranet.api.intranet.models.entities.Contratos;

public interface IContratosRepository {

    public Contratos buscaContrato(Integer rut);
}
