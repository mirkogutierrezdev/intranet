package com.intranet.api.intranet.repositories;

import java.util.List;


import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.Funcionario;


public interface IFuncionariossRepository {

     List<Departamentos> findAll();

     List<Funcionario> listFuncionarios();

     Funcionario findByRut(Integer rut);

}
