package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Abbonamento;
import dariocecchinato.entities.Distributore;
import dariocecchinato.entities.Rivenditore;
import dariocecchinato.entities.Tessera;
import dariocecchinato.enums.Tipo_abbonamento;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class AbbonamentoSupplier implements Supplier<Abbonamento> {
    Random random = new Random();
    Faker f = new Faker();
    private List<Tessera> tessere;
    private List<Rivenditore> rivenditori;
    private List<Distributore> distributori;

    public AbbonamentoSupplier(List<Tessera> tessere, List<Rivenditore> rivenditori, List<Distributore> distributori) {
        this.tessere = tessere;
        this.rivenditori = rivenditori;
        this.distributori = distributori;
    }

    @Override
    public Abbonamento get() {
        Tipo_abbonamento tipo = random.nextBoolean() ? Tipo_abbonamento.MENSILE : Tipo_abbonamento.SETTIMANALE;
        LocalDate dataValidazione = LocalDate.now().minusDays(f.number().numberBetween(1, 60));
        Tessera tessera = tessere.get(random.nextInt(tessere.size()));
        if (random.nextBoolean()) {
            Rivenditore rivenditore = rivenditori.get(random.nextInt(rivenditori.size()));
            return new Abbonamento(dataValidazione, tipo, tessera, rivenditore);
        } else {
            Distributore distributore = distributori.get(random.nextInt(distributori.size()));
            return new Abbonamento(dataValidazione, tipo, tessera, distributore);
        }
    }
}
