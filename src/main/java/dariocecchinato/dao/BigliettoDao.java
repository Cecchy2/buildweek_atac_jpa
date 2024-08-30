package dariocecchinato.dao;

import dariocecchinato.entities.Biglietto;
import dariocecchinato.entities.Distributore;
import dariocecchinato.entities.Rivenditore;
import dariocecchinato.entities.Tessera;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static dariocecchinato.Application.gestioneInputIntMenu;
import static dariocecchinato.Application.menuUtente;

public class BigliettoDao {
    private final EntityManager em;
    private DistributoreDao db;
    private RivenditoreDao rivDao;
    private TesseraDao td;

    public BigliettoDao(EntityManager em) {
        this.em = em;
        this.db = new DistributoreDao(em);
        this.rivDao = new RivenditoreDao(em);
        this.td = new TesseraDao(em);
    }

    public void save(Biglietto biglietto) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(biglietto);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("Il Biglietto con ID : " + biglietto.getId() + " " + " è stato salvato con successo!");
    }

    //*************************************  Metodo getById  ****************************************
    public Biglietto getById(UUID bigliettoId) {
        Biglietto found = em.find(Biglietto.class, bigliettoId);
        if (found == null) throw new NotFoundException(bigliettoId);
        return found;
    }

    //*************************************  Metodo findAll  ****************************************
    public List<Biglietto> findAll() {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
        return query.getResultList();
    }

    //*************************************  Metodo acquistaBiglietto  ****************************************
    public void acquistaBiglietto(Tessera tessera) {
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
                return;
            }
        }
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Vuoi acquistare da un Distributore o da un Rivenditore?");
                System.out.println("1. Distributore");
                System.out.println("2. Rivenditore");
                System.out.println("0. Torna indietro");
                String scelta = scanner.nextLine();

                switch (scelta) {
                    case "1":
                        List<Distributore> distributori = db.findAll();
                        System.out.println("Scegli un Distributore");
                        for (int i = 0; i < distributori.size(); i++) {
                            System.out.println(i + 1 + ". " + distributori.get(i).getUbicazione());
                        }
                        String distributoreScelto = scanner.nextLine();
                        int indiceDistributore = Integer.parseInt(distributoreScelto);

                        Distributore distributore = distributori.get(indiceDistributore - 1);
                        Biglietto biglietto = new Biglietto(LocalDate.now(), 1.50, distributore, tessera);
                        tessera.setBiglietti(biglietto.getTessera_id().getBiglietti());
                        tessera.getBiglietti().add(biglietto);
                        save(biglietto);

                        System.out.println("Hai acquistato Il Biglietto " + biglietto.getId());

                        menuUtente(biglietto.getTessera_id());
                        break;

                    case "2":
                        List<Rivenditore> rivenditori = rivDao.findAll();
                        System.out.println("Scegli un Rivenditore");
                        for (int i = 0; i < rivenditori.size(); i++) {
                            System.out.println(i + 1 + ". " + rivenditori.get(i).getNomeLocale());
                        }
                        String rivenditoreScelto = scanner.nextLine();
                        int indiceRivenditore = Integer.parseInt(rivenditoreScelto);
                        Rivenditore rivenditore = rivenditori.get(indiceRivenditore - 1);

                        Biglietto biglietto2 = new Biglietto(LocalDate.now(), 2.0, rivenditore, tessera);
                        tessera.setBiglietti(biglietto2.getTessera_id().getBiglietti());
                        tessera.getBiglietti().add(biglietto2);
                        save(biglietto2);
                        System.out.println("Hai acquistato Il Biglietto" + biglietto2.getId());
                        break;

                    case "0":
                        System.out.println("Esci..");
                        return;

                    default:
                        System.out.println("Scelta non valida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //*************************************  Metodo counterBigliettiVendutiInUnPeriodo  ****************************************
    public Long counterBigliettiVendutiInUnPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione BETWEEN :data_inizio AND :data_fine", Long.class);
        query.setParameter("data_inizio", dataInizio);
        query.setParameter("data_fine", dataFine);
        return query.getSingleResult();
    }

    //*************************************  Metodo listaBigliettiVendutiInUnPeriodo  ****************************************
    public List<Biglietto> listaBigliettiVendutiInUnPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :data_inizio AND :data_fine", Biglietto.class);
        query.setParameter("data_inizio", dataInizio);
        query.setParameter("data_fine", dataFine);
        return query.getResultList();
    }
}
