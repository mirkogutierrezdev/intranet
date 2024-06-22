package com.intranet.api.intranet.models.entities;

import java.sql.Date;

public class Contratos {


    private Date fechainicio;
    private Date fechatermino;
    private int licontrato;
    private String depto;

    public Contratos(){


    }



    public Date getFechainicio() {
        return fechainicio;
    }
    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }
    public Date getFechatermino() {
        return fechatermino;
    }
    public void setFechatermino(Date fechatermino) {
        this.fechatermino = fechatermino;
    }
    public int getLicontrato() {
        return licontrato;
    }
    public void setLicontrato(int licontrato) {
        this.licontrato = licontrato;
    }



    public String getDepto() {
        return depto;
    }



    public void setDepto(String depto) {
        this.depto = depto;
    }


    
}
