package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import dariocecchinato.enums.StatoDistributore;
import dariocecchinato.enums.Tipo_abbonamento;
import enums.TipoMezzo;
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
        /*CREAZIONE TRATTE*/
        Supplier<Tratta> trattaSupplier = () -> new Tratta(
                f.address().cityName(),
                f.address().cityName(),
                f.number().numberBetween(15, 120)
        );
        List<Tratta> tratte = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tratta tratta = trattaSupplier.get();
            tratte.add(tratta);
        }
        //tratte.forEach(trattaDao::save);

        /*CREAZIONE RIVENDITORI*/
        Supplier<Rivenditore> randomRivenditoreSupplier = () -> {
            String nomeLocale = f.company().name();
            return new Rivenditore(nomeLocale);
        };
        /*for (int i = 0; i < 5; i++) {
            rivDao.save(randomRivenditoreSupplier.get());
        }*/
        List<Rivenditore> rivenditori = rivDao.findAll();

        /*CREAZIONE UTENTI*/
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

        /*CREAZIONE DISTRIBUTORI*/
        Supplier<Distributore> distributoreSupplier = () -> {
            StatoDistributore stato = StatoDistributore.values()[f.number().numberBetween(0, StatoDistributore.values().length)];
            String ubicazione = f.address().streetAddress();
            return new Distributore(stato, ubicazione);
        };
        /*for (int i = 0; i < 10; i++) {
            db.save(distributoreSupplier.get());
        }*/
        List<Distributore> distributori = db.findAll();

        utenti.stream()
                .filter(utente -> utente.getTessera() == null)
                .forEach(utente -> {
                    LocalDate data_emissione = LocalDate.now().minusDays(f.number().numberBetween(1, 720));
                    Tessera tessera = new Tessera(data_emissione, utente);
                    //td.save(tessera);
                });

        List<Tessera> tessere = td.findAll();

        /*CREAZIONE ABBONAMENTI*/
        Supplier<Abbonamento> randomAbbonamentoSupplier = () -> {
            Tipo_abbonamento tipo = random.nextBoolean() ? Tipo_abbonamento.MENSILE : Tipo_abbonamento.SETTIMANALE;
            LocalDate dataValidazione = LocalDate.now().minusDays(f.number().numberBetween(1, 60));
            Tessera tessera = tessere.get(random.nextInt(tessere.size()));
            if (random.nextBoolean()) {
                Rivenditore rivenditore = rivenditori.get(random.nextInt(rivenditori.size()));
                return new Abbonamento(dataValidazione, tipo, tessera, rivenditore);
            } else {
                Distributore distributore = distributori.get(random.nextInt(distributori.size()));
                return new Abbonamento(dataValidazione, tipo, tessera, distributore);
            }


        };
        /*for (int i = 0; i < 15; i++) {
            ab.save(randomAbbonamentoSupplier.get());
        }*/

        /*CREAZIONE MEZZI*/
        Supplier<Mezzo> mezzoSupplier = () -> {
            TipoMezzo[] tipiMezzi = TipoMezzo.values();
            return new Mezzo(f.number().numberBetween(8, 100), tipiMezzi[f.number().numberBetween(0, 2)]);
        };
        List<Mezzo> mezzi = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Mezzo mezzo = mezzoSupplier.get();
            mezzi.add(mezzo);
        }
        //mezzi.forEach(mezzoDao::save);

        //GENERAZIONE BIGLIETTI PER OGNI UTENTE
        List<Biglietto> biglietti = bigliettoDao.findAll();
        for (Utente utente : utenti) {
            LocalDate dataEmissione = LocalDate.now();
            double prezzo = 2.00;
            Distributore distributore = distributori.get(random.nextInt(distributori.size()));
            Rivenditore rivenditore = rivenditori.get(random.nextInt(rivenditori.size()));
            Tessera tessera = utente.getTessera();
            Biglietto biglietto = new Biglietto(dataEmissione, prezzo, distributore, rivenditore, utente, tessera);
            bigliettoDao.save(biglietto);
        }

        //**************VIDIMAZIONE DI BIGLIETTO
        /*Supplier<Vidimato> validazioneDiUnBigliettoRandomSupplier = () -> {
            Biglietto biglietto = biglietti.get(random.nextInt(biglietti.size()));
            LocalDate dataVidimazione = LocalDate.now();
            return new Vidimato(biglietto, tramFromDb, dataVidimazione);
        };*/
       /* for (int i = 0; i < 2; i++) {
            vidimatoDao.save(validazioneDiUnBigliettoRandomSupplier.get());
        }*/

        List<Mezzo> mezziT = mezzoDao.findAll();
        List<Tratta> tratteT = trattaDao.findAll();
        /*CREAZIONE GIRITRATTA*/
        Supplier<GiroTratta> giroTrattaSupplier = () -> {
            Mezzo mezzo = mezziT.get(f.number().numberBetween(0, mezzi.size()));
            Tratta tratta = tratteT.get(f.number().numberBetween(0, tratte.size()));
            LocalDateTime tempoPartenza = LocalDateTime.now().minusHours(f.number().numberBetween(1, 12));
            LocalDateTime tempoArrivo = tempoPartenza.plusMinutes(f.number().numberBetween(15, 120));
            return new GiroTratta(mezzo, tratta, tempoPartenza, tempoArrivo);
        };
        List<GiroTratta> giroTratte = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GiroTratta giroTratta = giroTrattaSupplier.get();
            giroTratte.add(giroTratta);
        }
        //giroTratte.forEach(giroTrattaDao::save);

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
                /*metodo controllo data di scadenza abbonamento*/ /*cristiano*/
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








