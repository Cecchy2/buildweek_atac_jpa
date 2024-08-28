package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Tessera;
import dariocecchinato.entities.Utente;

import java.time.LocalDate;
import java.util.Random;
import java.util.function.Supplier;

public class TesseraSupplier implements Supplier<Tessera> {
    Random random = new Random();
    Faker f = new Faker();
    private Utente utente;

    public TesseraSupplier(Utente utente) {
        this.utente = utente;
    }

    @Override
    public Tessera get() {
        LocalDate data_emissione = LocalDate.now().minusDays(f.number().numberBetween(1, 720));
        return new Tessera(data_emissione, utente);
    }
}
