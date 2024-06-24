package com.intranet.api.intranet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intranet.api.intranet.models.entities.DetalleLM;
import com.intranet.api.intranet.repositories.IDetalleLMRepository;

@Service
public class DetalleLmServiceImpl implements IDetalleLmServices {

    @Autowired
    private IDetalleLMRepository detalleLmRespoisotry;

    @Override
    public DetalleLM buscDetalleLM(Long numlic) {
       return detalleLmRespoisotry.buscaDetalleLm(numlic);
    }

  
}
