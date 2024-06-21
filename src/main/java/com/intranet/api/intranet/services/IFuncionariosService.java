package com.intranet.api.intranet.services;

import java.util.List;

import com.intranet.api.intranet.models.entities.Ausencias;
import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.Funcionario;

public interface IFuncionariosService {

     List<Departamentos> findAll();

     List<Funcionario> listFuncionarios();

     Funcionario findByRut(Integer rut);

     List<Ausencias> listAusencias(Integer rut);

}
