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
import pl.dawid.yourmotobudget.data.UserData;

public class List3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserDataAdapter adapter;
    private ContactDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list3);

        TextView currentDateText = findViewById(R.id.dataText);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentDateText.setText(currentDate);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List3.this, BookMarks.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicjalizacja bazy danych
        database = ContactDatabase.getInstance(this);

        // Załaduj dane
        loadUserData();
    }

    private void loadUserData() {
        if (database == null) {
            Log.e("Database Error", "Database is not initialized");
            return;
        }

        new Thread(() -> {
            String loggedInUserId = getLoggedInEmail(); // Pobierz ID zalogowanego użytkownika

            // Pobierz dane tylko dla zalogowanego użytkownika
            List<UserData> userDataList = database.userDataDao().getUserDataByUserId(loggedInUserId);

            runOnUiThread(() -> {
                if (userDataList != null && !userDataList.isEmpty()) {
                    // Przekazujemy kontekst do adaptera
                    adapter = new UserDataAdapter(userDataList, List3.this); // Przekazujemy kontekst
                    recyclerView.setAdapter(adapter);
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