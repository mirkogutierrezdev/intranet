package com.intranet.api.intranet.services;

import java.util.List;


import org.springframework.stereotype.Service;

import com.intranet.api.intranet.dto.ContratosDto;
import com.intranet.api.intranet.repositories.IContratosRepository;

@Service
public class ContratosServiceImpl implements IContratosDtoService {

  private final IContratosRepository contratosRepository;

    public ContratosServiceImpl(IContratosRepository contratosRepository) {
        this.contratosRepository = contratosRepository;
    }

    @Override
    public List<ContratosDto> lisContratosDtos(String depto) {
       return contratosRepository.findContratosByDepto(depto);
    }

}
