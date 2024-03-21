package org.eliezer.springcloud.msvc.naves.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.eliezer.springcloud.msvc.naves.models.Tripulante;
import org.eliezer.springcloud.msvc.naves.models.entity.Nave;
import org.eliezer.springcloud.msvc.naves.services.NaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Autor: Eliezer Santiago
 * Fecha: 20/03/2024
 *
 * Controlador para manejar las peticiones API Rest en cada Endpoint
 * */

@RestController
public class NaveController {

    @Autowired
    private NaveService service;

    @GetMapping
    public ResponseEntity<List<Nave>> listar(){
        return  ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Nave> f = service.porId(id);
        if(f.isPresent()){
            return  ResponseEntity.ok(f.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Nave nave, BindingResult result){
        if(result.hasErrors()){//Si hay errores en los campos
            return validar(result);
        }
        Nave naveDb = service.guardar(nave);
        return ResponseEntity.status(HttpStatus.CREATED).body(naveDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Nave nave, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){//Si hay errores en los campos
            return validar(result);
        }
        Optional<Nave> f = service.porId(id);
        if(f.isPresent()){
            Nave naveDb = f.get();
            naveDb.setNombre(nave.getNombre());
            naveDb.setModelo(nave.getModelo());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(naveDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Nave> f = service.porId(id);
        if(f.isPresent()){
            service.eliminar(f.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Metodos de mapaeo de los nuevos metodos de la relacion tripulante y naves
    @PutMapping("/asignar-tripulante/{naveId}")
    public ResponseEntity<?> asignarTripulante(@RequestBody Tripulante tripulante, @PathVariable Long naveId){
        Optional<Tripulante> o;
        try {
            o = service.asignarTripulante(tripulante, naveId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el tripulante por " +
                            "el id o error en la comunicacion: " + e.getMessage() ));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-tripulante/{naveId}")
    public ResponseEntity<?> crearCliente(@RequestBody Tripulante tripulante, @PathVariable Long naveId){
        Optional<Tripulante> o;
        try {
            o = service.crearTripulante(tripulante, naveId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el tripulante " +
                            "o error en la comunicacion: " + e.getMessage() ));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-tripulante/{naveId}")
    public ResponseEntity<?> eliminarCliente(@RequestBody Tripulante tripulante, @PathVariable Long naveId){
        Optional<Tripulante> o;
        try {
            o = service.eliminarTripulante(tripulante, naveId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el tripulante por " +
                            "el id o error en la comunicacion: " + e.getMessage() ));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }


    //Metodo extraido para validar campos
    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(),"El campo " + err.getField()+ "" +err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
