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
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;
    @Column(name = "data_vidimazione", nullable = false)
    private LocalDate dataVidimazione;


    public Vidimato() {
    }

    public Vidimato(Biglietto biglietto, Mezzo mezzo, LocalDate dataVidimazione) {
        this.biglietto = biglietto;
        this.mezzo = mezzo;
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

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
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
