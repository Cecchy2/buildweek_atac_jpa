package dariocecchinato.dao;

import dariocecchinato.entities.Vidimato;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
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

    public List<Vidimato> findAll() {
        TypedQuery<Vidimato> query = em.createQuery("SELECT v FROM Vidimato v", Vidimato.class);
        return query.getResultList();
    }

    public Long restituisciNumeroTotaleBigliettiVidimati() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(v) FROM Vidimato v", Long.class);
        return query.getSingleResult();
    }

    public Long restituisciNumeroTotaleBigliettiVidimatiPerMezzo(UUID mezzoId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM Vidimato v JOIN v.giroTratta gt WHERE gt.mezzo.mezzo_id = :mezzoId", Long.class);
        query.setParameter("mezzoId", mezzoId);
        return query.getSingleResult();
    }
}
