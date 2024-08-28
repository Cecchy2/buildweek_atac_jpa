package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Mezzo;
import dariocecchinato.enums.TipoMezzo;

import java.util.function.Supplier;

public class MezzoSupplier implements Supplier<Mezzo> {

    @Override
    public Mezzo get() {
        Faker f = new Faker();

        TipoMezzo[] tipiMezzi = TipoMezzo.values();
        return new Mezzo(f.number().numberBetween(8, 100), tipiMezzi[f.number().numberBetween(0, 2)]);
    }
}

