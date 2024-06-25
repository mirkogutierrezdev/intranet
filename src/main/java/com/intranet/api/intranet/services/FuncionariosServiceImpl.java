package com.intranet.api.intranet.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intranet.api.intranet.models.entities.Ausencias;
import com.intranet.api.intranet.models.entities.Contratos;
import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.DiasAdm;
import com.intranet.api.intranet.models.entities.Feriados;
import com.intranet.api.intranet.models.entities.Funcionario;
import com.intranet.api.intranet.models.entities.LicienciaMedica;
import com.intranet.api.intranet.repositories.IAusenciasRespository;
import com.intranet.api.intranet.repositories.IContratosRepository;
import com.intranet.api.intranet.repositories.IDepartamentosRepository;
import com.intranet.api.intranet.repositories.IDiasAdmRespository;
import com.intranet.api.intranet.repositories.IFeriadosRepository;
import com.intranet.api.intranet.repositories.IFuncionariossRepository;
import com.intranet.api.intranet.repositories.ILicenciasMedicasRepository;

@Service
public class FuncionariosServiceImpl implements IFuncionariosService {

    @Autowired
    public IFuncionariossRepository repository;


    @Autowired
    private IAusenciasRespository ausenciasRespository;

    @Autowired
    private IContratosRepository contratosRepository;

    @Autowired
    private IDepartamentosRepository departamentosRepository;

    @Autowired
    private IFeriadosRepository feriadosRepository;

    @Autowired
    private ILicenciasMedicasRepository licenciasMedicasRepository;

    @Autowired
    private IDiasAdmRespository diasAdmRespository;

  



    @Override
    @Transactional
    public Funcionario findByRut(Integer rut) {
       
        Funcionario funcionario = repository.findByRut(rut);

        Contratos contrato = contratosRepository.buscaContrato(rut);

        funcionario.setContrato(contrato);

        Departamentos depto = departamentosRepository.buscaDepartamento(contrato.getDepto());

        funcionario.setDepartamento(depto);

        List<Ausencias> ausencias = ausenciasRespository.buscaAusencias(rut);

        funcionario.setAusencias(ausencias);

        List<Feriados> feriados = feriadosRepository.buscaFeriados(rut);

        funcionario.setFeriados(feriados);

        List<LicienciaMedica> licencias  = licenciasMedicasRepository.buscaLicencias(rut);

        funcionario.setLicencias(licencias);

        DiasAdm diasAdm = diasAdmRespository.consultaSaldo(rut);
        funcionario.setDiasAdm(diasAdm);




        return funcionario;
    }

   
}
