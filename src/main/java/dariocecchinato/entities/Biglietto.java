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
    private Distributore distributore;

    @ManyToOne
    @JoinColumn(name = "rivenditore_id")
    private Rivenditore rivenditore;

    @OneToOne(mappedBy = "biglietto")
    private Vidimato vidimato;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera;

    // Costruttori
    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, double prezzo, Distributore distributore, Tessera tessera) {
        this.dataEmissione = dataEmissione;
        this.prezzo = prezzo;
        this.distributore = distributore;
        this.tessera = tessera;
    }

    public Biglietto(LocalDate dataEmissione, double prezzo, Rivenditore rivenditore, Tessera tessera) {
        this.dataEmissione = dataEmissione;
        this.prezzo = prezzo;
        this.rivenditore = rivenditore;
        this.tessera = tessera;
    }

    // Getter e Setter
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

    public Distributore getDistributore() {
        return distributore;
    }

    public void setDistributore(Distributore distributore) {
        this.distributore = distributore;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
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
