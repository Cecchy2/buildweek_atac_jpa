package dariocecchinato.dao;

import dariocecchinato.entities.Amministratore;
import dariocecchinato.entities.Utente;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class PersonaDao {
    private final EntityManager em;

    public PersonaDao(EntityManager em) {
        this.em = em;
    }

    //*************************************  Metodo findUserOrAdminById  ****************************************
    public void findUserOrAdminById(UUID personaId) {
        Utente foundUser = em.find(Utente.class, personaId);
        if (foundUser == null) {
            Amministratore foundAdmin = em.find(Amministratore.class, personaId);
            System.out.println("Benvenuto/a " + foundAdmin.getNome() + " Accesso effettuato come amministratore!");
            /*add method menuAdmin*/
        }
        System.out.println("Benvenuto/a utente " + foundUser.getNome());

    }
}
