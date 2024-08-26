package dariocecchinato.dao;

import dariocecchinato.entities.Distributore;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class DistributoreDao {
    private final EntityManager em;

    public DistributoreDao(EntityManager em) {
        this.em = em;
    }

    public void save(Distributore distributore) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(distributore);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("Il Distributore con ID : " + distributore.getId() + " " + " è stato salvato con successo!");
    }

    public Distributore getById(UUID distributoreId) {
        Distributore found = em.find(Distributore.class, distributoreId);
        if (found == null) throw new NotFoundException(distributoreId);
        return found;
    }
}
