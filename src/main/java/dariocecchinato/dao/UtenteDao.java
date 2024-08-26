package dariocecchinato.dao;

import dariocecchinato.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
}
