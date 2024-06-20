package com.intranet.api.intranet.models.entities;

public class Funcionario extends Persona {



    private Departamentos departemento; 
    private Contratos contrato;

    public Funcionario(){
        super();
    }

    public Departamentos getDepartemento() {
        return departemento;
    }

    public void setDepartemento(Departamentos departemento) {
        this.departemento = departemento;
    }

    public Contratos getContrato() {
        return contrato;
    }

    public void setContrato(Contratos contrato) {
        this.contrato = contrato;
    }

    



}
