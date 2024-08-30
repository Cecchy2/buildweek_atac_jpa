package dariocecchinato;

import com.github.javafaker.Faker;
import dariocecchinato.Supplier.*;
import dariocecchinato.dao.*;
import dariocecchinato.entities.*;
import dariocecchinato.enums.Tipo_abbonamento;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
    private static StatoServizioDao ssd = new StatoServizioDao(em);


    private static Random random = new Random();
    private static Faker f = new Faker(Locale.ITALY);

    public static void main(String[] args) {

        Amministratore amministratore = new Amministratore("Signor", "Palle", "signorpalle@gmail.com", 45, "12345");
        //amministratoreDao.save(amministratore);

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

        //**************************   CREAZIONE GIROTRATTE  *********************************
        Supplier<GiroTratta> giroTrattaSupplier = new GiroTrattaSupplier(mezzi, tratte);
        for (int i = 0; i < 10; i++) {
            //giroTrattaDao.save(giroTrattaSupplier.get());
        }
        List<GiroTratta> girotratte = giroTrattaDao.findAll();

        //**************************   CREAZIONE STATO SERVIZIO PER MEZZI  *********************************
        mezzi.forEach(mezzo -> {
            int numeroStati = random.nextInt(4) + 1;

            for (int i = 0; i < numeroStati; i++) {
                boolean statoCorrente = (i == numeroStati - 1);
                StatoServizioSupplier statoServizioSupplier = new StatoServizioSupplier(mezzo, statoCorrente);
                StatoServizio statoServizio = statoServizioSupplier.get();
                //ssd.save(statoServizio);
            }
        });

        startMenu();
        em.close();
        emf.close();
    }

    public static void startMenu() {
        menuAtac:
        while (true) {
            try {
                System.out.println("Benvenuto in Atac");
                System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione:");
                System.out.println("1- Registrati");
                System.out.println("2- Login");
                System.out.println("3- Esci");

                int scelta = gestioneInputIntMenu(1, 3);
                if (scelta < 1 || scelta > 3) {
                    System.out.println("Il numero inserito non è valido");
                    return;
                }
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
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int gestioneInputIntMenu(int min, int max) {
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
        try {
            UUID uuid = UUID.fromString(input);
            findUserOrAdminById2(uuid);
        } catch (Exception e) {
            System.out.println("id non valido, reinserisci un id valido");
            login();
        }
    }

    public static void findUserOrAdminById2(UUID personaId) {
        Utente foundUser = em.find(Utente.class, personaId);
        if (foundUser == null) {
            Amministratore foundAdmin = em.find(Amministratore.class, personaId);
            if (foundAdmin == null) {
                System.out.println("Utente o Amministratore non trovato, controlla l' ID");
                return;
            }
            System.out.println("Benvenuto/a " + foundAdmin.getNome() + " Accesso effettuato come amministratore!");
            menuAdmin(foundAdmin);
        }
        System.out.println("Benvenuto/a utente " + foundUser.getNome());
        menuUtente(foundUser.getTessera());
    }

    public static void menuUtente(Tessera tessera) {
        if (!td.isTesseraValida(tessera.getId())) { /*metodo per controllare la validita della tessera in caso fosse scaduta*/ /*kenny*/
            System.out.println("Attenzione: la tua tessera è scaduta! Vuoi rinnovarla?");
            System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione:");
            System.out.println("1- Rinnova tessera");
            System.out.println("2- Non rinnovare");
            int scelta = gestioneInputIntMenu(1, 2);
            if (scelta == 1) {
                tessera.rinnovoTessera();
            } else {
                System.out.println("Tessera non rinnovata");
            }
        }
        cicloMenuUtente:
        while (true) {
            try {
                System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione da effettuare:");
                System.out.println("1- Validazione corsa");
                System.out.println("2- Acquista biglietto");
                System.out.println("3- Abbonamenti");
                System.out.println("4- Contattaci");
                System.out.println("5- Torna indietro");
                int scelta = gestioneInputIntMenu(1, 4);
                switch (scelta) {
                    case 1:
                        vidmazioneBiglietto(tessera);
                        break;
                    case 2:
                        bigliettoDao.acquistaBiglietto(tessera);
                        break;
                    case 3:
                        menuAbbonamento(tessera.getUtente());
                        break;
                    case 4:
                        System.out.println("Hai un problema che questo menu non riesce a soddisfare, contattaci al numero +00-111-222-3333");
                        break;
                    case 5:
                        break cicloMenuUtente;
                    default:
                        System.out.println("Scelta non valida");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                acquistaAbbonamento(utente.getTessera()); /*cristiano*/
                break;
            default:
                System.out.println("Scelta non valida");
                break;
        }
    }

    public static void controllaValiditaAbbonamento(Utente utente) {
        System.out.println("Inserisci l'UUID della tua tessera:");
        String uuidInput = scanner.nextLine();
        UUID tesseraId = UUID.fromString(uuidInput);
        List<Abbonamento> abbonamenti = utente.getTessera().getAbbonamenti();
        if (abbonamenti == null || abbonamenti.isEmpty()) {
            System.out.println("Non ci sono abbonamenti associati a questa tessera.");
            return;
        }
        Abbonamento abbonamentoRecente = abbonamenti.stream()
                .max(Comparator.comparing(Abbonamento::getData_validazione))/*.max((a1, a2) -> a1.getData_validazione().compareTo(a2.getData_validazione()))*/
                .orElse(null);
        if (abbonamentoRecente == null) {
            System.out.println("Nessun abbonamento valido trovato.");
            return;
        }
        LocalDate dataScadenza = abbonamentoRecente.getData_scadenza();
        if (dataScadenza != null && !dataScadenza.isBefore(LocalDate.now())) {
            System.out.println("Il tuo abbonamento è ancora valido fino il: " + dataScadenza);
        } else {
            System.out.println("Il tuo abbonamento è scaduto il: " + dataScadenza);
        }
    }

    private static void registrazione() {

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
        Tessera nuovaTessera = new Tessera(LocalDate.now(), nuovoUtente);
        td.save(nuovaTessera);

        menuUtente(nuovaTessera);
    }

    public static void vidmazioneBiglietto(Tessera tessera) {

        List<Biglietto> biglietti = tessera.getBiglietti();
        if (biglietti.isEmpty()) {
            System.out.println("Non hai biglietti disponibili");
            return;
        }

        System.out.println("Scegli il biglietto da vidimare");
        for (int i = 0; i < biglietti.size(); i++) {
            Biglietto biglietto = biglietti.get(i);
            System.out.println(i + 1 + ". " + biglietto.getId());
        }
        int scelta = Integer.parseInt(scanner.nextLine());
        Biglietto biglietto = biglietti.get(scelta - 1);
        System.out.println("Scegli la tratta che desideri percorrere:");
        List<Tratta> listaTratte = trattaDao.findAll();
        List<Object[]> zonePartenzaCapolinea = trattaDao.getAllZonaPartenzaECapolinea();
        for (int i = 0; i < zonePartenzaCapolinea.size(); i++) {
            System.out.println(i + 1 + "- " + Arrays.toString(zonePartenzaCapolinea.get(i)));
        }
        int inputTratta = gestioneInputIntMenu(1, zonePartenzaCapolinea.size());
        List<GiroTratta> giroTrattaDellaTrattaSelezionata = listaTratte.get(inputTratta).getGiritratte();
        System.out.println("Informazioni sul giro della tratta:");
        System.out.println("Tempo di partenza : " + giroTrattaDellaTrattaSelezionata.getFirst().getTempo_partenza());
        System.out.println("Tempo di arrivo: " + giroTrattaDellaTrattaSelezionata.getFirst().getTempo_arrivo());
        System.out.println("Mezzo : " + giroTrattaDellaTrattaSelezionata.getFirst().getMezzo_id().getTipo_mezzo());
        try {
            Vidimato vidimazione = new Vidimato(biglietto, giroTrattaDellaTrattaSelezionata.getFirst(), LocalDate.now());
            vidimatoDao.save(vidimazione);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void acquistaAbbonamento(Tessera tessera) {
        System.out.println("Scegli il tipo di abbonamento:");
        System.out.println("1- Settimanale");
        System.out.println("2- Mensile");
        int sceltaTipo = gestioneInputIntMenu(1, 2);
        Tipo_abbonamento tipoAbbonamento = (sceltaTipo == 1) ? Tipo_abbonamento.SETTIMANALE : Tipo_abbonamento.MENSILE;

        boolean isRivenditore = random.nextBoolean();

        Abbonamento nuovoAbbonamento;
        if (isRivenditore) {
            Rivenditore rivenditore = rivDao.findAll().get(random.nextInt(rivDao.findAll().size()));
            nuovoAbbonamento = new Abbonamento(LocalDate.now(), tipoAbbonamento, tessera, rivenditore);
        } else {
            Distributore distributore = db.findAll().get(random.nextInt(db.findAll().size()));
            nuovoAbbonamento = new Abbonamento(LocalDate.now(), tipoAbbonamento, tessera, distributore);
        }
        ab.save(nuovoAbbonamento);

        System.out.println("Abbonamento aggiunto con successo!");
    }

    public static void menuAdmin(Amministratore admin) {
        controlloPassword:
        while (true) {
            System.out.println("Per continuare inserisci la password");
            String password = scanner.nextLine();
            if (Objects.equals(password, "12345")) {
                break controlloPassword;
            } else {
                System.out.println("password errata, riprova");
            }
        }
        cicloAdmin:
        while (true) {
            System.out.println("Quale comando vuoi eseguire?");
            System.out.println("Premi uno dei seguenti pulsanti per scegliere un operazione da effettuare:");
            System.out.println("1- Crea nuovo utente e tessera associata"); /*kenny*/
            System.out.println("2- Elimina utente"); /*kenny dato un id*/
            System.out.println("3- Cerca stato di servizio di un mezzo"); /*kenny*/
            System.out.println("4- Cerca il numero di biglietto vidimati dato un mezzo");
            System.out.println("5- Cerca il numero totale di biglietti vidimati"); /*gianluca*/
            System.out.println("6- Numero di biglietti venduti in un periodo"); /*gianluca*/
            System.out.println("7- Cerca il numero di volte che un mezzo fa una tratta");
            System.out.println("8- Tempo effettivo medio di percorrenza di una tratta"); /*diego*/
            System.out.println("9- Tempo effettivo di percorrenza di una tratta"); /*diego*/
            System.out.println("10- Cerca id tessera e id utente dato un nome e cognome ed eta"); /*ultima cosa*/
            System.out.println("11- Esci");
            int scelta = gestioneInputIntMenu(1, 11);
            switch (scelta) {
                case 1:
                    break;
                case 2:
                    eliminaUtente();
                    break;
                case 3:
                    cercaStatoMezzo();
                    break;
                case 4:
                    break;
                case 5:
                    numeroTotaleBigliettiVidimati();
                    break;
                case 6:
                    numeroBigliettiVendutiInUnPeriodo();
                    break;
                case 7:
                    numeroDiGiriEffettuatiDaUnMezzo();
                    break;
                case 8:
                    tempoEffettivoMedioPercorrenza();
                    break;
                case 9:
                    tempoEffettivoTratta();
                    break;
                case 10:
                    nonnoHaSmarritoTutto();
                    break;
                case 11:
                    break cicloAdmin;
                default:
                    break;
            }
        }
    }

    public static void tempoEffettivoMedioPercorrenza() {
        try {
            System.out.print("Inserisci l'ID della Tratta: ");
            String trattaIdInput = scanner.nextLine();
            UUID trattaId = UUID.fromString(trattaIdInput);

            System.out.print("Inserisci l'ID del Mezzo: ");
            String mezzoIdInput = scanner.nextLine();
            UUID mezzoId = UUID.fromString(mezzoIdInput);


            Tratta tratta = trattaDao.getById(trattaId);
            if (tratta == null) {
                System.out.println("Errore: Tratta con ID " + trattaId + " non trovata.");
                return;
            }

            Mezzo mezzo = mezzoDao.getById(mezzoId);
            if (mezzo == null) {
                System.out.println("Errore: Mezzo con ID " + mezzoId + " non trovato.");
                return;
            }


            double tempoMedio = amministratoreDao.calcolaTempoMedioEffettivo(tratta, mezzo);
            System.out.println("Il tempo medio effettivo di percorrenza è: " + tempoMedio + " minuti");

        } catch (IllegalArgumentException e) {
            System.out.println("Errore: L'ID inserito non è valido. Assicurati di inserire un UUID corretto.");
        } catch (NullPointerException e) {
            System.out.println("Errore: Si è verificato un problema nel recupero della tratta o del mezzo. Verifica che gli ID siano corretti.");
        } catch (Exception e) {
            System.out.println("Errore: Si è verificato un errore imprevisto. Dettagli: " + e.getMessage());
        }
    }

    public static void eliminaUtente() {
        System.out.println("Inserisci l'UUID dell'utente da eliminare:");
        String input = scanner.nextLine();
        try {
            UUID utenteId = UUID.fromString(input);
            ud.delete(utenteId);
        } catch (IllegalArgumentException e) {
            System.out.println("Formato UUID non valido.");
        } catch (Exception e) {
            System.out.println("Errore durante l'eliminazione dell'utente: " + e.getMessage());
        }
    }

    private static void tempoEffettivoTratta() {
        try {
            System.out.println("Inserisci l'UUID della tratta per la quale vuoi calcolare il tempo medio effettivo:");
            String input = scanner.nextLine();
            UUID trattaId = UUID.fromString(input);
            Tratta tratta = trattaDao.getById(trattaId);
            double tempoMedio = amministratoreDao.calcolaTempoEffettivo(tratta);
            System.out.println("Il tempo medio effettivo di percorrenza per la tratta selezionata è: " + tempoMedio + " minuti");
        } catch (IllegalArgumentException e) {
            System.out.println("L'UUID inserito non è valido. Assicurati di inserire un UUID corretto.");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static void numeroBigliettiVendutiInUnPeriodo() {
        while (true) {

            try {
                System.out.println("Devi inserire le date che indicano il periodo di tempo che vuoi analizzare");
                System.out.println("1- Inserisci la data di inzio periodo (formato YYYY-MM-DD): ");
                LocalDate dataInizioInput = LocalDate.parse(scanner.nextLine());
                System.out.println("2- Inserisci la data di fine periodo (formato YYYY-MM-DD): ");
                LocalDate dataFineInput = LocalDate.parse(scanner.nextLine());
                System.out.println("I biglietti venduti nel periodo tra " + dataInizioInput + " e " + dataFineInput + " sono: " + bigliettoDao.counterBigliettiVendutiInUnPeriodo(dataInizioInput, dataFineInput));
                System.out.println("Qui sotto la lista completa: ");
                bigliettoDao.listaBigliettiVendutiInUnPeriodo(dataInizioInput, dataFineInput).forEach(System.out::println);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("FORMATO DATA INSERITO NON VALIDO");
            }

        }
    }

    public static void cercaStatoMezzo() {
        while (true) {
            try {
                System.out.println("Inserisci l'UUID del mezzo per cui vuoi conoscere lo stato");
                String input = scanner.nextLine().trim();

                System.out.println("UUID inserito: '" + input + "'");
                UUID mezzoId = UUID.fromString(input);
                System.out.println("UUID convertito è" + mezzoId);
                StatoServizio statoMezzo = ssd.getUltimoStatoMezzo(mezzoId);

                System.out.println("Ultimo stato del mezzo: " + statoMezzo.getTipo_servizio());

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Formato UUID non valido.");
                // Stampa dettagliata dell'errore
            } catch (NoResultException e) {
                System.out.println("Errore durante la ricerca dello stato del mezzo: ");
            }
        }
    }

    public static void numeroDiGiriEffettuatiDaUnMezzo() {
        while (true) {
            try {
                System.out.println("Inserisci l'ID del mezzo per sapere quanti volte percorre una tratta: ");
                String inputMezzoId = scanner.nextLine();
                Mezzo mezzoFromDB = mezzoDao.getById(UUID.fromString(inputMezzoId));
                System.out.println("Il mezzo selezionato è un " + mezzoFromDB.getTipo_mezzo() + " ed ha percorso la tratta " + giroTrattaDao.numeroGiriTrattaDiUnMezzo(mezzoFromDB) + " volte");
                break;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void nonnoHaSmarritoTutto() {
        System.out.print("Inserisci il nome: ");
        String nome = scanner.nextLine();
        System.out.print("Inserisci il cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Inserisci l'età: ");
        int eta = scanner.nextInt();
        Utente utente = ud.ricercaNonnoUtente(nome, cognome, eta);
        System.out.println("id del utente: " + utente.getId());
        System.out.println("id della tessera: " + utente.getTessera().getId());
    }

    public static void numeroTotaleBigliettiVidimati() {
        try {
            System.out.println("Il numero totale di biglietti vidimanti è: " + vidimatoDao.restituisciNumeroTotaleBigliettiVidimati());
            System.out.println("Qui sotto la lista completa: ");
            vidimatoDao.findAll().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}








