package org.eliezer.springcloud.msvc.tripulantes.services;

import org.eliezer.springcloud.msvc.tripulantes.models.entity.Tripulante;
import org.eliezer.springcloud.msvc.tripulantes.repositories.TripulanteRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Autor: Eliezer Santiago
 * Fecha: 20/03/2024
 * Servicio de implementacion de metodos
 * */
@Service
public class TripulanteServiceImpl implements TripulanteService{

    @Autowired
    private TripulanteRepositorie repositorie;
    @Override
    @Transactional(readOnly = true)
    public List<Tripulante> listar() {
        return (List<Tripulante>) repositorie.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tripulante> porId(Long id) {
        return repositorie.findById(id);
    }

    @Override
    @Transactional
    public Tripulante guardar(Tripulante tripulante) {
        return repositorie.save(tripulante);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repositorie.deleteById(id);
    }

    @Override
    public Optional<Tripulante> porApellidoPaterno(String apellidoPaterno) {
        return repositorie.findByApellidoPaterno(apellidoPaterno);
    }
}
