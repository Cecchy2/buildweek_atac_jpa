package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.UtenteDao;
import dariocecchinato.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Locale;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

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
