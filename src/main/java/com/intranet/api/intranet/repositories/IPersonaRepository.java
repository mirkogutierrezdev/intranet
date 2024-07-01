package com.intranet.api.intranet.repositories;

import com.intranet.api.intranet.models.entities.Persona;

public interface IPersonaRepository {

    Persona findByRut(Integer rut);

}
