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
    @JoinColumn(name = "distributore_id (FK)")
    private Distributore distributore;
    @ManyToOne
    @JoinColumn(name = "rivenditore_id (FK)")
    private Rivenditore rivenditore;
    

}
