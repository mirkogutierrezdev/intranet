package com.intranet.api.intranet.repositories;

import java.util.List;

import com.intranet.api.intranet.models.entities.Departamentos;

public interface IDepartamentosRepository {

     Departamentos buscaDepartamento(String depto);

     List<Departamentos> findAll();


}
