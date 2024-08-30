package dariocecchinato.dao;

import dariocecchinato.entities.StatoServizio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.UUID;

public class StatoServizioDao {
    private final EntityManager em;

    public StatoServizioDao(EntityManager em) {
        this.em = em;
    }


    public void save(StatoServizio statoServizio) {
        //NEL PROCESSO DI SCRITTURA BISOGNA UTILIZZARE UNA TRANSAZIONE PER ASSICURARSI CHE AVVENGA IN SICUREZZA

        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        //2.avviamo la transazione
        transaction.begin();

        //3.aggiungo l'evento al persistence context
        em.persist(statoServizio);

        //4.concludiamo la transazione salvando l'evento nel DB
        transaction.commit();

        System.out.println("lo stato servizio" + statoServizio.getTipo_servizio() + " " + " è stato salvato con successo!");
    }

    public StatoServizio getUltimoStatoMezzo(UUID mezzoId) {

        TypedQuery<StatoServizio> query = em.createQuery(
                "SELECT s FROM StatoServizio s WHERE s.mezzo.mezzo_id = :mezzoId",
                StatoServizio.class);
        query.setParameter("mezzoId", mezzoId);
        query.setMaxResults(1);
        return query.getSingleResult();

    }

}
