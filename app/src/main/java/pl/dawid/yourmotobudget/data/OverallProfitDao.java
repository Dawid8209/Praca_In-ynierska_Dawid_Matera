package pl.dawid.yourmotobudget.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OverallProfitDao {

    @Query("SELECT " +
            "COALESCE(SUM(costsTextView), 0) AS costsTextView, " +
            "COALESCE(SUM(salaryTextView), 0) AS salaryTextView, " +
            "COALESCE(SUM(bonusTextView), 0) AS bonusTextView, " +
            "COALESCE(SUM(supplementTextView), 0) AS supplementTextView, " +
            "COALESCE(SUM(priceItem), 0) AS priceItem, " +
            "COALESCE(SUM(priceHour), 0) AS priceHour " +
            "FROM (" +
            "   SELECT costs.costsTextView, NULL AS salaryTextView, NULL AS bonusTextView, NULL AS supplementTextView, NULL AS priceItem, NULL AS priceHour " +
            "   FROM user_costs AS costs WHERE costs.email = :userId " +
            "   UNION ALL " +
            "   SELECT NULL, salary.salaryTextView, salary.bonusTextView, salary.supplementTextView, NULL, NULL " +
            "   FROM user_salary AS salary WHERE salary.email = :userId " +
            "   UNION ALL " +
            "   SELECT NULL, NULL, NULL, NULL, data.priceItem, data.priceHour " +
            "   FROM user_data AS data WHERE data.email = :userId " +
            ")")
    List<OverallProfit> getOverallProfit(String userId);
}
