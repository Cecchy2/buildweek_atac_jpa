package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import dariocecchinato.enums.StatoDistributore;
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

        TrattaDao trattaDao = new TrattaDao(em);
        Faker faker = new Faker(Locale.ITALY);
        Supplier<Tratta> trattaSupplier = () -> new Tratta(
                faker.address().cityName(),
                faker.address().cityName(),
                faker.number().numberBetween(15, 120)
        );
        List<Tratta> tratte = new ArrayList<>();
        /*for (int i = 0; i < 10; i++) {
            Tratta tratta = trattaSupplier.get();
            tratte.add(tratta);
        }*/
        //tratte.forEach(trattaDao::save);

        System.out.println("fin qui ci siamo...");


        RivenditoreDao rivDao = new RivenditoreDao(em);
        Supplier<Rivenditore> randomRivenditoreSupplier = () -> {
            String nomeLocale = faker.company().name();

            return new Rivenditore(nomeLocale);


        };

        DistributoreDao distribDao = new DistributoreDao(em);

        Supplier<Distributore> randomDistributoreSupplier = () -> {
            // Ottengo tutti i valori dell'enum
            StatoDistributore[] stati = StatoDistributore.values();

            StatoDistributore stato = stati[random.nextInt(stati.length)];
            String ubicazione = faker.address().fullAddress();
            return new Distributore(stato, ubicazione);
        };

        /*for (int i = 0; i < 10; i++) {
            distribDao.save(randomDistributoreSupplier.get());
        }*/

       /* for (int i = 0; i < 10; i++) {
            rivDao.save(randomRivenditoreSupplier.get());
        }*/

        List<Distributore> distributori = distribDao.findAll();
        List<Rivenditore> rivenditori = rivDao.findAll();

        UtenteDao ud = new UtenteDao(em);
        TesseraDao td = new TesseraDao(em);

        Supplier<Utente> randomUtenteSupplier = () -> {
            String nomeUtente = f.name().firstName();
            String cognomeUtente = f.name().lastName();
            String email = f.internet().emailAddress();
            int eta = f.number().numberBetween(14, 88);
            String zone_di_residenza = f.address().fullAddress();
            return new Utente(nomeUtente, cognomeUtente, email, eta, zone_di_residenza);
        };

       /* for (int i = 0; i < 20; i++) {
            ud.save(randomUtenteSupplier.get());

        }*/

        List<Utente> utenti = ud.findAll();

        TesseraDao tesseraDao = new TesseraDao(em);
        Supplier<Tessera> randomTesseraSupplier = () -> {
            LocalDate dataEmissione = LocalDate.now();
            Utente utente = utenti.get(random.nextInt(utenti.size()));
            return new Tessera(dataEmissione, utente);

        };

       /* for (int i = 0; i < 2; i++) {
            tesseraDao.save(randomTesseraSupplier.get());
        }*/

        // Lista per memorizzare le tessere generate
        List<Tessera> tessere = new ArrayList<>();

        // GENRAZIONE TESSERE PER OGNI UTENTE NEL DB
        for (Utente utente : utenti) {
            // Genera la data di emissione per ogni tessera (usa la data corrente)
            LocalDate dataEmissione = LocalDate.now();

            // Crea una nuova tessera per l'utente corrente
            Tessera tessera = new Tessera(dataEmissione, utente);

            // Aggiungi la tessera alla lista di tessere
            tessere.add(tessera);
            tesseraDao.save(tessera);
        }




        /* List<Tessera> tessere = td.findAll();*/

        Supplier<Abbonamento> randomAbbonamentoSupplier = () -> {
            Tipo_abbonamento tipo = random.nextBoolean() ? Tipo_abbonamento.MENSILE : Tipo_abbonamento.SETTIMANALE;
            LocalDate dataValidazione = LocalDate.now().minusMonths(2);
            LocalDate dataScadenza = tipo == Tipo_abbonamento.MENSILE ? dataValidazione.plusMonths(1) : dataValidazione.plusWeeks(1);
            return new Abbonamento(dataValidazione, tipo);
        };

        BigliettoDao bigliettoDao = new BigliettoDao(em);

        Supplier<Biglietto> randomBigliettoSupplier = () -> {

            LocalDate dataEmissione = LocalDate.now();
            double prezzo = 2.00;
            int indiceRandomUtenti = random.nextInt(utenti.size());
            Utente utente = utenti.get(indiceRandomUtenti);
            int indiceRandomDistributori = random.nextInt(distributori.size());
            Distributore distributore = distributori.get(indiceRandomDistributori);
            int indiceRandomRivenditori = random.nextInt(rivenditori.size());
            Rivenditore rivenditore = rivenditori.get(indiceRandomRivenditori);
            Tessera tessera = tessere.get(random.nextInt(tessere.size()));
            return new Biglietto(dataEmissione, prezzo, distributore, rivenditore, utente, tessera);
        };

        for (int i = 0; i < tessere.size(); i++) {
            bigliettoDao.save(randomBigliettoSupplier.get());
        }


        em.close();
        emf.close();
    }


}







