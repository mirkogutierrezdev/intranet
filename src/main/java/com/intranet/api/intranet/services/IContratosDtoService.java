package com.intranet.api.intranet.services;

import java.util.List;

import com.intranet.api.intranet.dto.ContratosDto;

public interface IContratosDtoService {

    List<ContratosDto> lisContratosDtos(String depto);

}
