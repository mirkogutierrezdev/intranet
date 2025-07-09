package com.intranet.api.intranet.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.api.intranet.models.entities.Contratos;
import com.intranet.api.intranet.services.ContratoService;

@RestController
@CrossOrigin(origins = "http://localhost")

@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Object> getContratoByRut(@PathVariable Integer rut) {
        try {
            Contratos contrato = contratoService.findContratosByRut(rut);
        
            return ResponseEntity.ok(contrato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
