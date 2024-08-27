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
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @OneToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera_id;

    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, double prezzo, Distributore distributore_id, Rivenditore rivenditore_id, Utente utente, Tessera tessera_id) {
        this.dataEmissione = dataEmissione;
        this.prezzo = prezzo;
        this.distributore_id = distributore_id;
        this.rivenditore_id = rivenditore_id;
        this.utente = utente;
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

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", prezzo=" + prezzo +
                ", utente=" + utente +

                '}';
    }
}
