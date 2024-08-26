package dariocecchinato.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "giro_tratta")
public class GiroTratta {
    @Id
    @GeneratedValue
    private UUID giro_tratta_id;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo_id;

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta_id;

    private LocalDateTime tempo_partenza;
    private LocalDateTime tempo_arrivo;
    private LocalDateTime tempo_effettivo_percorrenza;

}
