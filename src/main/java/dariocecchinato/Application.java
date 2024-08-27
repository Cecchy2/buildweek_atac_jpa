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
import java.util.*;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        Random random = new Random();
        Faker f = new Faker(Locale.ITALY);
        Scanner scanner = new Scanner(System.in);


        DistributoreDao distributoreDao = new DistributoreDao(em);
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


        Supplier<Distributore> distributoreSupplier = () -> {
            StatoDistributore stato = StatoDistributore.values()[faker.number().numberBetween(0, StatoDistributore.values().length)];
            String ubicazione = faker.address().streetAddress();
            return new Distributore(stato, ubicazione);
        };

        /*for (int i = 0; i < 10; i++) {
            Distributore distributore = distributoreSupplier.get();
            distributoreDao.save(distributore);
            System.out.println("Creato: " + distributore);*/


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
        System.out.println("fin qui ci siamo...");
        menu();
    }


    public static void menu() {
        System.out.println("Benvenuto, sei un admin o un utente?");
        System.out.println("Premere:");
        System.out.println("1- Utente");
        System.out.println("2- Amministratore");

        int scelta = inputScanner();

        switch (scelta) {
            case 1:
                menuUtente();
                break;
            case 2:
                menuAdmin();
                break;
            default:
                System.out.println("Scelta non valida");
                menu();
                break;
        }
    }

    public static void menuUtente() {
        System.out.println("Bisogna effettuare il login, inserisci il tuo codice UUID:");
        String uuid = inputScannerUUID();

        /*---------------RICERCA UTENTE TRAMITE UUUID--------------------*/
        if (trovaUtente(uuid)) {
            salutaUtente(uuid);
            controllaTessera(uuid);
        } else {
            System.out.println("Utente non trovato.");
            menu();
        }
    }

    /* -----------------MENU AMMINISTRATORE-------------------*/
    public static void menuAdmin() {
        System.out.println("Menu amministratore in sviluppo :)");

    }

    public static void salutaUtente(String uuid) {
        System.out.println("Ciao CiccioGamer");
    }

    /*------------------CONTROLL0 TESSERA---------------------*/
    public static void controllaTessera(String uuid) {
        boolean tesseraValida = verificaValiditàTessera(uuid);

        if (tesseraValida) {
            opzioniUtente();
        } else {
            System.out.println("La tua tessera è scaduta, vuoi rinnovarla?");
            System.out.println("1- Rinnovo");
            System.out.println("2- Esci");

            int scelta = inputScanner();
            switch (scelta) {
                case 1:
                    rinnovaTessera(uuid);
                    break;
                case 2:
                    chiudiScanner();
                    break;
                default:
                    System.out.println("Scelta non valida");
                    controllaTessera(uuid);
                    break;
            }
        }
    }

    /*---------OPZIONI UTENTE--------------*/
    public static void opzioniUtente() {
        System.out.println("Cosa vuoi fare?");
        System.out.println("1- Controlla data di scadenza tessera");
        System.out.println("2- Controlla i biglietti disponibili");
        System.out.println("3- Controlla il tuo abbonamento");
        System.out.println("4- Acquista biglietto");
        System.out.println("5- Acquista abbonamento");
        System.out.println("6- Esci");
        System.out.println("7- Contattaci");

        int scelta = inputScanner();

        switch (scelta) {
            case 1:
                controllaDataScadenza();
                break;
            case 2:
                controllaBiglietti();
                break;
            case 3:
                controllaAbbonamento();
                break;
            case 4:
                acquistaBiglietto();
                break;
            case 5:
                acquistaAbbonamento();
                break;
            case 6:
                chiudiScanner();
                break;
            case 7:
                contattaci();
                break;
            default:
                System.out.println("Scelta non valida");
                opzioniUtente();
                break;
        }
    }

    public static void rinnovaTessera(String uuid) {
        System.out.println("Complimenti, hai pagato millemilaeuro ad ATAC e non ce lo meritiamo!");
        chiudiScanner();
    }

    /*crare metodi per interazione utente*/

    /*------- INTERAZIONE UTENTE-----------*/
    public static int inputScanner() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Inserisci la tua scelta: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    public static String inputScannerUUID() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci UUID: ");
        return scanner.nextLine();
    }


    /*------IMPLEMENTARE LE LOGICHE---------*/
    public static boolean trovaUtente(String uuid) {

        return true;
    }

    public static boolean verificaValiditàTessera(String uuid) {

        return true;
    }

    public static void controllaDataScadenza() {
        System.out.println("La tua tessera scade il 31/12/2024.");
    }

    public static void controllaBiglietti() {
        System.out.println("Hai 5 biglietti disponibili.");
    }

    public static void controllaAbbonamento() {
        System.out.println("Il tuo abbonamento è attivo fino al 31/12/2024.");
    }

    public static void acquistaBiglietto() {
        System.out.println("Hai acquistato un biglietto.");
    }

    public static void acquistaAbbonamento() {
        System.out.println("Hai acquistato un abbonamento.");
    }

    public static void contattaci() {
        System.out.println("Puoi contattarci al numero 123-456-7890.");
    }

    public static void chiudiScanner() {
        System.out.println("Scanner chiuso. Arrivederci!");
    }
}










