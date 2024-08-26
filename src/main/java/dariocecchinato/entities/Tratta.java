package dariocecchinato.entities;

import jakarta.persistence.*;

import java.util.List;
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
    @OneToMany(mappedBy = "tratta_id")
    private List<GiroTratta> giritratte;

    public Tratta(String zona_di_partenza, String capolinea, long tempo_percorrenza) {
        this.zona_di_partenza = zona_di_partenza;
        this.capolinea = capolinea;
        this.tempo_percorrenza = tempo_percorrenza;
    }

    public Tratta() {
    }

    public UUID getTratta_id() {
        return tratta_id;
    }

    public String getZona_di_partenza() {
        return zona_di_partenza;
    }

    public void setZona_di_partenza(String zona_di_partenza) {
        this.zona_di_partenza = zona_di_partenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public long getTempo_percorrenza() {
        return tempo_percorrenza;
    }

    public void setTempo_percorrenza(long tempo_percorrenza) {
        this.tempo_percorrenza = tempo_percorrenza;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "tratta_id=" + tratta_id +
                ", zona_di_partenza='" + zona_di_partenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempo_percorrenza=" + tempo_percorrenza +
                '}';
    }
}
