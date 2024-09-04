package com.intranet.api.intranet.services;

import java.util.List;
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

    private final IFuncionariossRepository repository;

    private final IAusenciasRespository ausenciasRespository;

    private final IContratosRepository contratosRepository;

    private final IDepartamentosRepository departamentosRepository;

    private final IFeriadosRepository feriadosRepository;

    private final ILicenciasMedicasRepository licenciasMedicasRepository;

    private final IDiasAdmRespository diasAdmRespository;

    public FuncionariosServiceImpl(IFuncionariossRepository repository, IAusenciasRespository ausenciasRespository,
            IContratosRepository contratosRepository, IDepartamentosRepository departamentosRepository,
            IFeriadosRepository feriadosRepository, ILicenciasMedicasRepository licenciasMedicasRepository,
            IDiasAdmRespository diasAdmRespository) {
        this.repository = repository;
        this.ausenciasRespository = ausenciasRespository;
        this.contratosRepository = contratosRepository;
        this.departamentosRepository = departamentosRepository;
        this.feriadosRepository = feriadosRepository;
        this.licenciasMedicasRepository = licenciasMedicasRepository;
        this.diasAdmRespository = diasAdmRespository;
    }

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

        List<LicienciaMedica> licencias = licenciasMedicasRepository.buscaLicencias(funcionario);

        funcionario.setLicencias(licencias);

        DiasAdm diasAdm = diasAdmRespository.consultaSaldo(rut, funcionario.getContrato().getIdent());

        funcionario.setDiasAdm(diasAdm);

        return funcionario;
    }

    public Boolean isJefe(Integer rut) {

        return repository.esJefe(rut);
    }
}
