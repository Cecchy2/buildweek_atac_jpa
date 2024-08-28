package dariocecchinato.dao;

import dariocecchinato.entities.GiroTratta;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class GiroTrattaDao {
    private final EntityManager em;

    public GiroTrattaDao(EntityManager em) {
        this.em = em;
    }

    public void save(GiroTratta giroTratta) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(giroTratta);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("Il GiroTratta con ID : " + giroTratta.getGiro_tratta_id() + " " + " è stato salvato con successo!");
    }

    public GiroTratta getById(UUID giroTrattaId) {
        GiroTratta found = em.find(GiroTratta.class, giroTrattaId);
        if (found == null) throw new NotFoundException(giroTrattaId);
        return found;
    }

    public List<GiroTratta> findAll() {
        TypedQuery<GiroTratta> query = em.createQuery("SELECT m FROM GiroTratta m", GiroTratta.class);
        return query.getResultList();
    }
}
