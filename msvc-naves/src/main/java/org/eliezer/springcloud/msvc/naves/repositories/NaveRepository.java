package org.eliezer.springcloud.msvc.naves.repositories;

import org.eliezer.springcloud.msvc.naves.models.entity.Nave;
import org.springframework.data.repository.CrudRepository;

/**
 * Autor: Eliezer Santiago
 * Fecha: 20/03/2024
 *
 * Repositorio CRUD de metodos
 * */
public interface NaveRepository extends CrudRepository<Nave, Long> {
}
