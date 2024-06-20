package com.intranet.api.intranet.models.entities;

public class Departamentos {

    private String depto;
    private String nombre_departamento;
    private String jefe_departamento;
 
   
    public Departamentos(){
        
    }

    
    public Departamentos(String depto, String nombre_departamentos, String jefe_departamentos, 
            String login, int ident) {
        this.depto = depto;
        this.nombre_departamento = nombre_departamentos;
        this.jefe_departamento = jefe_departamentos;
        
       
    
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
 
  

    


}
