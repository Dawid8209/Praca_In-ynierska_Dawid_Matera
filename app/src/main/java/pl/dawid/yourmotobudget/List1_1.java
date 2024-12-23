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
import pl.dawid.yourmotobudget.data.Costs;

public class List1_1 extends AppCompatActivity {

    private EditText nameTextView, costsTextView;
    private Button saveButton, backButton;
    private ContactDatabase database;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list1_1);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List1_1.this, BookMarks.class);
                startActivity(intent);
            }
        });

        // Inicjalizacja pól
        nameTextView = findViewById(R.id.nameTextView);
        costsTextView = findViewById(R.id.costsTextView);

        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        database = ContactDatabase.getInstance(this);

        // Obsługa kliknięcia przycisku "Zapisz"
        saveButton.setOnClickListener(v -> saveCosts());

        // Obsługa kliknięcia przycisku "Cofnij"
        backButton.setOnClickListener(v -> finish());
    }

    private void saveCosts() {
        new Thread(() -> {
            String loggedInEmail = getLoggedInEmail();

            String costsTextViewString = costsTextView.getText().toString().trim();

            // Upewnij się, że pola istnieją i nie są puste
            if (nameTextView == null || nameTextView.getText().toString().isEmpty() ||
                    costsTextView == null || costsTextView.getText().toString().isEmpty()) {

                runOnUiThread(() -> Toast.makeText(this, "Uzupełnij wszystkie pola z gwiazdką!", Toast.LENGTH_SHORT).show());
                return;

            }
            if (!costsTextViewString.matches("^[0-9]+([.,][0-9]{1,2})?$")) {

                runOnUiThread(() -> Toast.makeText(this, "Nieprawidłowa cena!", Toast.LENGTH_SHORT).show());
                return;
            }

            // Utwórz nowy obiekt Costs na podstawie wprowadzonych danych
            try {
                // Zamień przecinek na kropkę, jeśli użytkownik używa przecinka w liczbach zmiennoprzecinkowych
                costsTextViewString = costsTextViewString.replace(",", ".");

                // Spróbuj przekonwertować tekst na Double
                Double costs = Double.parseDouble(costsTextViewString);  // Parsowanie na Double

                // Utwórz obiekt Costs i ustaw wartości
                Costs user = new Costs();
                user.setNameTextView(nameTextView.getText().toString());
                user.setCostsTextView(costs);  // Ustawienie jako Double
                user.setEmail(loggedInEmail);

                database.costsDao().insert(user);
                runOnUiThread(() -> Toast.makeText(this, "Dane zapisane!", Toast.LENGTH_SHORT).show());

            } catch (NumberFormatException e) {
                runOnUiThread(() -> Toast.makeText(this, "Podaj poprawną cenę!", Toast.LENGTH_SHORT).show());
            }

        }).start();
    }

    private String getLoggedInEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("loggedInEmail", null); // Pobierz ID użytkownika
    }
}