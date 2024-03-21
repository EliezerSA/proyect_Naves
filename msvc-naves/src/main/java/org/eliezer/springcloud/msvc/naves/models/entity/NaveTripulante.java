package org.eliezer.springcloud.msvc.naves.models.entity;

import jakarta.persistence.*;

/***
 * Autor:Eliezer Santiago
 * Fecha:20/03/2024
 *
 * Descripcion: Entidad Nave tripulante intermedia para la comunicacion de
 * microservicios Nave y Tripulante
 *
 */
@Entity
@Table(name="naves_tripulantes")
public class NaveTripulante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tripulante_id", unique = true)
    private Long tripulanteId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTripulanteId() {
        return tripulanteId;
    }

    public void setTripulanteId(Long tripulanteId) {
        this.tripulanteId = tripulanteId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;

        }
        if(!(obj instanceof NaveTripulante)){
            return false;
        }
        NaveTripulante o = (NaveTripulante) obj;
        return this.tripulanteId != null && this.tripulanteId.equals(o.tripulanteId);

    }


}
