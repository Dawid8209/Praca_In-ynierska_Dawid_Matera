package pl.dawid.yourmotobudget.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_salary")
public class UserSalary {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nameTextView;
    private String salaryTextView;
    private String bonusTextView;
    private String supplementTextView;
    private String email;

    public UserSalary() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNameTextView() { return nameTextView; }
    public void setNameTextView(String nameTextView) { this.nameTextView = nameTextView; }

    public String getSalaryTextView() { return salaryTextView; }
    public void setSalaryTextView(String salaryTextView) { this.salaryTextView = salaryTextView; }

    public String getBonusTextView() { return bonusTextView; }
    public void setBonusTextView(String bonusTextView) { this.bonusTextView = bonusTextView; }

    public String getSupplementTextView() { return supplementTextView; }
    public void setSupplementTextView(String supplementTextView) { this.supplementTextView = supplementTextView; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}