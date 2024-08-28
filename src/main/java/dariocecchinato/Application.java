package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.Supplier.*;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");
    private static Scanner scanner = new Scanner(System.in);
    private static EntityManager em = emf.createEntityManager();
    /* Sezione DAO*/
    private static DistributoreDao db = new DistributoreDao(em);
    private static RivenditoreDao rivDao = new RivenditoreDao(em);
    private static UtenteDao ud = new UtenteDao(em);
    private static TesseraDao td = new TesseraDao(em);
    private static AbbonamentoDao ab = new AbbonamentoDao(em);
    private static TrattaDao trattaDao = new TrattaDao(em);
    private static BigliettoDao bigliettoDao = new BigliettoDao(em);
    private static MezzoDao mezzoDao = new MezzoDao(em);
    private static GiroTrattaDao giroTrattaDao = new GiroTrattaDao(em);
    private static AmministratoreDao amministratoreDao = new AmministratoreDao(em);
    private static VidimatoDao vidimatoDao = new VidimatoDao(em);
    private static Random random = new Random();
    private static Faker f = new Faker(Locale.ITALY);

    public static void main(String[] args) {

        //**************************   CREAZIONE TRATTE  *********************************
        Supplier<Tratta> trattaSupplier = new TrattaSupplier();
        for (int i = 0; i < 10; i++) {
            //trattaDao.save(trattaSupplier.get());
        }
        List<Tratta> tratte = trattaDao.findAll();
        //**************************   CREAZIONE RIVENDITORI  *********************************
        Supplier<Rivenditore> randomRivenditoreSupplier = new RivenditoreSupplier();
        for (int i = 0; i < 5; i++) {
            //rivDao.save(randomRivenditoreSupplier.get());
        }
        List<Rivenditore> rivenditori = rivDao.findAll();
        //**************************   CREAZIONE UTENTI  *********************************
        Supplier<Utente> randomUtenteSupplier = new UtenteSupplier();
        for (int i = 0; i < 20; i++) {
            //ud.save(randomUtenteSupplier.get());
        }
        List<Utente> utenti = ud.findAll();
        //**************************   CREAZIONE DISTRIBUTORI  *********************************
        Supplier<Distributore> distributoreSupplier = new DistributoreSupplier();
        for (int i = 0; i < 10; i++) {
            //db.save(distributoreSupplier.get());
        }
        List<Distributore> distributori = db.findAll();
        //**************************   CREAZIONE TESSERE  *********************************
        utenti.stream()
                .filter(utente -> utente.getTessera() == null)
                .forEach(utente -> {
                    Supplier<Tessera> tesseraSupplier = new TesseraSupplier(utente);
                    Tessera tessera = tesseraSupplier.get();
                    //td.save(tessera);
                });
        List<Tessera> tessere = td.findAll();
        //**************************   CREAZIONE ABBONAMENTI  *********************************
        Supplier<Abbonamento> randomAbbonamentoSupplier = new AbbonamentoSupplier(tessere, rivenditori, distributori);
        for (int i = 0; i < 15; i++) {
            //ab.save(randomAbbonamentoSupplier.get());
        }
        List<Abbonamento> abbonamenti = ab.findAll();
        //**************************   CREAZIONE MEZZI  *********************************
        Supplier<Mezzo> mezzoSupplier = new MezzoSupplier();
        for (int i = 0; i < 25; i++) {
            //mezzoDao.save(mezzoSupplier.get());
        }
        List<Mezzo> mezzi = mezzoDao.findAll();
        //**************************   CREAZIONE BIGLIETTI  *********************************
        Supplier<Biglietto> bigliettoSupplier = new BigliettoSupplier(tessere, rivenditori, distributori);
        for (int i = 0; i < 4; i++) {
            //bigliettoDao.save(bigliettoSupplier.get());
        }
        List<Biglietto> biglietti = bigliettoDao.findAll();
        //**************VIDIMAZIONE DI BIGLIETTO
        /*Supplier<Vidimato> validazioneDiUnBigliettoRandomSupplier = () -> {
            Biglietto biglietto = biglietti.get(random.nextInt(biglietti.size()));
            LocalDate dataVidimazione = LocalDate.now();
            return new Vidimato(biglietto, tramFromDb, dataVidimazione);
        };
        for (int i = 0; i < 2; i++) {
            vidimatoDao.save(validazioneDiUnBigliettoRandomSupplier.get());
        }*/
        //**************************   CREAZIONE GIROTRATTE  *********************************
        Supplier<GiroTratta> giroTrattaSupplier = new GiroTrattaSupplier(mezzi, tratte);
        for (int i = 0; i < 10; i++) {
            //giroTrattaDao.save(giroTrattaSupplier.get());
        }
        List<GiroTratta> girotratte = giroTrattaDao.findAll();

        /**/
        /*Tratta trattaAnalizzata = trattaDao.getById(UUID.fromString("7aa0af42-9fa6-420d-9a53-a7fdeac6fb91"));
        System.out.println("Tempo medio effettivo in minuti: " + amministratoreDao.calcolaTempoMedioEffettivo(trattaAnalizzata));*/

        em.close();
        emf.close();
        System.out.println("fin qui ci siamo...");
    }

    public static void menu() {
        System.out.println("Benvenuto in atac, sei un admin o un utente?");
        System.out.println("Premere:");
        System.out.println("1- Utente");
        System.out.println("2- Amministratore");

        int scelta = scanner.nextInt();

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
        String uuid = scanner.nextLine();

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

    /*creare metodi per interazione utente*/

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








