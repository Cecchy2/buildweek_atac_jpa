package dariocecchinato.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "biglietti")
public class Biglietto {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "data_emissione")
    private LocalDate dataEmissione;
    @Column(name = "prezzo")
    private double prezzo;
    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private Distributore distributore_id;
    @ManyToOne
    @JoinColumn(name = "rivenditore_id")
    private Rivenditore rivenditore_id;
    @OneToOne(mappedBy = "biglietto")
    private Vidimato vidimato;

    @OneToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera_id;

    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, double prezzo, Distributore distributore_id, Tessera tessera_id) {
        this.dataEmissione = dataEmissione;
        this.prezzo = prezzo;
        this.distributore_id = distributore_id;
        this.rivenditore_id = rivenditore_id;

        this.tessera_id = tessera_id;
    }

    public Biglietto(LocalDate dataEmissione, double prezzo, Rivenditore rivenditore_id, Tessera tessera_id) {
        this.dataEmissione = dataEmissione;
        this.prezzo = prezzo;
        this.rivenditore_id = rivenditore_id;

        this.tessera_id = tessera_id;
    }


    public UUID getId() {
        return id;
    }


    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public Vidimato getVidimato() {
        return vidimato;
    }

    public void setVidimato(Vidimato vidimato) {
        this.vidimato = vidimato;
    }

    public Tessera getTessera_id() {
        return tessera_id;
    }

    public void setTessera_id(Tessera tessera_id) {
        this.tessera_id = tessera_id;
    }

    public Rivenditore getRivenditore_id() {
        return rivenditore_id;
    }

    public void setRivenditore_id(Rivenditore rivenditore_id) {
        this.rivenditore_id = rivenditore_id;
    }

    public Distributore getDistributore_id() {
        return distributore_id;
    }

    public void setDistributore_id(Distributore distributore_id) {
        this.distributore_id = distributore_id;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", prezzo=" + prezzo +


                '}';
    }
}
