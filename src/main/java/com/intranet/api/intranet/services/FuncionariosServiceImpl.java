package com.intranet.api.intranet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.intranet.api.intranet.models.entities.Ausencias;
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

    @Override
    public List<Ausencias> listAusencias(Integer rut) {
        return repository.listAusencias(rut);
    }
}
