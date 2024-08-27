package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.TesseraDao;
import dariocecchinato.dao.UtenteDao;
import dariocecchinato.entities.Tessera;
import dariocecchinato.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
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

        UtenteDao ud = new UtenteDao(em);
        TesseraDao td = new TesseraDao(em);

        /*Supplier<Utente> randomUtenteSupplier = () -> {
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

        List<Utente> utenti = ud.findAll();*/

        ¸

        //List<Tessera> tessere = td.findAll();

        /*Supplier<Abbonamento> randomAbbonamentoSupplier = () -> {
            Tipo_abbonamento tipo = random.nextBoolean() ? Tipo_abbonamento.MENSILE : Tipo_abbonamento.SETTIMANALE;
            LocalDate dataValidazione = LocalDate.now().minusMonths(2);
            LocalDate dataScadenza = tipo == Tipo_abbonamento.MENSILE ? dataValidazione.plusMonths(1) : dataValidazione.plusWeeks(1);
            return new Abbonamento(dataValidazione, tipo);
        };*/

        em.close();
        emf.close();
    }

}
