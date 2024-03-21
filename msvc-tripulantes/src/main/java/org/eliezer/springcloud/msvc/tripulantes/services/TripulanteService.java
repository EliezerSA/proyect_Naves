package org.eliezer.springcloud.msvc.tripulantes.services;

import org.eliezer.springcloud.msvc.tripulantes.models.entity.Tripulante;

import java.util.List;
import java.util.Optional;

/**
 * Autor: Eliezer Santiago
 * Fecha: 20/03/2024
 * Servicio de de Metodos a implementar en IMPL
 */
public interface TripulanteService {
    /**
     * Metodos a probar en Api
     * */
    List<Tripulante> listar();
    Optional<Tripulante> porId(Long id);
    Tripulante guardar(Tripulante tripulante);
    void eliminar(Long id);


    //Metodo por apellidoPaterno
    Optional<Tripulante> porApellidoPaterno(String apellidoPaterno);
}
