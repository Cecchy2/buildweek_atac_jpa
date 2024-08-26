package dariocecchinato.entities;

import dariocecchinato.enums.Tipo_abbonamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Abbonamenti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate data_validazione;
    private LocalDate data_scadenza;
    private Tipo_abbonamento tipo_abbonamento;

    public Abbonamenti(LocalDate data_validazione, LocalDate data_scadenza, Tipo_abbonamento tipo_abbonamento) {
        this.data_validazione = data_validazione;
        this.data_scadenza = data_scadenza;
        this.tipo_abbonamento = tipo_abbonamento;
    }

    public Abbonamenti() {
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
