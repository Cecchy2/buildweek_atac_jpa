package dariocecchinato.dao;

import dariocecchinato.entities.Tratta;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class TrattaDao {
    private final EntityManager em;

    public TrattaDao(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta tratta) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(tratta);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("La Tratta con ID : " + tratta.toString() + " " + " è stata salvata con successo!");
    }

    public Tratta getById(UUID trattaId) {
        Tratta found = em.find(Tratta.class, trattaId);
        if (found == null) throw new NotFoundException(trattaId);
        return found;
    }
}
