package pl.dawid.yourmotobudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.OverallProfit;
import pl.dawid.yourmotobudget.data.OverallProfitAdapter;
import pl.dawid.yourmotobudget.data.OverallProfitDao;

public class List4 extends AppCompatActivity {

    private RecyclerView profitView;
    private OverallProfitAdapter adapter;
    private OverallProfitDao overallProfitDao;
    private ContactDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list4);

        TextView currentDateText = findViewById(R.id.dataText);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentDateText.setText(currentDate);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List4.this, BookMarks.class);
                startActivity(intent);
            }
        });

        profitView = findViewById(R.id.profitView);
        profitView.setLayoutManager(new LinearLayoutManager(this));

        database = ContactDatabase.getInstance(this);
        if (database != null) {
            overallProfitDao = database.overallProfitDao();
        } else {
            Log.e("List4", "Database initialization failed!");
        }
        loadProfit();
    }

    private void loadProfit() {
        String loggedInUserId = getLoggedInEmail();

        new Thread(() -> {
            List<OverallProfit> dataList = overallProfitDao.getOverallProfit(loggedInUserId);
            Log.d("Database", "Pobrano dane: " + dataList);

            runOnUiThread(() -> {
                if (dataList != null && !dataList.isEmpty()) {

                    adapter = new OverallProfitAdapter(dataList);
                    profitView.setAdapter(adapter);
                } else {
                    Log.e("Load Data", "Brak danych dla użytkownika");
                }
            });
        }).start();
    }

    private String getLoggedInEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("loggedInEmail", null);
    }
}
