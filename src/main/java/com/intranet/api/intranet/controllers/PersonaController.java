package com.intranet.api.intranet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.intranet.api.intranet.models.entities.Persona;
import com.intranet.api.intranet.services.IPersonaService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @GetMapping("persona/buscar/{rut}")
     public ResponseEntity<?> showFuncionario(@PathVariable Integer rut) {
        try {
            Persona persona = personaService.findByRut(rut);
            if (persona == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Funcionario no encontrado con el RUT: " + rut);
            }
            return ResponseEntity.ok(persona);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al buscar el funcionario: " + e.getMessage());
        }
    }

   

}
