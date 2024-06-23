package com.intranet.api.intranet.repositories;

import java.util.List;

import com.intranet.api.intranet.models.entities.LicienciaMedica;

public interface ILicenciasMedicasRepository {

    List<LicienciaMedica> buscaLicencias(Integer rut);

}
