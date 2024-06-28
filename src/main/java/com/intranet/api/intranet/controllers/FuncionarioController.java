package com.intranet.api.intranet.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.intranet.api.intranet.models.entities.Funcionario;

import com.intranet.api.intranet.services.IFuncionariosService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class FuncionarioController {

    @Autowired
    IFuncionariosService service;
    
    @GetMapping("/buscar/{rut}")
    public Funcionario showFuncionario(@PathVariable Integer rut){
        return service.findByRut(rut);
    }
}
