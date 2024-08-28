package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.GiroTratta;
import dariocecchinato.entities.Mezzo;
import dariocecchinato.entities.Tratta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class GiroTrattaSupplier implements Supplier<GiroTratta> {
    Random random = new Random();
    Faker f = new Faker();
    private List<Mezzo> mezzi;
    private List<Tratta> tratte;

    public GiroTrattaSupplier(List<Mezzo> mezzi, List<Tratta> tratte) {
        this.mezzi = mezzi;
        this.tratte = tratte;
    }

    @Override
    public GiroTratta get() {
        Mezzo mezzo = mezzi.get(random.nextInt(mezzi.size()));
        Tratta tratta = tratte.get(random.nextInt(tratte.size()));
        LocalDateTime tempo_partenza = LocalDateTime.now().minusHours(random.nextInt(12) + 1);
        LocalDateTime tempo_arrivo = tempo_partenza.plusMinutes(random.nextInt(105) + 15);

        return new GiroTratta(mezzo, tratta, tempo_partenza, tempo_arrivo);

    }

}
