package dariocecchinato.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Utente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

}
