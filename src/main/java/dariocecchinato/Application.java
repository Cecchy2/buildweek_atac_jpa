package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.Supplier.*;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("atac");
    private static Scanner scanner = new Scanner(System.in);
    private static EntityManager em = emf.createEntityManager();


    //**************************   SEZIONE DAO  *********************************
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
            // db.save(distributoreSupplier.get());
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
                    registrazione();
                    break;
                case 2:
                    /*add metodo per gestire il login*/
                    login();
                    //menuUtente(); /*da togliere una volta finito il metodo login, per ora questa è solo un modo per continuare la struttura del menu*/
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
        findUserOrAdminById2(UUID.fromString(input));

    }

    public static void findUserOrAdminById2(UUID personaId) {
        Utente foundUser = em.find(Utente.class, personaId);
        if (foundUser == null) {
            Amministratore foundAdmin = em.find(Amministratore.class, personaId);
            System.out.println("Benvenuto/a " + foundAdmin.getNome() + " Accesso effettuato come amministratore!");
            /*metodo per avanzare nel menu amministratore*/
        }
        System.out.println("Benvenuto/a utente " + foundUser.getNome());
        menuUtente(foundUser);
    }

    public static void menuUtente(Utente utente) {
        if (!td.isTesseraValida(utente.getTessera().getId())) { /*metodo per controllare la validita della tessera in caso fosse scaduta*/ /*kenny*/
            System.out.println("Attenzione: la tua tessera è scaduta! Vuoi rinnovarla?");
            System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione:");
            System.out.println("1- Rinnova tessera");
            System.out.println("2- Non rinnovare");
            int scelta = gestioneInputIntMenu(1, 2);
            if (scelta == 1) {
                Tessera tessera = utente.getTessera();
                tessera.rinnovoTessera();
            } else {
                System.out.println("Tessera non rinnovata");
            }
        }
        cicloMenuUtente:
        while (true) {
            System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione da effettuare:");
            System.out.println("1- Validazione corsa");
            System.out.println("2- Acquista biglietto");
            System.out.println("3- Abbonamenti");
            System.out.println("4- Contattaci");
            System.out.println("5- Esci");
            int scelta = gestioneInputIntMenu(1, 4);
            switch (scelta) {
                case 1:
                    /*metodo per vidimare il biglietto*/ /*gianluca*/
                    break;
                case 2:
                    bigliettoDao.acquistaBiglietto(utente.getTessera());
                    break;
                case 3:
                    /*metodo abbonamento*/
                    menuAbbonamento(utente);
                    break;
                case 4:
                    /*messaggio che compare in contattaci*/
                    System.out.println("Hai un problema che questo menu non riesce a soddisfare, contattaci al numero +00-111-222-3333");
                    break;
                case 5:
                    break cicloMenuUtente;
                default:
                    System.out.println("Scelta non valida");
                    break;
            }

        }
    }

    public static void menuAbbonamento(Utente utente) {
        System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione da effettuare:");
        System.out.println("1- Controlla validità");
        System.out.println("2- Acquista abbonamento");
        int scelta = gestioneInputIntMenu(1, 2);
        switch (scelta) {
            case 1:
                controllaValiditaAbbonamento(utente); /*cristiano*/
                break;
            case 2:
                /*metodo acquista abbonamento*/
                break;
            default:
                System.out.println("Scelta non valida");
                break;
        }
    }

    public static void controllaValiditaAbbonamento(Utente utente) {

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

    private static void registrazione() {
        /*crei un utente, e subito dopo ti crei la tessera*/


        // dati per la registrazione


        System.out.println("Inserisci il tuo nome:");
        String nome = scanner.nextLine();
        System.out.println("Inserisci il tuo cognome:");
        String cognome = scanner.nextLine();
        System.out.println("Inserisci la tua email:");
        String email = scanner.nextLine();
        System.out.println("Inserisci la tua età:");
        int eta = gestioneInputIntMenu(12, 95);
        System.out.println("Inserisci la tua zona di residenza:");
        String zonaDiResidenza = scanner.nextLine();

        // creazione nuovo utente
        Utente nuovoUtente = new Utente(nome, cognome, email, eta, zonaDiResidenza);

        ud.save(nuovoUtente);
        // creazione Tessera associata

        Tessera nuovaTessera = new Tessera(LocalDate.now(), nuovoUtente);

        td.save(nuovaTessera);

        System.out.println("Utente creato: " + nuovoUtente);
        System.out.println("Tessera associata:" + nuovaTessera);
        
        menuUtente(nuovoUtente);


    }
}








