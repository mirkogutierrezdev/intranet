package com.intranet.api.intranet.services;

import org.springframework.stereotype.Service;

import com.intranet.api.intranet.models.entities.Contratos;
import com.intranet.api.intranet.repositories.IContratosRepository;

@Service
public class ContratoService {

    private final IContratosRepository contratosRepository;

    public ContratoService(IContratosRepository contratosRepository){
        this.contratosRepository = contratosRepository;
    }

    public Contratos findContratosByRut(Integer rut){

        return contratosRepository.buscaContrato(rut);
    }

}
