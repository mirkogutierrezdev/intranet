package com.intranet.api.intranet.models.entities;

public class Departamentos {

    private String depto;
    private String nombre_departamento;
    private String jefe_departamento;
    private String cargo_jefe;

    public Departamentos() {

    }

    public Departamentos(String depto, String nombre_departamentos, String jefe_departamentos,
            String cargo_jefe) {
        this.depto = depto;
        this.nombre_departamento = nombre_departamentos;
        this.jefe_departamento = jefe_departamentos;
        this.cargo_jefe = cargo_jefe;

    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamentos) {
        this.nombre_departamento = nombre_departamentos;
    }

    public String getJefe_departamento() {
        return jefe_departamento;
    }

    public void setJefe_departamento(String jefe_departamentos) {
        this.jefe_departamento = jefe_departamentos;
    }

    public String getCargo_jefe() {
        return cargo_jefe;
    }

    public void setCargo_jefe(String cargo_jefe) {
        this.cargo_jefe = cargo_jefe;
    }
}
