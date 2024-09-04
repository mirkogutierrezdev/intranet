package com.intranet.api.intranet.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intranet.api.intranet.models.entities.Persona;
import com.intranet.api.intranet.repositories.IPersonaRepository;

@Service
public class PersonaServiceImpl implements IPersonaService {

    private final IPersonaRepository personaRepository;


    public PersonaServiceImpl(IPersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }


    @Override
    @Transactional
    public Persona findByRut(Integer rut) {
        return personaRepository.findByRut(rut);
      
    }

}
