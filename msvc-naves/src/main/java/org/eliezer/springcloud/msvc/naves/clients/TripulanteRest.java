package org.eliezer.springcloud.msvc.naves.clients;

import org.eliezer.springcloud.msvc.naves.models.Tripulante;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/***
 * Autor: Eliezer Santiago
 * Fecha:20/03/2024
 *
 * Descripcion: Clase para consumir metodos http del controlador microservicio nave
 * */
@FeignClient(name = "msvc-tripulantes", url="localhost:8080")
public interface TripulanteRest {
    //Consumir el detalle
    @GetMapping("/{id}")
    Tripulante detalle(@PathVariable Long id);

    @PostMapping("/")
    Tripulante crear(@RequestBody Tripulante tripulante);
}
