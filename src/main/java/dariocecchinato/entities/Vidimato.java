package dariocecchinato.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vidimato")
public class Vidimato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "biglietto_id", nullable = false)
    private Biglietto biglietto;
    @ManyToOne
    @JoinColumn(name = "giro_tratta_id", nullable = false)
    private GiroTratta giroTratta;
    @Column(name = "data_vidimazione", nullable = false)
    private LocalDate dataVidimazione;


    public Vidimato() {
    }

    public Vidimato(Biglietto biglietto, GiroTratta giroTratta, LocalDate dataVidimazione) {
        this.biglietto = biglietto;
        this.giroTratta = giroTratta;
        this.dataVidimazione = dataVidimazione;
    }

    public UUID getId() {
        return id;
    }

    public Biglietto getBiglietto() {
        return biglietto;
    }

    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
    }

    public GiroTratta getGiroTratta() {
        return giroTratta;
    }

    public void setGiroTratta(GiroTratta giroTratta) {
        this.giroTratta = giroTratta;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }


    @Override
    public String toString() {
        return "Vidimato{" +
                "id=" + id +
                ", biglietto=" + biglietto +
                ", mezzo=" + mezzo +
                ", dataVidimazione=" + dataVidimazione +
                '}';
    }
}
