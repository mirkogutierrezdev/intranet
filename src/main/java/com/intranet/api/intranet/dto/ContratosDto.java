package com.intranet.api.intranet.dto;

public class ContratosDto {

    private Integer rut;
    private String nombre;
    private String nombreDepartamento;
    private Integer isJefe;
    public Integer getRut() {
        return rut;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }
    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
    public Integer getIsJefe() {
        return isJefe;
    }
    public void setIsJefe(Integer isJefe) {
        this.isJefe = isJefe;
    }

    


}
