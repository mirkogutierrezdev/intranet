package com.intranet.api.intranet.models.entities;

import java.util.List;

public class Funcionario extends Persona {



    private Departamentos departamento; 
    private Contratos contrato;


    public Funcionario(){
        super();
    }

    public Departamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamentos departemento) {
        this.departamento = departemento;
    }

    public Contratos getContrato() {
        return contrato;
    }

    public void setContrato(Contratos contrato) {
        this.contrato = contrato;
    }

    
   


    
    



}
