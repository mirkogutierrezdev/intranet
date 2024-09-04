package com.intranet.api.intranet.models.entities;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Ausencias {

    private Integer ident;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaTermino;
    private Long diasAusencia;
    private Integer diasFeriados;


    public Ausencias() {
    }

    public Ausencias(Integer ident, String descripcion, Date fechaInicio, Date fechaTermino,Long diasAusencia, Integer diasFeriados) {
        this.ident = ident;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.diasFeriados = diasFeriados;
        this.diasAusencia = diasAusencia;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Long getDiasAusencia() {
        return diasAusencia;
    }

    public void setDiasAusencia(Long diasAusencia) {
        this.diasAusencia = diasAusencia;
    }

    //Metodo que calcula la cantidad de dias de trabajo , descontando sa
    public long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {
        LocalDate startDate = sqlStartDate.toLocalDate();
        LocalDate endDate = sqlEndDate.toLocalDate();

        // Si la fecha de inicio es después de la fecha de fin, no hay días hábiles
        if (startDate.isAfter(endDate)) {
            return 0;
        }

        long workingDays = 0;

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            // Añadir 1 al conteo de días hábiles si no es sábado ni domingo
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            // Moverse al siguiente día
            date = date.plusDays(1);
        }

        return workingDays;
    }

    public Integer getDiasFeriados() {
        return diasFeriados;
    }

    public void setDiasFeriados(Integer diasFeriados) {
        this.diasFeriados = diasFeriados;
    }
}
