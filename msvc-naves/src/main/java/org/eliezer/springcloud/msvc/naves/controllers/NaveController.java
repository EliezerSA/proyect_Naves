package org.eliezer.springcloud.msvc.naves.controllers;

import jakarta.validation.Valid;
import org.eliezer.springcloud.msvc.naves.models.entity.Nave;
import org.eliezer.springcloud.msvc.naves.services.NaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    //Metodo extraido para validar campos
    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(),"El campo " + err.getField()+ "" +err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
