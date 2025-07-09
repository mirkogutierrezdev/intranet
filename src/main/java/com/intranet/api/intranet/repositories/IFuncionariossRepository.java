package com.intranet.api.intranet.repositories;

import com.intranet.api.intranet.models.entities.Funcionario;

public interface IFuncionariossRepository {

     Funcionario findByRut(Integer rut);

     Boolean esJefe(Integer rut, String depto);

    

}