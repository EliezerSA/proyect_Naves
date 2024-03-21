package org.eliezer.springcloud.msvc.tripulantes.controllers;


import org.eliezer.springcloud.msvc.tripulantes.models.entity.Tripulante;
import org.eliezer.springcloud.msvc.tripulantes.services.TripulanteService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
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
public class TripulanteController {

    @Autowired
    private TripulanteService service;

    //Mapeo de Listar
    @GetMapping
    public List<Tripulante> listar() {
        return service.listar();
    }

    //Mapeo id
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Tripulante> tripulanteOptional = service.porId(id);
        if (tripulanteOptional.isPresent()) {
            return ResponseEntity.ok(tripulanteOptional.get());
        }
        //Respuesta de status http 404 "No encontrado" y si es "Encontrado" = 200
        return ResponseEntity.notFound().build();
    }

    //Guardar un objeto de tipo tripulante
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)//Status creado 201
    public ResponseEntity<?> crear(@Valid @RequestBody Tripulante tripulante, BindingResult result) {
        //Existe por ejemplo un cliente con ese apellido paterno
        if (!tripulante.getApellidoPaterno().isEmpty() && service.porApellidoPaterno(tripulante.getApellidoPaterno()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un cliente con ese apellido paterno"));
        }
        if (result.hasErrors()) {//Si hay errores en los campos
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(tripulante));
    }

    //Actualizar un registro o editarlo
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Tripulante tripulante, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {//Si hay errores en los campos
            return validar(result);
        }
        Optional<Tripulante> o = service.porId(id);
        if (o.isPresent()) {
            Tripulante tripulanteDb = o.get();
            //Existe por ejemplo un cliente con ese apellido paterno
            if (!tripulante.getApellidoPaterno().isEmpty() &&
                    !tripulante.getApellidoMaterno().equalsIgnoreCase(tripulanteDb.getApellidoMaterno()) &&
                    service.porApellidoPaterno(tripulante.getApellidoPaterno()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un cliente con ese apellido paterno"));
            }
            tripulanteDb.setNombre(tripulante.getNombre());
            tripulanteDb.setApellidoPaterno(tripulante.getApellidoPaterno());
            tripulanteDb.setApellidoMaterno(tripulante.getApellidoMaterno());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(tripulanteDb));
        }
        return ResponseEntity.notFound().build();
    }

    //Eliminar registro por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Tripulante> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Metodo extraido para validar campos
    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
