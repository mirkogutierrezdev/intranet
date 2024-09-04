package com.intranet.api.intranet.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.api.intranet.models.entities.Feriado;
import com.intranet.api.intranet.services.IFeriadoService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/smc/feriados")
public class FeriadoController {

    private final IFeriadoService feriadosService;

    public FeriadoController(IFeriadoService feriadosService) {
        this.feriadosService = feriadosService;
    }



    @GetMapping("/calcular")
    public ResponseEntity<List<Feriado>> getFeriados(@RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaTermino") Date fechaTermino) {

        try {
            
            List<Feriado> diasFeriados = feriadosService.buscaFeriados(fechaInicio, fechaTermino);
            return new ResponseEntity<>(diasFeriados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
