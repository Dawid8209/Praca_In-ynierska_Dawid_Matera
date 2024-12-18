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
import pl.dawid.yourmotobudget.data.UserSalary;
import pl.dawid.yourmotobudget.data.UserSalaryAdapter;

public class List2 extends AppCompatActivity {

    private RecyclerView salaryView;
    private UserSalaryAdapter adapter;
    private ContactDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list2);

        TextView currentDateText = findViewById(R.id.dataText);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentDateText.setText(currentDate);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List2.this, BookMarks.class);
                startActivity(intent);
            }
        });
        salaryView = findViewById(R.id.salaryView);
        salaryView.setLayoutManager(new LinearLayoutManager(this));

        // Inicjalizacja bazy danych
        database = ContactDatabase.getInstance(this);

        // Załaduj dane
        loadUserSalary();
    }

    private void loadUserSalary() {
        if (database == null) {
            Log.e("Database Error", "Database is not initialized");
            return;
        }

        new Thread(() -> {
            String loggedInUserId = getLoggedInEmail(); // Pobierz ID zalogowanego użytkownika

            // Pobierz dane tylko dla zalogowanego użytkownika
            List<UserSalary> userSalaryList = database.userSalaryDao().getUserSalaryByUserId(loggedInUserId);

            runOnUiThread(() -> {
                if (userSalaryList != null && !userSalaryList.isEmpty()) {
                    // Przekazujemy kontekst do adaptera
                    adapter = new UserSalaryAdapter(userSalaryList, List2.this); // Przekazujemy kontekst
                    salaryView.setAdapter(adapter);
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