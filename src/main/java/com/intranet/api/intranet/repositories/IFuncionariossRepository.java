package com.intranet.api.intranet.repositories;

import java.util.List;

import com.intranet.api.intranet.models.entities.Ausencias;

import com.intranet.api.intranet.models.entities.Funcionario;


public interface IFuncionariossRepository {

 
     Funcionario findByRut(Integer rut);

   

     List<Ausencias> listAusencias(Integer rut);

}
