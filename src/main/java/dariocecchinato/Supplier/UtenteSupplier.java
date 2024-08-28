package dariocecchinato.Supplier;

import com.github.javafaker.Faker;
import dariocecchinato.entities.Utente;

import java.util.function.Supplier;

public class UtenteSupplier implements Supplier<Utente> {
    Faker f = new Faker();

    @Override
    public Utente get() {
        String nomeUtente = f.name().firstName();
        String cognomeUtente = f.name().lastName();
        String email = f.internet().emailAddress();
        int eta = f.number().numberBetween(14, 88);
        String zone_di_residenza = f.address().fullAddress();
        return new Utente(nomeUtente, cognomeUtente, email, eta, zone_di_residenza);
    }
}
