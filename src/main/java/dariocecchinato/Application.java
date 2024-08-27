package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import dariocecchinato.enums.Tipo_abbonamento;
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

        RivenditoreDao rivDao = new RivenditoreDao(em);
        UtenteDao ud = new UtenteDao(em);
        TesseraDao td = new TesseraDao(em);
        AbbonamentoDao ab = new AbbonamentoDao(em);

        TrattaDao trattaDao = new TrattaDao(em);
        Faker faker = new Faker(Locale.ITALY);
        Supplier<Tratta> trattaSupplier = () -> new Tratta(
                faker.address().cityName(),
                faker.address().cityName(),
                faker.number().numberBetween(15, 120)
        );
        List<Tratta> tratte = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tratta tratta = trattaSupplier.get();
            tratte.add(tratta);
        }
        tratte.forEach(trattaDao::save);
        System.out.println("fin qui ci siamo...");


        Supplier<Rivenditore> randomRivenditoreSupplier = () -> {
            String nomeLocale = faker.company().name();

            return new Rivenditore(nomeLocale);
        };


        /*for (int i = 0; i < 5; i++) {
            rivDao.save(randomRivenditoreSupplier.get());
        }*/


        Supplier<Utente> randomUtenteSupplier = () -> {
            String nomeUtente = f.name().firstName();
            String cognomeUtente = f.name().lastName();
            String email = f.internet().emailAddress();
            int eta = f.number().numberBetween(14, 88);
            String zone_di_residenza = f.address().fullAddress();
            return new Utente(nomeUtente, cognomeUtente, email, eta, zone_di_residenza);
        };
        for (int i = 0; i < 20; i++) {
            //ud.save(randomUtenteSupplier.get());

        }

        List<Utente> utenti = ud.findAll();

        Supplier<Tessera> randomTesseraSupplier = () -> {
            LocalDate data_emissione = LocalDate.now().minusYears(random.nextInt(1));
            Utente utente = utenti.get(random.nextInt(20));
            return new Tessera(data_emissione, utente);
        };
        for (int i = 0; i < 15; i++) {
            //td.save(randomTesseraSupplier.get());
        }
        List<Tessera> tessere = td.findAll();

        Supplier<Abbonamento> randomAbbonamentoSupplier = () -> {
            Tipo_abbonamento tipo = random.nextBoolean() ? Tipo_abbonamento.MENSILE : Tipo_abbonamento.SETTIMANALE;
            LocalDate dataValidazione = LocalDate.now().minusMonths(2);
            LocalDate dataScadenza = tipo == Tipo_abbonamento.MENSILE ? dataValidazione.plusMonths(1) : dataValidazione.plusWeeks(1);
            return new Abbonamento(dataValidazione, tipo);
        };

        for (int i = 0; i < 15; i++) {
            ab.save(randomAbbonamentoSupplier.get());
        }


        em.close();
        emf.close();
    }


}







