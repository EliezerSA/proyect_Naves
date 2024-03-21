package org.eliezer.springcloud.msvc.naves.entity;

import jakarta.persistence.*;

/**
 * Autor: Eliezer Santiago
 * Fecha: 20/03/2024
 *
 *  Clase de Entidad Nave para microservicio de Navess de varios tripulantes
 */
@Entity
@Table(name = "naves")
public class Nave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String modelo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
