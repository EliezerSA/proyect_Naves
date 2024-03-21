package org.eliezer.springcloud.msvc.naves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/***
 * Autor: Eliezer Santiago
 * Fecha:20/03/2024
 *
 * Descripcion: Clase main para ejecutar microservicio naves
 * */
@EnableFeignClients//Habilitar Api de Feign
@SpringBootApplication
public class MsvcNavesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcNavesApplication.class, args);
	}

}
