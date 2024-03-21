package org.eliezer.springcloud.msvc.naves.models.entity;

import jakarta.persistence.*;
import org.eliezer.springcloud.msvc.naves.models.Tripulante;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "nave_id")//Crear foreign Key de la otra tabla
    private List<NaveTripulante> naveTripulantes;

    //Atributo no mapeado a peristencia de tablas
    @Transient
    private List<Tripulante> tripulantes;
    public Nave(){
        naveTripulantes = new ArrayList<>();
        tripulantes = new ArrayList<>();
    }

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

    //Getter y Setter de la relacion con la clase nave y tripulantes
    public void addNaveTripulante(NaveTripulante naveTripulante){
        naveTripulantes.add(naveTripulante);
    }

    public void removeNaveTripulante(NaveTripulante naveTripulante){
        naveTripulantes.remove(naveTripulante);
    }


    public List<NaveTripulante> getNaveTripulantes() {
        return naveTripulantes;
    }

    public void setNaveTripulantes(List<NaveTripulante> naveTripulantes) {
        this.naveTripulantes = naveTripulantes;
    }

    public List<Tripulante> getTripulantes() {
        return tripulantes;
    }

    public void setTripulantes(List<Tripulante> tripulantes) {
        this.tripulantes = tripulantes;
    }
}
