package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Biglietto;
import dariocecchinato.entities.Distributore;
import dariocecchinato.entities.Rivenditore;
import dariocecchinato.entities.Tessera;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BigliettoSupplier implements Supplier<Biglietto> {
    Random random = new Random();
    Faker f = new Faker();
    private List<Tessera> tessere;
    private List<Rivenditore> rivenditori;
    private List<Distributore> distributori;

    public BigliettoSupplier(List<Tessera> tessere, List<Rivenditore> rivenditori, List<Distributore> distributori) {
        this.tessere = tessere;
        this.rivenditori = rivenditori;
        this.distributori = distributori;
    }

    @Override
    public Biglietto get() {
        LocalDate dataEmissione = LocalDate.now().minusDays(f.number().numberBetween(1, 30));
        double prezzo;
        Tessera tessera = tessere.get(random.nextInt(tessere.size()));

        if (random.nextBoolean()) {
            Distributore distributore = distributori.get(random.nextInt(distributori.size()));
            prezzo = 1.50;
            return new Biglietto(dataEmissione, prezzo, distributore, tessera);
        } else {
            Rivenditore rivenditore = rivenditori.get(random.nextInt(rivenditori.size()));
            prezzo = 2.0;
            return new Biglietto(dataEmissione, prezzo, rivenditore, tessera);
        }
    }
}