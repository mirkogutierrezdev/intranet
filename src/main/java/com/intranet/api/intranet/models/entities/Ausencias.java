package com.intranet.api.intranet.models.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Ausencias {

    private Integer ident;
    private String descripcion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Long diasAusencia;
    private Integer diasFeriados;

    public Ausencias() {
    }

    public Ausencias(Integer ident, String descripcion, LocalDate fechaInicio, LocalDate fechaTermino,
            Long diasAusencia, Integer diasFeriados) {
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Long getDiasAusencia() {
        return diasAusencia;
    }

    public void setDiasAusencia(Long diasAusencia) {
        this.diasAusencia = diasAusencia;
    }

    // Metodo que calcula la cantidad de dias de trabajo , descontando sa
    public long calcularDiasHabiles(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate))
            return 0;

        long workingDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
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
