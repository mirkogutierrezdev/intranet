package com.intranet.api.intranet.models.entities;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Ausencias {

    private Integer ident;
    private Integer rut;
    private String descripcion;
    private Integer linausencia;
    private String resol;
    private Date fecha_resol;
    private Date fecha_inicio;
    private Date fecha_termino;
    private Long dias_ausencia;

    public Ausencias(Integer ident, String descripcion, Integer linausencia, Date fecha_resol, Date fecha_inicio,
            Date fecha_termino) {
        this.ident = ident;
        this.descripcion = descripcion;
        this.linausencia = linausencia;
        this.fecha_resol = fecha_resol;
        this.fecha_inicio = fecha_inicio;
        this.fecha_termino = fecha_termino;
    }

    public Ausencias() {
    }

    public Integer getIdent() {
        return ident;
    }

    public void setIdent(Integer ident) {
        this.ident = ident;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getLinausencia() {
        return linausencia;
    }

    public void setLinausencia(Integer linausencia) {
        this.linausencia = linausencia;
    }

    public Date getFecha_resol() {
        return fecha_resol;
    }

    public void setFecha_resol(Date fecha_resol) {
        this.fecha_resol = fecha_resol;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_termino() {
        return fecha_termino;
    }

    public void setFecha_termino(Date fecha_termino) {
        this.fecha_termino = fecha_termino;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getResol() {
        return resol;
    }

    public void setResol(String resol) {
        this.resol = resol;
    }

    public Long getDias_ausencia() {
        return dias_ausencia;
    }

    public void setDias_ausencia(Long dias_ausencia) {
        this.dias_ausencia = dias_ausencia;
    }

   
    public long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {
        LocalDate startDate = sqlStartDate.toLocalDate();
        LocalDate endDate = sqlEndDate.toLocalDate();

        long workingDays = 0;

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            date = date.plusDays(1);
        }

        return workingDays;
    }

    
    

    

    

    

}
