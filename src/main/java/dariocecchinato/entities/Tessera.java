package dariocecchinato.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Tessera {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate data_emissione;
    private LocalDate data_scadenza;
    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Biglietto> biglietti = new ArrayList<>();

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Abbonamento> abbonamenti = new ArrayList<>();

    public Tessera(LocalDate data_emissione, Utente utente) {
        this.data_emissione = data_emissione;
        this.data_scadenza = data_emissione.plusYears(1);
        this.utente = utente;
    }

    public Tessera() {
    }

   /* public void rinnovoTessera() {
        if (LocalDate.now().isAfter(data_scadenza)) {
            data_scadenza = LocalDate.now().plusYears(1);

            System.out.println("Tessera rinnovata. Nuova data di scadenza: " + data_scadenza);
        } else {
            System.out.println("La tessera è ancora valida fino al: " + data_scadenza);
        }
    }*/


    public UUID getId() {
        return id;
    }

    public LocalDate getData_emissione() {
        return data_emissione;
    }

    public void setData_emissione(LocalDate data_emissione) {
        this.data_emissione = data_emissione;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    // Nuovo metodo per aggiungere un biglietto
    public void addBiglietto(Biglietto biglietto) {
        biglietti.add(biglietto);
        biglietto.setTessera(this); // Assicura la bidirezionalità
    }

    // Nuovo metodo per rinnovare la tessera
    /*public void rinnovoTessera() {
        this.data_scadenza = LocalDate.now().plusYears(1);
        System.out.println("Tessera rinnovata. Nuova data di scadenza: " + data_scadenza);
    }*/

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", data_emissione=" + data_emissione +
                ", data_scadenza=" + data_scadenza +
                ", utente=" + utente +
                ", biglietti=" + biglietti +
                ", abbonamenti=" + abbonamenti +
                '}';
    }
}
