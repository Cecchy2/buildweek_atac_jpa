package dariocecchinato.dao;

import dariocecchinato.entities.Amministratore;
import dariocecchinato.entities.Mezzo;
import dariocecchinato.entities.Tratta;
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

    public double calcolaTempoMedioEffettivo(Tratta tratta, Mezzo mezzo) {
        TypedQuery<Double> query = em.createQuery("SELECT AVG(g.tempo_effettivo_percorrenza) FROM GiroTratta g WHERE g.tratta_id = :tratta AND g.mezzo = :mezzo", Double.class);
        query.setParameter("tratta", tratta);
        query.setParameter("mezzo", mezzo);
        Double tempoMedioInMinuti = query.getSingleResult();
        if (tempoMedioInMinuti == null) {
            System.out.println("Questo mezzo non ha ancora percorso questa tratta.");
        }
        return tempoMedioInMinuti;
    }

    public double calcolaTempoEffettivo(Tratta tratta) {
        TypedQuery<Long> query = em.createQuery("SELECT g.tempo_effettivo_percorrenza FROM GiroTratta g WHERE g.tratta_id = :tratta", Long.class);
        query.setParameter("tratta", tratta);
        List<Long> tempiEffettivi = query.getResultList();
        if (tempiEffettivi.isEmpty()) {
            throw new NotFoundException("Nessun GiroTratta trovato per la tratta specificata.");
        }
        double somma = 0;
        for (Long tempo : tempiEffettivi) {
            somma += tempo;
        }
        double media = somma / tempiEffettivi.size();
        return media;
    }
}
