package com.intranet.api.intranet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intranet.api.intranet.models.entities.Persona;
import com.intranet.api.intranet.repositories.IPersonaRepository;

@Service
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    @Transactional
    public Persona findByRut(Integer rut) {
        return personaRepository.findByRut(rut);
      
    }

}
