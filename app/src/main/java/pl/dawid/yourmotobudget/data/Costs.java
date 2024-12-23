package pl.dawid.yourmotobudget.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_costs")
public class Costs {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nameTextView;
    private Double costsTextView;
    private String email;

    public Costs() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNameTextView() { return nameTextView; }
    public void setNameTextView(String nameTextView) { this.nameTextView = nameTextView; }

    public Double getCostsTextView() { return costsTextView; }
    public void setCostsTextView(Double costsTextView) { this.costsTextView = costsTextView; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}