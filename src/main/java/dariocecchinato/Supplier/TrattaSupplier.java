package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Tratta;

import java.util.function.Supplier;

public class TrattaSupplier implements Supplier<Tratta> {
    Faker f = new Faker();

    @Override
    public Tratta get() {
        return new Tratta(f.address().cityName(),
                f.address().cityName(),
                f.number().numberBetween(15, 120));
    }
}
