package dariocecchinato.dao;

import dariocecchinato.entities.Biglietto;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class BigliettoDao {
    private final EntityManager em;

    public BigliettoDao(EntityManager em) {
        this.em = em;
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
}
