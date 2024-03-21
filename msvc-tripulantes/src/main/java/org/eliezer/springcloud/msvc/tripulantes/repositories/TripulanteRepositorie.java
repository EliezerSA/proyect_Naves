package org.eliezer.springcloud.msvc.tripulantes.repositories;

import org.eliezer.springcloud.msvc.tripulantes.models.entity.Tripulante;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Autor: Eliezer Santiago
 * Fecha: 20/03/2024
 *
 * Repositorio CRUD de metodos
 * */
public interface TripulanteRepositorie extends CrudRepository<Tripulante, Long> {
    Optional<Tripulante> findByApellidoPaterno(String apellidoPaterno);
}
