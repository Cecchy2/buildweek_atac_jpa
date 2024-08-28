package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Rivenditore;

import java.util.function.Supplier;

public class RivenditoreSupplier implements Supplier<Rivenditore> {
    Faker f = new Faker();

    @Override
    public Rivenditore get() {
        return new Rivenditore(f.company().name());
    }
}
