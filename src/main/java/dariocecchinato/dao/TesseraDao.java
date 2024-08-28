package dariocecchinato.dao;

import dariocecchinato.entities.Tessera;
import dariocecchinato.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
        //1.
        EntityTransaction transaction = em.getTransaction();
        //2.
        transaction.begin();
        //3.
        em.persist(tessera);
        //4.
        transaction.commit();

        System.out.println("La tessera " + tessera.getId() + " è stata salvata correttamente");
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
}
