package pl.dawid.yourmotobudget.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_salary")
public class UserSalary {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nameTextView;
    private Double salaryTextView = 0.0;
    private Double bonusTextView = 0.0;
    private Double supplementTextView = 0.0;
    private String email;

    public UserSalary() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNameTextView() { return nameTextView; }
    public void setNameTextView(String nameTextView) { this.nameTextView = nameTextView; }

    public Double getSalaryTextView() { return salaryTextView; }
    public void setSalaryTextView(Double salaryTextView) { this.salaryTextView = salaryTextView; }

    public Double getBonusTextView() { return bonusTextView; }
    public void setBonusTextView(Double bonusTextView) { this.bonusTextView = bonusTextView; }

    public Double getSupplementTextView() { return supplementTextView; }
    public void setSupplementTextView(Double supplementTextView) { this.supplementTextView = supplementTextView; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}