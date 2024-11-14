package com.intranet.api.intranet.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.Funcionario;
import com.intranet.api.intranet.services.IDepartamentosService;
import com.intranet.api.intranet.services.IFuncionariosService;

@RestController
@CrossOrigin(origins = "http://localhost")
@RequestMapping("/api/smc/funcionario")
public class FuncionarioController {

    private final IFuncionariosService service;

    private final IDepartamentosService departamentosService;

    
    
    public FuncionarioController(IFuncionariosService service, IDepartamentosService departamentosService) {
        this.service = service;
        this.departamentosService = departamentosService;
    }



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


    
    @GetMapping("/esjefe/{rut}")
    public ResponseEntity<Boolean> esJefe(@PathVariable Integer rut) {
        Boolean result = service.isJefe(rut);
        HttpStatus status = result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND; // Podrías ajustar según tu lógica
        return ResponseEntity.status(status).body(result);
    }

    @GetMapping("/depto/{depto}")
    public Departamentos buscaDepto(@PathVariable String depto){
        return departamentosService.buscaDepartamento(depto);
    }

}
