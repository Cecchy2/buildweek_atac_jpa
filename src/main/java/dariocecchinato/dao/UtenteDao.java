package dariocecchinato.dao;

import dariocecchinato.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UtenteDao {
    private final EntityManager em;

    public UtenteDao(EntityManager em) {
        this.em = em;
    }

    //*************************************  Metodo SAVE  ****************************************
    public void save(Utente utente) {
        //1.
        EntityTransaction transaction = em.getTransaction();
        //2.
        transaction.begin();
        //3.
        em.persist(utente);
        //4.
        transaction.commit();

        System.out.println("L' utente " + utente.getNome() + " è stato salvato correttamente");
    }

    public List<Utente> findAll() {
        TypedQuery<Utente> query = em.createQuery("SELECT p FROM Utente p", Utente.class);
        return query.getResultList();
    }
}
