package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Mezzo;
import dariocecchinato.entities.StatoServizio;
import dariocecchinato.enums.TipoServizio;

import java.time.LocalDate;
import java.util.Random;
import java.util.function.Supplier;

public class StatoServizioSupplier implements Supplier<StatoServizio> {
    Random random = new Random();
    Faker f = new Faker();
    private Mezzo mezzo;
    private boolean statoCorrente;

    public StatoServizioSupplier(Mezzo mezzo, boolean statoCorrente) {
        this.mezzo = mezzo;
        this.statoCorrente = statoCorrente;
    }

    @Override
    public StatoServizio get() {
        TipoServizio[] tipiServizio = TipoServizio.values();
        TipoServizio tipoServizio = tipiServizio[random.nextInt(tipiServizio.length)];

        LocalDate dataInizio = LocalDate.now().minusDays(f.number().numberBetween(10, 90));
        LocalDate dataFine = dataInizio.plusDays(f.number().numberBetween(10, 30));

        return new StatoServizio(mezzo, dataInizio, dataFine, tipoServizio);
    }
}
