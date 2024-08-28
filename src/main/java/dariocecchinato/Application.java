package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.Supplier.*;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import dariocecchinato.enums.StatoDistributore;
import dariocecchinato.enums.Tipo_abbonamento;
import dariocecchinato.exceptions.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");
    private static Scanner scanner = new Scanner(System.in);
    private static EntityManager em = emf.createEntityManager();
    /* Sezione DAO*/
    private static DistributoreDao db = new DistributoreDao(em);
    private static PersonaDao personaDao = new PersonaDao(em);
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
        };*/
       /* for (int i = 0; i < 2; i++) {
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


        startMenu();
        em.close();
        emf.close();
        System.out.println("fin qui ci siamo...");


    }

    public static void startMenu() {
        menuAtac:
        while (true) {
            System.out.println("Benvenuto in Atac");
            System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione:");
            System.out.println("1- Registrati");
            System.out.println("2- Login");
            System.out.println("3- Esci");

            int scelta = gestioneInputIntMenu(1, 3);

            switch (scelta) {
                case 1:
                    /*aggiungere metodo per la gestione del registrati*/
                    break;
                case 2:
                    /*add metodo per gestire il login*/
                    login();
                    menuUtente(); /*da togliere una volta finito il metodo login, per ora questa è solo un modo per continuare la struttura del menu*/
                    break;
                case 3:
                    /*si esce dal while principale*/
                    break menuAtac;
                default:
                    System.out.println("Scelta non valida");
                    startMenu();
                    break;
            }
        }
    }

    private static int gestioneInputIntMenu(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Pulisce il newline rimasto dopo nextInt()
                if (input < min || input > max) {
                    System.out.println("Scelta non valida. Inserisci un numero valido.");
                } else {
                    return input;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                scanner.nextLine(); // Pulisce l'input non valido
            }
        }
    }

    private static void login() {
        System.out.println("inserisci il tuo codice UUID");
        String input = scanner.nextLine();
        /*devo capire se è un utente o un admin */  /*gianluca*/
        /*fatto questo nel if else if che creeremo dobbiamo implementare i metodi che continueranno il menu*/
        personaDao.findUserOrAdminById(UUID.fromString(input));
    }

    public static void menuUtente() {
        /*metodo per controllare la validita della tessera in caso fosse scaduta*/ /*kenny*/

        System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione da effettuare:");
        System.out.println("1- Validazione corsa");
        System.out.println("2- Acquista biglietto");
        System.out.println("3- Abbonamenti");
        System.out.println("4- Contattaci");
        int scelta = gestioneInputIntMenu(1, 4);
        switch (scelta) {
            case 1:
                /*metodo per vidimare il biglietto*/ /*gianluca*/
                break;
            case 2:
                /*metodo acquista biglietto*/ /*acquista biglietto*/
                break;
            case 3:
                /*metodo abbonamento*/
                menuAbbonamento();
                break;
            case 4:
                /*metodo contattaci che in realta posso gestire con due rughe qua*/

                break;
            default:
                System.out.println("Scelta non valida");
                break;
        }

    }

    public static void menuAbbonamento() {
        System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione da effettuare:");
        System.out.println("1- Controlla validità");
        System.out.println("2- Acquista abbonamento");
        int scelta = gestioneInputIntMenu(1, 2);
        switch (scelta) {
            case 1:
                controllaValiditaAbbonamento();
                break;
            case 2:
                /*metodo acquista abbonamento*/
                break;
            default:
                System.out.println("Scelta non valida");
                break;
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

    public static void controllaValiditaAbbonamento() {

        /* 1 mi recupero tessera tramite tesseradao */

        /*2 controllo se la tessera ha gia abbonamenti caricati
         * 2.1 e se il piu recente è ancora valido
         *
         * 3 mi trovo l'abb piu recente
         *
         * 4 verifico la data discadenza*/
        System.out.println("Inserisci l'UUID della tua tessera:");
        String uuidInput = scanner.nextLine();
        UUID tesseraId = UUID.fromString(uuidInput);

        /*1*/
        Tessera tessera;
        try {
            tessera = td.getById(tesseraId);
        } catch (NotFoundException e) {
            System.out.println("Tessera non trovata.");
            return;
        }
        /*2*/

        List<Abbonamento> abbonamenti = tessera.getAbbonamenti();
        if (abbonamenti == null || abbonamenti.isEmpty()) {
            System.out.println("Non ci sono abbonamenti associati a questa tessera.");
            return;
        }

        /*3*/
        Abbonamento abbonamentoRecente = abbonamenti.stream()
                .max(Comparator.comparing(Abbonamento::getData_validazione))/*.max((a1, a2) -> a1.getData_validazione().compareTo(a2.getData_validazione()))*/
                .orElse(null);

        if (abbonamentoRecente == null) {
            System.out.println("Nessun abbonamento valido trovato.");
            return;
        }

        /*4*/
        LocalDate dataScadenza = abbonamentoRecente.getData_scadenza();
        if (dataScadenza != null && !dataScadenza.isBefore(LocalDate.now())) {
            System.out.println("Il tuo abbonamento è ancora valido fino il: " + dataScadenza);
        } else {
            System.out.println("Il tuo abbonamento è scaduto il: " + dataScadenza);
        }
    }

    private static void controllaAbbonamento() {
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








