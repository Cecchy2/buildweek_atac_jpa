package dariocecchinato.entities;

import jakarta.persistence.*;

import java.time.Duration;
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
    private Duration tempo_effettivo_percorrenza;

    public GiroTratta() {
    }

    public GiroTratta(Mezzo mezzo_id, Tratta tratta_id, LocalDateTime tempo_partenza, LocalDateTime tempo_arrivo) {
        this.mezzo_id = mezzo_id;
        this.tratta_id = tratta_id;
        this.tempo_partenza = tempo_partenza;
        this.tempo_arrivo = tempo_arrivo;
        this.tempo_effettivo_percorrenza = calcoloTempoEffettivoPercorreza(tempo_partenza, tempo_arrivo);
    }

    public Duration calcoloTempoEffettivoPercorreza(LocalDateTime tempo_partenza, LocalDateTime tempo_arrivo) {
        if (tempo_arrivo != null && tempo_partenza != null) {
            tempo_effettivo_percorrenza = Duration.between(tempo_partenza, tempo_arrivo);
        } else {
            System.out.println("errore nel reperimento dei tempo di partenza o di arrivo!");
        }
        return tempo_effettivo_percorrenza;
    }
}
