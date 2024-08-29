package dariocecchinato.entities;

import dariocecchinato.enums.TipoMezzo;
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

    @OneToMany(mappedBy = "mezzo")
    private List<GiroTratta> giri_tratta;

    private long capienza_max;

    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo_mezzo;

    /*COSTRUTTORI*/
    public Mezzo() {
    }

    public Mezzo(long capienza_max, TipoMezzo tipo_mezzo) {
        this.capienza_max = capienza_max;
        this.tipo_mezzo = tipo_mezzo;
    }

    public long getCapienza_max() {
        return capienza_max;
    }

    public void setCapienza_max(long capienza_max) {
        this.capienza_max = capienza_max;
    }

    public TipoMezzo getTipo_mezzo() {
        return tipo_mezzo;
    }

    public void setTipo_mezzo(TipoMezzo tipo_mezzo) {
        this.tipo_mezzo = tipo_mezzo;
    }

    public UUID getMezzo_id() {
        return mezzo_id;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "mezzo_id=" + mezzo_id +
                ", stato_servizio=" + stato_servizio +

                ", capienza_max=" + capienza_max +
                ", tipo_mezzo=" + tipo_mezzo +
                '}';
    }
}
