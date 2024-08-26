package dariocecchinato.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Il record con ID: " + id + " non è stato trovato");
    }
}
