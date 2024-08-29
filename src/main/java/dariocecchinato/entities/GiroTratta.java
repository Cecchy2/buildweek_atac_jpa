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
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta_id;

    private LocalDateTime tempo_partenza;
    private LocalDateTime tempo_arrivo;
    private Long tempo_effettivo_percorrenza;

    public GiroTratta() {
    }

    public GiroTratta(Mezzo mezzo_id, Tratta tratta_id, LocalDateTime tempo_partenza, LocalDateTime tempo_arrivo) {
        this.mezzo = mezzo_id;
        this.tratta_id = tratta_id;
        this.tempo_partenza = tempo_partenza;
        this.tempo_arrivo = tempo_arrivo;
        this.tempo_effettivo_percorrenza = calcoloTempoEffettivoPercorreza(tempo_partenza, tempo_arrivo);
    }

    public Long calcoloTempoEffettivoPercorreza(LocalDateTime tempo_partenza, LocalDateTime tempo_arrivo) {
        if (tempo_arrivo != null && tempo_partenza != null) {
            tempo_effettivo_percorrenza = Duration.between(tempo_partenza, tempo_arrivo).toMinutes();
        } else {
            System.out.println("errore nel reperimento dei tempo di partenza o di arrivo!");
        }
        return tempo_effettivo_percorrenza;
    }

    public UUID getGiro_tratta_id() {
        return giro_tratta_id;
    }

    public Mezzo getMezzo_id() {
        return mezzo;
    }

    public void setMezzo_id(Mezzo mezzo_id) {
        this.mezzo = mezzo_id;
    }

    public Tratta getTratta_id() {
        return tratta_id;
    }

    public void setTratta_id(Tratta tratta_id) {
        this.tratta_id = tratta_id;
    }

    public LocalDateTime getTempo_partenza() {
        return tempo_partenza;
    }

    public void setTempo_partenza(LocalDateTime tempo_partenza) {
        this.tempo_partenza = tempo_partenza;
    }

    public LocalDateTime getTempo_arrivo() {
        return tempo_arrivo;
    }

    public void setTempo_arrivo(LocalDateTime tempo_arrivo) {
        this.tempo_arrivo = tempo_arrivo;
    }

    public Long getTempo_effettivo_percorrenza() {
        return tempo_effettivo_percorrenza;
    }

    public void setTempo_effettivo_percorrenza(Long tempo_effettivo_percorrenza) {
        this.tempo_effettivo_percorrenza = tempo_effettivo_percorrenza;
    }


    @Override
    public String toString() {
        return "GiroTratta{" +
                "mezzo_id=" + mezzo +
                ", tratta_id=" + tratta_id +
                ", tempo_partenza=" + tempo_partenza +
                ", tempo_arrivo=" + tempo_arrivo +
                '}';
    }
}
