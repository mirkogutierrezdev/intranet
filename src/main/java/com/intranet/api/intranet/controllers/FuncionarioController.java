package com.intranet.api.intranet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> showFuncionario(@PathVariable Integer rut) {
        try {
            Funcionario funcionario = service.findByRut(rut);
            if (funcionario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Funcionario no encontrado con el RUT: " + rut);
            }
            return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al buscar el funcionario: " + e.getMessage());
        }
    }
}
