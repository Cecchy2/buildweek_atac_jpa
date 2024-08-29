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

    public Biglietto getById(UUID bigliettoId) {
        Biglietto found = em.find(Biglietto.class, bigliettoId);
        if (found == null) throw new NotFoundException(bigliettoId);
        return found;
    }

    public List<Biglietto> findAll() {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
        return query.getResultList();
    }

    public void acquistaBiglietto(Tessera tessera) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
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
                    save(biglietto);
                    System.out.println("Hai acquistato Il Biglietto " + biglietto.getId());
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

                    Biglietto biglietto2 = new Biglietto(LocalDate.now(), 1.50, rivenditore, tessera);
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
        }
    }
}
