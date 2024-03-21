package org.eliezer.springcloud.msvc.naves.services;

import org.eliezer.springcloud.msvc.naves.models.entity.Nave;

import java.util.List;
import java.util.Optional;

/**
 * Autor: Eliezer Santiago
 * Fecha: 20/03/2024
 * Servicio de de Metodos a implementar en IMPL
 */
public interface NaveService {
    List<Nave> listar();
    Optional<Nave> porId(Long id);
    Nave guardar(Nave nave);
    void eliminar(Long id);
}
