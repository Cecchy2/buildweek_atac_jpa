package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import dariocecchinato.enums.StatoDistributore;
import dariocecchinato.enums.Tipo_abbonamento;
import enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        Random random = new Random();
        Faker f = new Faker(Locale.ITALY);

        DistributoreDao db = new DistributoreDao(em);
        RivenditoreDao rivDao = new RivenditoreDao(em);
        UtenteDao ud = new UtenteDao(em);
        TesseraDao td = new TesseraDao(em);
        AbbonamentoDao ab = new AbbonamentoDao(em);
        TrattaDao trattaDao = new TrattaDao(em);
        MezzoDao mezzoDao = new MezzoDao(em);

        Supplier<Tratta> trattaSupplier = () -> new Tratta(
                f.address().cityName(),
                f.address().cityName(),
                f.number().numberBetween(15, 120)
        );
        List<Tratta> tratte = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tratta tratta = trattaSupplier.get();
            tratte.add(tratta);
        }
        tratte.forEach(trattaDao::save);

        Supplier<Rivenditore> randomRivenditoreSupplier = () -> {
            String nomeLocale = f.company().name();
            return new Rivenditore(nomeLocale);
        };

        for (int i = 0; i < 5; i++) {
            rivDao.save(randomRivenditoreSupplier.get());
        }

        List<Rivenditore> rivenditori = rivDao.findAll();

        Supplier<Utente> randomUtenteSupplier = () -> {
            String nomeUtente = f.name().firstName();
            String cognomeUtente = f.name().lastName();
            String email = f.internet().emailAddress();
            int eta = f.number().numberBetween(14, 88);
            String zone_di_residenza = f.address().fullAddress();
            return new Utente(nomeUtente, cognomeUtente, email, eta, zone_di_residenza);
        };

        for (int i = 0; i < 20; i++) {
            ud.save(randomUtenteSupplier.get());
        }

        List<Utente> utenti = ud.findAll();

        Supplier<Distributore> distributoreSupplier = () -> {
            StatoDistributore stato = StatoDistributore.values()[f.number().numberBetween(0, StatoDistributore.values().length)];
            String ubicazione = f.address().streetAddress();
            return new Distributore(stato, ubicazione);
        };

        for (int i = 0; i < 10; i++) {
            db.save(distributoreSupplier.get());
        }

        List<Distributore> distributori = db.findAll();

        utenti.stream()
                .filter(utente -> utente.getTessera() == null)
                .forEach(utente -> {
                    LocalDate data_emissione = LocalDate.now().minusDays(f.number().numberBetween(1, 720));
                    Tessera tessera = new Tessera(data_emissione, utente);
                    td.save(tessera);
                });

        List<Tessera> tessere = td.findAll();

        Supplier<Abbonamento> randomAbbonamentoSupplier = () -> {
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
        };

        for (int i = 0; i < 15; i++) {
            ab.save(randomAbbonamentoSupplier.get());
        }

        Supplier<Mezzo> mezzoSupplier = () -> {
            TipoMezzo[] tipiMezzi = TipoMezzo.values();
            return new Mezzo(f.number().numberBetween(8, 100), tipiMezzi[f.number().numberBetween(0, 2)]);
        };
        List<Mezzo> mezzi = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Mezzo mezzo = mezzoSupplier.get();
            mezzi.add(mezzo);
        }
        mezzi.forEach(mezzoDao::save);

        em.close();
        emf.close();
        System.out.println("fin qui ci siamo...");
    }

    public void menu() {
        System.out.println("benvenuto, sei un admin o un utente?");
        System.out.println("premere:");
        System.out.println("1- utente");
        System.out.println("2-amministratore");
    }
}
