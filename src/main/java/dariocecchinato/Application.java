package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.RivenditoreDao;
import dariocecchinato.dao.TesseraDao;
import dariocecchinato.dao.TrattaDao;
import dariocecchinato.dao.UtenteDao;
import dariocecchinato.entities.Rivenditore;
import dariocecchinato.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.*;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        Random random = new Random();
        Faker f = new Faker(Locale.ITALY);
        Scanner scanner = new Scanner(System.in);

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


        RivenditoreDao rivDao = new RivenditoreDao(em);
        Supplier<Rivenditore> randomRivenditoreSupplier = () -> {
            String nomeLocale = faker.company().name();

            return new Rivenditore(nomeLocale);


        };
        //for (int i = 0; i < 5; i++) {
        //rivDao.save(randomRivenditoreSupplier.get());
        // }

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

        //List<Tessera> tessere = td.findAll();

        /*Supplier<Abbonamento> randomAbbonamentoSupplier = () -> {
            Tipo_abbonamento tipo = random.nextBoolean() ? Tipo_abbonamento.MENSILE : Tipo_abbonamento.SETTIMANALE;
            LocalDate dataValidazione = LocalDate.now().minusMonths(2);
            LocalDate dataScadenza = tipo == Tipo_abbonamento.MENSILE ? dataValidazione.plusMonths(1) : dataValidazione.plusWeeks(1);
            return new Abbonamento(dataValidazione, tipo);
        };*/

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







