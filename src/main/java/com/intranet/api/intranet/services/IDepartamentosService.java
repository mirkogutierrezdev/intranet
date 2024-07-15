package com.intranet.api.intranet.services;

import java.util.List;

import com.intranet.api.intranet.models.entities.Departamentos;

public interface IDepartamentosService {

     Departamentos buscaDepartamento(String depto);

     List<Departamentos> findAll();

}
