package com.intranet.api.intranet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.Funcionario;
import com.intranet.api.intranet.services.IFuncionariosService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class DeptosController {

    @Autowired
    IFuncionariosService service;

    @GetMapping("/listdeptos")
    public List<Departamentos> show(){
        return service.findAll();
     


    }

    @GetMapping("/listjefes")
    public List<Funcionario> showList(){
        return service.listFuncionarios();
    }
    
    
    @GetMapping("/buscar/{rut}")
    public Funcionario showFuncionario(@PathVariable Integer rut){
        return service.findByRut(rut);
    }

}
