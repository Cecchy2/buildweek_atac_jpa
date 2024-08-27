package dariocecchinato.entities;

import dariocecchinato.enums.Tipo_abbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Abbonamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate data_validazione;
    private LocalDate data_scadenza;
    @Enumerated(EnumType.STRING)
    private Tipo_abbonamento tipo_abbonamento;
    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera_id;
    @ManyToOne
    @JoinColumn(name = "distributore_id")
    private Distributore distributore_id;
    @ManyToOne
    @JoinColumn(name = "rivenditore_id")
    private Rivenditore rivenditore_id;


    public Abbonamento(LocalDate data_validazione, Tipo_abbonamento tipo_abbonamento, Tessera tessera, Rivenditore rivenditore, Distributore distributore) {
        this.data_validazione = data_validazione;
        this.tipo_abbonamento = tipo_abbonamento;
        this.data_scadenza = tipo_abbonamento == Tipo_abbonamento.MENSILE ?
                data_validazione.plusMonths(1) :
                data_validazione.plusWeeks(1);
        this.tessera_id = tessera;
        this.rivenditore_id = rivenditore;
        this.distributore_id = distributore;
    }


    public Abbonamento() {
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getData_validazione() {
        return data_validazione;
    }

    public void setData_validazione(LocalDate data_validazione) {
        this.data_validazione = data_validazione;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public Tipo_abbonamento getTipo_abbonamento() {
        return tipo_abbonamento;
    }

    public void setTipo_abbonamento(Tipo_abbonamento tipo_abbonamento) {
        this.tipo_abbonamento = tipo_abbonamento;
    }

    @Override
    public String toString() {
        return "Abbonamenti{" +
                "id=" + id +
                ", data_validazione=" + data_validazione +
                ", data_scadenza=" + data_scadenza +
                ", tipo_abbonamento=" + tipo_abbonamento +
                '}';
    }
}
