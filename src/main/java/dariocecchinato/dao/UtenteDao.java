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

    public List<Utente> findAll() {
        TypedQuery<Utente> query = em.createQuery("SELECT p FROM Utente p", Utente.class);
        return query.getResultList();
    }

    //*************************************  Metodo Delete  ****************************************

    public void delete(UUID utenteId) {
        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        try {
            //2. avviamo la transazione
            transaction.begin();

            //3. Troviamo l'utente tramite ID
            Utente utente = em.find(Utente.class, utenteId);

            //4. Verifica se l'utente esiste
            if (utente == null) {
                System.out.println("Utente non trovato con ID: " + utenteId);
                transaction.commit();
                return;
            }

            //5. Rimuoviamo l'utente dal persistence context
            em.remove(utente);

            //6. Eseguiamo il commit della transazione se tutto va bene
            transaction.commit();
            System.out.println("L'utente con ID " + utenteId + " è stato cancellato correttamente");
        } catch (Exception e) {
            // Gestione dell'eccezione senza rollback
            System.out.println("Errore durante l'eliminazione dell'utente: " + e.getMessage());
        }
    }


}
