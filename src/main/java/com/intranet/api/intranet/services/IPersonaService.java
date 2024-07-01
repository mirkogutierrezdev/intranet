package com.intranet.api.intranet.services;

import com.intranet.api.intranet.models.entities.Persona;

public interface IPersonaService {

     Persona findByRut(Integer rut);

}
