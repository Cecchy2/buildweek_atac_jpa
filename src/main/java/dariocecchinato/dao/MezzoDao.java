package dariocecchinato.dao;

import dariocecchinato.entities.Mezzo;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class MezzoDao {
    private final EntityManager em;

    public MezzoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(mezzo);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("Il Mezzo con ID : " + mezzo.getMezzo_id() + " " + " è stato salvato con successo!");
    }

    public Mezzo getById(UUID mezzoId) {
        Mezzo found = em.find(Mezzo.class, mezzoId);
        if (found == null) throw new NotFoundException(mezzoId);
        return found;
    }
}
