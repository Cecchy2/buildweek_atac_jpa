package dariocecchinato.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tratta")
public class Tratta {
    @Id
    @GeneratedValue
    private UUID tratta_id;

    private String zona_di_partenza;
    private String capolinea;
    private long tempo_percorrenza;
}
