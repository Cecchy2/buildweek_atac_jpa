package dariocecchinato.entities;

public class Amministratore extends Persona {
    private String Password;


    public Amministratore(String password) {
        Password = password;
    }

    public Amministratore(String nome, String cognome, String email, int eta, String password) {
        super(nome, cognome, email, eta);
        Password = password;
    }

    public Amministratore() {
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Amministratore{" +
                "Password=" + Password +
                '}';
    }
}
