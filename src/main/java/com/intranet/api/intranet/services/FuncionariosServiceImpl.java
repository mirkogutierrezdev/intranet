package com.intranet.api.intranet.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.Funcionario;
import com.intranet.api.intranet.repositories.IFuncionariossRepository;

@Repository
public class FuncionariosServiceImpl implements IFuncionariosService {

    @Autowired
    public IFuncionariossRepository repository;

    @Override
    public Funcionario findByRut(Integer rut) {
        return repository.findByRut(rut);
    }

   
}
