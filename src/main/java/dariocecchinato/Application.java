package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.RivenditoreDao;
import dariocecchinato.entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Locale;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        RivenditoreDao rivDao = new RivenditoreDao(em);
        Supplier<Rivenditore> randomRivenditoreSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            String nomeLocale = faker.company().name();

            return new Rivenditore(nomeLocale);


        };
        for (int i = 0; i < 5; i++) {
            rivDao.save(randomRivenditoreSupplier.get());
        }
    }
}
