package dariocecchinato.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
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
}
