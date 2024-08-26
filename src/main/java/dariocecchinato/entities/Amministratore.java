package dariocecchinato.entities;

public class Amministratore extends Persona {
    private int Password;


    public Amministratore(int password) {
        Password = password;
    }

    public Amministratore(String nome, String cognome, String email, int eta, int password) {
        super(nome, cognome, email, eta);
        Password = password;
    }

    public Amministratore() {
    }

    public int getPassword() {
        return Password;
    }

    public void setPassword(int password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Amministratore{" +
                "Password=" + Password +
                '}';
    }
}
