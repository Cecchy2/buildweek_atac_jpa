package dariocecchinato.dao;

import dariocecchinato.entities.Rivenditore;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class RivenditoreDao {
    private final EntityManager em;

    public RivenditoreDao(EntityManager em) {
        this.em = em;
    }

    public void save(Rivenditore rivenditore) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(rivenditore);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("Il Rivenditore con ID : " + rivenditore.getId() + " " + " è stato salvato con successo!");
    }

    public Rivenditore getById(UUID rivenditoreId) {
        Rivenditore found = em.find(Rivenditore.class, rivenditoreId);
        if (found == null) throw new NotFoundException(rivenditoreId);
        return found;
    }

    public List<Rivenditore> findAll() {
        TypedQuery<Rivenditore> query = em.createQuery("SELECT r FROM Rivenditore r", Rivenditore.class);
        return query.getResultList();
    }
}
