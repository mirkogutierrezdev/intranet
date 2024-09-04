package com.intranet.api.intranet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.repositories.IDepartamentosRepository;

@Service
public class DepartamentosServiceImpl implements IDepartamentosService {

    private final IDepartamentosRepository departamentosRepository;

    public DepartamentosServiceImpl(IDepartamentosRepository departamentosRepository) {
        this.departamentosRepository = departamentosRepository;
    }

    @Override
    public Departamentos buscaDepartamento(String depto) {
        return departamentosRepository.buscaDepartamento(depto);
    }

    @Override
    public List<Departamentos> findAll() {
        return departamentosRepository.findAll();
    }

}
