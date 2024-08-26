package dariocecchinato.entities;

import enums.TipoServizio;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "stato_servizio")
public class StatoServizio {
    @Id
    @GeneratedValue
    private UUID stato_servizio_id;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    private LocalDate data_inizio;
    private LocalDate data_fine;

    @Enumerated(EnumType.STRING)
    private TipoServizio tipo_servizio;

}
