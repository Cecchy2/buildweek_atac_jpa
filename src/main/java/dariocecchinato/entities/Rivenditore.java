package dariocecchinato.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rivenditori")
public class Rivenditore {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "nome_locale")
    private String nomeLocale;
    @OneToMany(mappedBy = "rivenditore")
    private List<Abbonamento> abbonamenti;
    @OneToMany(mappedBy = "rivenditore")
    private List<Biglietto> biglietti;


    public Rivenditore() {
    }

    public Rivenditore(String nomeLocale) {
        this.nomeLocale = nomeLocale;
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

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    @Override
    public String toString() {
        return "Rivenditore{" +
                "id=" + id +
                ", nomeLocale='" + nomeLocale + '\'' +
                '}';
    }
}
