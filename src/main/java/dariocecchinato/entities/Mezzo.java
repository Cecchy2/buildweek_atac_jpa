package dariocecchinato.entities;

import enums.TipoMezzo;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mezzo")
public class Mezzo {
    @Id
    @GeneratedValue
    private UUID mezzo_id;

    @OneToMany(mappedBy = "mezzo")
    private List<StatoServizio> stato_servizio;

    @OneToMany(mappedBy = "mezzo_id")
    private List<GiroTratta> giri_tratta;

    private long capienza_max;

    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo_mezzo;
}
