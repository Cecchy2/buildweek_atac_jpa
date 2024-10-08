package dariocecchinato.dao;

import dariocecchinato.entities.Tessera;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TesseraDao {
    private final EntityManager em;

    public TesseraDao(EntityManager em) {
        this.em = em;
    }

    public void save(Tessera tessera) {
        //1. chiedo all'entity manager di fornire una transazione
        EntityTransaction transaction = em.getTransaction();

        try {
            //2. avviamo la transazione
            transaction.begin();

            //3. aggiungo l'evento al persistence context
            em.persist(tessera);

            //4. concludiamo la transazione salvando l'evento nel DB
            transaction.commit();

            System.out.println("La tessera " + tessera.getId() + " è stata salvata correttamente");
        } catch (Exception e) {
            // Gestione dell'eccezione senza rollback
            System.out.println("Errore durante il salvataggio della tessera: " + e.getMessage());
        }
    }

    public Tessera getById(UUID idTessera) {
        Tessera found = em.find(Tessera.class, idTessera);
        if (found == null) {
            throw new NotFoundException(idTessera);
        } else {
            return found;
        }
    }

    public boolean isTesseraValida(UUID idTessera) {
        Tessera tessera = getById(idTessera);
        return tessera != null && LocalDate.now().isBefore(tessera.getData_scadenza());
    }

    public List<Tessera> findAll() {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t", Tessera.class);
        return query.getResultList();
    }

    public void updateDataEmissioneTessera(UUID id, LocalDate newDate) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query updateQuery = em.createQuery("UPDATE Tessera t SET t.data_emissione = :new_date WHERE t.id = :id");
        updateQuery.setParameter("new_date", newDate);
        updateQuery.setParameter("id", id);
        transaction.commit();
    }


}
