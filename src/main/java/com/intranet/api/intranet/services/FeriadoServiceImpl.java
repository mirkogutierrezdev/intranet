package com.intranet.api.intranet.services;



import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intranet.api.intranet.models.entities.Feriado;
import com.intranet.api.intranet.repositories.IFeriadoRepository;

@Service
public class FeriadoServiceImpl implements IFeriadoService {

    @Autowired
    private IFeriadoRepository feriadoRepository;

    @Override
    public List<Feriado> buscaFeriados(Date fechaInicio, Date fechaTermino) {
        return feriadoRepository.buscaFeriados(fechaInicio, fechaTermino);
        
    }

   

    
    



    

}
