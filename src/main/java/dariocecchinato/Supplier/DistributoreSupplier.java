package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Distributore;
import dariocecchinato.enums.StatoDistributore;

import java.util.function.Supplier;

public class DistributoreSupplier implements Supplier<Distributore> {
    Faker f = new Faker();

    @Override
    public Distributore get() {
        StatoDistributore stato = StatoDistributore.values()[f.number().numberBetween(0, StatoDistributore.values().length)];
        String ubicazione = f.address().streetAddress();
        return new Distributore(stato, ubicazione);
    }
}
