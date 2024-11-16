package com.intranet.api.intranet.repositories;

import java.util.List;

import com.intranet.api.intranet.dto.ContratosDto;
import com.intranet.api.intranet.models.entities.Contratos;

public interface IContratosRepository {

    public Contratos buscaContrato(Integer rut);
    
    public List<ContratosDto> findContratosByDepto(String depto);
}
