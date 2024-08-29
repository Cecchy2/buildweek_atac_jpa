package dariocecchinato.entities;

import dariocecchinato.enums.StatoDistributore;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "distributori")
public class Distributore {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "stato")
    @Enumerated(EnumType.STRING)
    private StatoDistributore stato;
    @Column(name = "ubicazione")
    private String ubicazione;
    @OneToMany(mappedBy = "distributore")
    private List<Abbonamento> abbonamenti;
    @OneToMany(mappedBy = "distributore")
    private List<Biglietto> biglietti;

    public Distributore() {
    }

    public Distributore(StatoDistributore stato, String ubicazione) {
        this.stato = stato;
        this.ubicazione = ubicazione;
    }

    public UUID getId() {
        return id;
    }


    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    public StatoDistributore getStato() {
        return stato;
    }

    public void setStato(StatoDistributore stato) {
        this.stato = stato;
    }

    public String getUbicazione() {
        return ubicazione;
    }

    public void setUbicazione(String ubicazione) {
        this.ubicazione = ubicazione;
    }

    @Override
    public String toString() {
        return "Distributore{" +
                "id=" + id +
                ", stato=" + stato +
                ", ubicazione='" + ubicazione + '\'' +
                '}';
    }
}
