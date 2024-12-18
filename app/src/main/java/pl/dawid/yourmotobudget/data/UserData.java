package pl.dawid.yourmotobudget.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class UserData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String plate;
    private String vin;
    private String task;
    private String buyItem;
    private String priceItem;
    private String priceHour;
    private String email;
    private String imagePath;

        public UserData() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    public String getBuyItem() { return buyItem; }
    public void setBuyItem(String buyItem) { this.buyItem = buyItem; }

    public String getPriceItem() { return priceItem; }
    public void setPriceItem(String priceItem) { this.priceItem = priceItem; }

    public String getPriceHour() { return priceHour; }
    public void setPriceHour(String priceHour) { this.priceHour = priceHour; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
