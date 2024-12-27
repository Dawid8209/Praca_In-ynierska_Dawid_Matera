package pl.dawid.yourmotobudget.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imie;
    private String nazwisko;
    private String adres;
    private String nazwaFirmy;
    private String email;
    private String haslo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }

    public String getNazwisko() { return nazwisko; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }

    public String getAdres() { return adres; }
    public void setAdres(String adres) { this.adres = adres; }

    public String getNazwaFirmy() { return nazwaFirmy; }
    public void setNazwaFirmy(String nazwaFirmy) { this.nazwaFirmy = nazwaFirmy; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHaslo() { return haslo; }
    public void setHaslo(String haslo) { this.haslo = haslo; }
}