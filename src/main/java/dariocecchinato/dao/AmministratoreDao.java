package dariocecchinato.dao;

import dariocecchinato.entities.Amministratore;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

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

    public Amministratore getById(UUID amministratoreId) {
        Amministratore found = em.find(Amministratore.class, amministratoreId);
        if (found == null) throw new NotFoundException(amministratoreId);
        return found;
    }
}
