package pl.dawid.yourmotobudget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.UserSalary;

public class List2_1 extends AppCompatActivity {

    private EditText nameTextView, salaryTextView, bonusTextView, supplementTextView;
    private Button saveButton, backButton;
    private ContactDatabase database;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list2_1);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List2_1.this, BookMarks.class);
                startActivity(intent);
            }
        });

        // Inicjalizacja pól
        nameTextView = findViewById(R.id.nameTextView);
        salaryTextView = findViewById(R.id.salaryTextView);
        bonusTextView = findViewById(R.id.bonusTextView);
        supplementTextView = findViewById(R.id.supplementTextView);

        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        database = ContactDatabase.getInstance(this);

        // Obsługa kliknięcia przycisku "Zapisz"
        saveButton.setOnClickListener(v -> saveUserSalary());

        // Obsługa kliknięcia przycisku "Cofnij"
        backButton.setOnClickListener(v -> finish());
    }

    private void saveUserSalary() {
        new Thread(() -> {
            String loggedInEmail = getLoggedInEmail();

            // Upewnij się, że pola istnieją i nie są puste
            if (nameTextView == null || nameTextView.getText().toString().isEmpty() ||
                    salaryTextView == null || salaryTextView.getText().toString().isEmpty()) {

                runOnUiThread(() ->
                        Toast.makeText(this, "Uzupełnij wszystkie pola z gwiazdką!", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            // Utwórz nowy obiekt UserSalary na podstawie wprowadzonych danych
            UserSalary user = new UserSalary();
            user.setNameTextView(nameTextView.getText().toString());
            user.setSalaryTextView(salaryTextView.getText().toString());
            user.setBonusTextView(bonusTextView.getText().toString());
            user.setSupplementTextView(supplementTextView.getText().toString());
            user.setEmail(loggedInEmail);

            // Zapisz dane do bazy
            database.userSalaryDao().insert(user);
            runOnUiThread(() -> Toast.makeText(this, "Dane zapisane!", Toast.LENGTH_SHORT).show());

        }).start();
    }

    private String getLoggedInEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("loggedInEmail", null); // Pobierz ID użytkownika
    }
}