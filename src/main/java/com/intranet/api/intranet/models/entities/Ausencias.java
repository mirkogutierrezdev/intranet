package com.intranet.api.intranet.models.entities;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Ausencias {

    private Integer ident;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_termino;
    private Long dias_ausencia;
    private Integer dias_feriados;

    public Ausencias() {
    }

    public Ausencias(Integer ident, String descripcion, Date fecha_inicio, Date fecha_termino, Integer dias_feriados) {
        this.ident = ident;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_termino = fecha_termino;
        this.dias_feriados = dias_feriados;
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

    public Long getDias_ausencia() {
        return dias_ausencia;
    }

    public void setDias_ausencia(Long dias_ausencia) {
        this.dias_ausencia = dias_ausencia;
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

    public Integer getDias_feriados() {
        return dias_feriados;
    }

    public void setDias_feriados(Integer dias_feriados) {
        this.dias_feriados = dias_feriados;
    }

}
