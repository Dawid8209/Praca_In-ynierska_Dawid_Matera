package pl.dawid.yourmotobudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.Costs;
import pl.dawid.yourmotobudget.data.CostsAdapter;

public class List1 extends AppCompatActivity {

    private RecyclerView costsView;
    private CostsAdapter adapter;
    private ContactDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list1);

        TextView currentDateText = findViewById(R.id.dataText);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentDateText.setText(currentDate);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List1.this, BookMarks.class);
                startActivity(intent);
            }
        });
        costsView = findViewById(R.id.costsView);
        costsView.setLayoutManager(new LinearLayoutManager(this));

        // Inicjalizacja bazy danych
        database = ContactDatabase.getInstance(this);

        // Załaduj dane
        loadCosts();
    }

    private void loadCosts() {
        if (database == null) {
            Log.e("Database Error", "Database is not initialized");
            return;
        }

        new Thread(() -> {
            String loggedInUserId = getLoggedInEmail(); // Pobierz ID zalogowanego użytkownika

            // Pobierz dane tylko dla zalogowanego użytkownika
            List<Costs> costsList = database.costsDao().getCostsByUserId(loggedInUserId);

            runOnUiThread(() -> {
                if (costsList != null && !costsList.isEmpty()) {
                    // Przekazujemy kontekst do adaptera
                    adapter = new CostsAdapter(costsList, List1.this); // Przekazujemy kontekst
                    costsView.setAdapter(adapter);
                } else {
                    Log.e("Load Data", "Brak danych dla użytkownika");
                }
            });
        }).start();
    }
    private String getLoggedInEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("loggedInEmail", null); // Pobierz ID użytkownika
    }
}