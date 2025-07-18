package com.intranet.api.intranet.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.api.intranet.dto.ContratosDto;
import com.intranet.api.intranet.models.entities.Departamentos;
import com.intranet.api.intranet.models.entities.Funcionario;
import com.intranet.api.intranet.services.ContratosServiceImpl;
import com.intranet.api.intranet.services.IDepartamentosService;
import com.intranet.api.intranet.services.IFuncionariosService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/smc/funcionario")
public class FuncionarioController {

    private final IFuncionariosService service;

    private final IDepartamentosService departamentosService;

    private final ContratosServiceImpl contratosServiceImpl;
    
    
    public FuncionarioController(IFuncionariosService service, IDepartamentosService departamentosService, ContratosServiceImpl contratosServiceImpl) {
        this.service = service;
        this.departamentosService = departamentosService;
        this.contratosServiceImpl = contratosServiceImpl;
    }



    @GetMapping("/buscar/{rut}")
    public ResponseEntity<Object> showFuncionario(@PathVariable Integer rut) {
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


    
    @GetMapping("/esjefe/{rut}/{depto}")
    public ResponseEntity<Boolean> esJefe(@PathVariable Integer rut,@PathVariable String depto ) {
        Boolean result = service.isJefe(rut, depto);
        HttpStatus status = result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND; // Podrías ajustar según tu lógica
        return ResponseEntity.status(status).body(result);
    }

    @GetMapping("/depto/{depto}")
    public Departamentos buscaDepto(@PathVariable String depto){
        return departamentosService.buscaDepartamento(depto);
    }

    
    @GetMapping("/listDtoDepto/{depto}")
    public List<ContratosDto> listDeptos(@PathVariable String depto){
        return contratosServiceImpl.lisContratosDtos(depto);
    }

}
