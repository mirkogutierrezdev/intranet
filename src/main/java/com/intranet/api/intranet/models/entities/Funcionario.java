package com.intranet.api.intranet.models.entities;

import java.util.List;

public class Funcionario extends Persona {


    private String area;
    private Departamentos departamento; 
    private Contratos contrato;
    private List<Ausencias> ausencias;
    private List<Feriados> feriados;
    private List<LicienciaMedica> licencias;
    private DiasAdm diasAdm;


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



    public String getArea() {
        return area;
    }



    public void setArea(String area) {
        this.area = area;
    }



    public List<Feriados> getFeriados() {
        return feriados;
    }



    public void setFeriados(List<Feriados> feriado) {
        this.feriados = feriado;
    }



    public List<Ausencias> getAusencias() {
        return ausencias;
    }



    public void setAusencias(List<Ausencias> ausencias) {
        this.ausencias = ausencias;
    }



    public List<LicienciaMedica> getLicencias() {
        return licencias;
    }



    public void setLicencias(List<LicienciaMedica> licencias) {
        this.licencias = licencias;
    }



    public DiasAdm getDiasAdm() {
        return diasAdm;
    }



    public void setDiasAdm(DiasAdm diasAdm) {
        this.diasAdm = diasAdm;
    }

    
    

   
    



}
