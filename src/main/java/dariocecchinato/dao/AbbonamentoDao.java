package dariocecchinato.dao;

import dariocecchinato.entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AbbonamentoDao {
    private final EntityManager em;

    public AbbonamentoDao(EntityManager em) {
        this.em = em;
    }


    //*************************************  Metodo save  ****************************************
    public void save(Abbonamento abbonamento) {
        //1.
        EntityTransaction transaction = em.getTransaction();
        //2.
        transaction.begin();
        //3.
        em.persist(abbonamento);
        //4.
        transaction.commit();

        System.out.println("L' abbonamento " + abbonamento.getTipo_abbonamento() + " è stato salvato correttamente");
    }

    //*************************************  Metodo findAll  ****************************************
    public List<Abbonamento> findAll() {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT p FROM Abbonamento p", Abbonamento.class);
        return query.getResultList();
    }
}
