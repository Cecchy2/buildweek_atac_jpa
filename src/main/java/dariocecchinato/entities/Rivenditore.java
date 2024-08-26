package dariocecchinato.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rivenditori")
public class Rivenditore {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "nome_locale")
    private String nomeLocale;

    public Rivenditore() {
    }

    public Rivenditore(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public UUID getId() {
        return id;
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
