package com.intranet.api.intranet.services;

import com.intranet.api.intranet.models.entities.Funcionario;

public interface IFuncionariosService {

     Funcionario findByRut(Integer rut);

     Boolean isJefe(Integer rut, String depto);
     
}
