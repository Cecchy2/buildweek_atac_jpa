package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.DistributoreDao;
import dariocecchinato.entities.Distributore;
import dariocecchinato.enums.StatoDistributore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");

    public static void main(String[] args) {
        Faker faker = new Faker();
        EntityManager em = emf.createEntityManager();
        DistributoreDao distributoreDao = new DistributoreDao(em);

        Supplier<Distributore> distributoreSupplier = () -> {
            StatoDistributore stato = StatoDistributore.values()[faker.number().numberBetween(0, StatoDistributore.values().length)];
            String ubicazione = faker.address().streetAddress();
            return new Distributore(stato, ubicazione);
        };


       /* for (int i = 0; i < 10; i++) {
            Distributore distributore = distributoreSupplier.get();
            distributoreDao.save(distributore);
            System.out.println("Creato: " + distributore);
        }*/


        em.close();
        emf.close();

        System.out.println("funzionaaa");
    }
}
