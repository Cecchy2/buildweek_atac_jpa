package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.RivenditoreDao;
import dariocecchinato.dao.TrattaDao;
import dariocecchinato.dao.UtenteDao;
import dariocecchinato.entities.Rivenditore;
import dariocecchinato.entities.Tratta;
import dariocecchinato.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

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
        //tratte.forEach(trattaDao::save);
        System.out.println("fin qui ci siamo...");


        RivenditoreDao rivDao = new RivenditoreDao(em);
        Supplier<Rivenditore> randomRivenditoreSupplier = () -> {
            String nomeLocale = faker.company().name();

            return new Rivenditore(nomeLocale);


        };
        //for (int i = 0; i < 5; i++) {
        //rivDao.save(randomRivenditoreSupplier.get());
        // }

        UtenteDao ud = new UtenteDao(em);

        Supplier<Utente> randomUtenteSupplier = () -> {
            Faker f = new Faker(Locale.ITALY);
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


    }


}
