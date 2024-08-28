package dariocecchinato.dao;

import dariocecchinato.entities.Amministratore;
import dariocecchinato.entities.Persona;
import dariocecchinato.entities.Utente;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class PersonaDao {
    private final EntityManager em;

    public PersonaDao(EntityManager em) {
        this.em = em;
    }

    public Persona findUserOrAdminById(UUID personaId) {
        Utente foundUser = em.find(Utente.class, personaId);
        if (foundUser == null) {
            Amministratore foundAdmin = em.find(Amministratore.class, personaId);
            System.out.println("Benvenuto/a " + foundAdmin.getNome() + " Accesso effettuato come amministratore!");
            return foundAdmin;
        }
        System.out.println("Benvenuto/a utente " + foundUser.getNome());
        return foundUser;
    }
}
