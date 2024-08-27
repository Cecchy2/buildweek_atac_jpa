package dariocecchinato.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Utente extends Persona {
    private String zone_di_residenza;
    @OneToOne(mappedBy = "utente")
    private Tessera tessera;


    public Utente() {
    }

    public Utente(String zone_di_residenza) {
        this.zone_di_residenza = zone_di_residenza;
    }

    public Utente(String nome, String cognome, String email, int eta, String zone_di_residenza) {
        super(nome, cognome, email, eta);
        this.zone_di_residenza = zone_di_residenza;
    }

    public void setZone_di_residenza(String zone_di_residenza) {
        this.zone_di_residenza = zone_di_residenza;
    }

    @Override
    public String toString() {
        return "Utente{" +
                ", zone_di_residenza='" + zone_di_residenza + '\'' +
                '}';
    }
}
