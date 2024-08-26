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

    public Mezzo(long capienza_max) {
        this.capienza_max = capienza_max;
    }

    public Mezzo() {
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "mezzo_id=" + mezzo_id +
                ", capienza_max=" + capienza_max +
                '}';
    }
}
