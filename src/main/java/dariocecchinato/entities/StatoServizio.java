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

    public StatoServizio(LocalDate data_inizio, LocalDate data_fine, TipoServizio tipo_servizio) {
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.tipo_servizio = tipo_servizio;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDate data_fine) {
        this.data_fine = data_fine;
    }

    public TipoServizio getTipo_servizio() {
        return tipo_servizio;
    }

    public void setTipo_servizio(TipoServizio tipo_servizio) {
        this.tipo_servizio = tipo_servizio;
    }

    @Override
    public String toString() {
        return "StatoServizio{" +
                "stato_servizio_id=" + stato_servizio_id +
                ", data_inizio=" + data_inizio +
                ", data_fine=" + data_fine +
                ", tipo_servizio=" + tipo_servizio +
                '}';
    }
}
