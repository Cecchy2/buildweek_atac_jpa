package dariocecchinato.dao;

import dariocecchinato.entities.Vidimato;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class VidimatoDao {
    private final EntityManager em;

    public VidimatoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Vidimato vidimato) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(vidimato);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("Il Biglietto con ID : " + vidimato.getBiglietto().getId() + " " + " è stato timbrato con successo!");
    }

    public Vidimato getById(UUID vidimatoId) {
        Vidimato found = em.find(Vidimato.class, vidimatoId);
        if (found == null) throw new NotFoundException(vidimatoId);
        return found;
    }
}
