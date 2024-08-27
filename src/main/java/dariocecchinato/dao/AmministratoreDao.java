package dariocecchinato.dao;

import dariocecchinato.entities.Amministratore;
import dariocecchinato.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AmministratoreDao {
    private final EntityManager em;

    public AmministratoreDao(EntityManager em) {
        this.em = em;
    }

    // Metodo save per salvare un amministratore nel database
    public void save(Amministratore amministratore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(amministratore);
        transaction.commit();
        System.out.println("L'amministratore " + amministratore.getNome() + " è stato salvato correttamente");
    }

    // Metodo findAll per trovare tutti gli amministratori nel database
    public List<Amministratore> findAll() {
        TypedQuery<Amministratore> query = em.createQuery("SELECT a FROM Amministratore a", Amministratore.class);
        return query.getResultList();
    }

    public double calcolaTempoMedioEffettivo2(Tratta tratta) {
        TypedQuery<Double> query = em.createQuery("SELECT AVG(g.tempo_effettivo_percorrenza) FROM GiroTratta g WHERE g.tratta_id = :tratta", Double.class);
        query.setParameter("tratta", tratta);
        return query.getSingleResult();
    }
}
