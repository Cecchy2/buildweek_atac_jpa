package dariocecchinato.dao;

import dariocecchinato.entities.Utente;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class UtenteDao {
    private final EntityManager em;

    public UtenteDao(EntityManager em) {
        this.em = em;
    }


    //*************************************  Metodo SAVE  ****************************************

    public Utente ricercaNonnoUtente(String nome, String cognome, int eta) {
        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u WHERE LOWER(u.nome) = :nome AND LOWER(u.cognome) = :cognome AND u.eta = :eta", Utente.class);
        query.setParameter("nome", nome.toLowerCase());
        query.setParameter("cognome", cognome.toLowerCase());
        query.setParameter("eta", eta);
        Utente utente = null;
        try {
            utente = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Utente non trovato o errore: " + e.getMessage());
        }
        return utente;
    }

    public void save(Utente utente) {
        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        try {
            //2. avviamo la transazione
            transaction.begin();

            //3. aggiungo l'utente al persistence context
            em.persist(utente);

            //4. concludiamo la transazione salvando l'utente nel DB
            transaction.commit();

            System.out.println("L' utente " + utente.getNome() + " è stato salvato correttamente");
        } catch (Exception e) {
            // Gestione dell'eccezione senza rollback
            System.out.println("Errore durante il salvataggio dell'utente: " + e.getMessage());
        }
    }

    public Utente getById(UUID utenteId) {
        Utente found = em.find(Utente.class, utenteId);
        if (found == null) throw new NotFoundException(utenteId);
        return found;
    }

    //*************************************  Metodo Delete  ****************************************

    public List<Utente> findAll() {
        TypedQuery<Utente> query = em.createQuery("SELECT p FROM Utente p", Utente.class);
        return query.getResultList();
    }

    public void delete(UUID utenteId) {
        EntityTransaction transaction = em.getTransaction();
        try {
            Utente utente = em.find(Utente.class, utenteId);
            transaction.begin();
            if (utente == null) {
                System.out.println("Utente non trovato con ID: " + utenteId);
                transaction.commit();
                return;
            }
            em.remove(utente);
            transaction.commit();
            System.out.println("L'utente con ID " + utenteId + " è stato cancellato correttamente");
        } catch (Exception e) {
            System.out.println("Errore durante l'eliminazione dell'utente: " + e.getMessage());
        }
    }
}
