package pl.dawid.yourmotobudget.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OverallProfitDao {

    @Query("SELECT " +
            "c.costsTextView AS costsTextView, " +
            "s.salaryTextView AS salaryTextView, " +
            "s.bonusTextView AS bonusTextView, " +
            "s.supplementTextView AS supplementTextView, " +
            "u.priceItem AS priceItem, " +
            "u.priceHour AS priceHour " +
            "FROM user_costs c " +
            "LEFT JOIN user_salary s ON c.email = s.email " +
            "LEFT JOIN user_data u ON c.email = u.email " +
            "WHERE c.email = :userId")
    List<OverallProfit> getOverallProfit(String userId);
}
