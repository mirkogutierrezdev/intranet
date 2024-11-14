package com.intranet.api.intranet.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.services.IDepartamentosService;

@RestController
@CrossOrigin(origins = "http://localhost")
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final IDepartamentosService departamentosService;


    public DepartamentoController(IDepartamentosService departamentosService) {
        this.departamentosService = departamentosService;
    }



    @GetMapping("/list")
    public List<Departamentos> findAll(){

        return departamentosService.findAll();

    }

}
