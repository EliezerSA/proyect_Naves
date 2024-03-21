package org.eliezer.springcloud.msvc.naves.services;

import org.eliezer.springcloud.msvc.naves.clients.TripulanteRest;
import org.eliezer.springcloud.msvc.naves.models.Tripulante;
import org.eliezer.springcloud.msvc.naves.models.entity.Nave;
import org.eliezer.springcloud.msvc.naves.models.entity.NaveTripulante;
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

    @Autowired
    private TripulanteRest client;


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



    //Metodos de la comunicacion nave con tripulantes
    //Metodos de la comunicacion nave con tripulantes
    @Override
    @Transactional
    public Optional<Tripulante> asignarTripulante(Tripulante tripulante, Long naveId) {
        Optional<Nave> o = repository.findById(naveId);
        if(o.isPresent()){
            Tripulante tripulanteMsvc = client.detalle(tripulante.getId());
            //Obtener la financieraCliente
            Nave financiera = o.get();
            NaveTripulante naveTripulante = new NaveTripulante();
            naveTripulante.setTripulanteId(tripulanteMsvc.getId());

            //Asignar
            financiera.addNaveTripulante(naveTripulante);
            repository.save(financiera);
            return Optional.of(tripulanteMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Tripulante> crearTripulante(Tripulante tripulante, Long naveId) {
        Optional<Nave> o = repository.findById(naveId);
        if(o.isPresent()){
            Tripulante tripulanteNuevoMsvc = client.crear(tripulante);
            //Obtener la naveTripulante
            Nave nave = o.get();
            NaveTripulante naveTripulante = new NaveTripulante();
            naveTripulante.setTripulanteId(tripulanteNuevoMsvc.getId());

            //Asignar
            nave.addNaveTripulante(naveTripulante);
            repository.save(nave);
            return Optional.of(tripulanteNuevoMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Tripulante> eliminarTripulante(Tripulante tripulante, Long naveId) {
        Optional<Nave> o = repository.findById(naveId);
        if(o.isPresent()){
            Tripulante tripulanteMsvc = client.detalle(tripulante.getId());
            //Obtener la naveTripulante
            Nave nave = o.get();
            NaveTripulante naveTripulante = new NaveTripulante();
            naveTripulante.setTripulanteId(tripulanteMsvc.getId());

            //Asignar
            nave.removeNaveTripulante(naveTripulante);
            repository.save(nave);
            return Optional.of(tripulanteMsvc);

        }
        return Optional.empty();
    }



}
