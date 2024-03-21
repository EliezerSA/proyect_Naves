package org.eliezer.springcloud.msvc.naves.services;

import org.eliezer.springcloud.msvc.naves.entity.Nave;
import org.eliezer.springcloud.msvc.naves.repositories.NaveRepository;
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
public class NaveServiceImpl implements NaveService{

    @Autowired
    private NaveRepository repository;


    @Override
    @Transactional(readOnly = true)
    public List<Nave> listar() {
        return (List<Nave>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Nave> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Nave guardar(Nave nave) {
        return repository.save(nave);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
