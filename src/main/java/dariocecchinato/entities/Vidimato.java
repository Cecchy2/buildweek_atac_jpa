package dariocecchinato.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "vidimato")
public class Vidimato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "biglietto_id", nullable = false)
    private Biglietto biglietto;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;


    public Vidimato() {
    }

    public UUID getId() {
        return id;
    }
    

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    @Override
    public String toString() {
        return "Vidimato{" +
                "id=" + id +
                ", biglietto=" + biglietto +
                ", mezzo=" + mezzo +
                ", dataVidimazione=" + dataVidimazione +
                '}';
    }
}

