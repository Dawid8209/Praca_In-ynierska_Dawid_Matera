package pl.dawid.projektiimplementacjaaplikacjimobilnejdoplanowaniaizarzdzaniabudetemwarsztatusamochodowego;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.dawid.projektiimplementacjaaplikacjimobilnejdoplanowaniaizarzdzaniabudetemwarsztatusamochodowego.data.ContactDatabase;
import pl.dawid.projektiimplementacjaaplikacjimobilnejdoplanowaniaizarzdzaniabudetemwarsztatusamochodowego.data.User;
import pl.dawid.yourmotobudget.R;

public class Create extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);  // Przypisanie drugiego layoutu

        TextView currentDateText = findViewById(R.id.dataText);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentDateText.setText(currentDate);

        Button backButton = findViewById(R.id.backButton);
        Button createButton = findViewById(R.id.creatButton);
        EditText imieField = findViewById(R.id.imie);
        EditText nazwiskoField = findViewById(R.id.nazwisko);
        EditText adresField = findViewById(R.id.adres);
        EditText nazwaFirmyField = findViewById(R.id.nazwaFirmy);
        EditText emailField = findViewById(R.id.email);
        EditText hasloField = findViewById(R.id.hasło);
        EditText powtorzHasloField = findViewById(R.id.powtorzHaslo);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        User newUser = null;
        userViewModel.insertUser(newUser).thenRun(() -> {
            runOnUiThread(() -> {
                Toast.makeText(this, "Użytkownik zapisany!", Toast.LENGTH_SHORT).show();
            });
        });

        createButton.setOnClickListener(v -> {
            String imie = imieField.getText().toString();
            String nazwisko = nazwiskoField.getText().toString();
            String adres = adresField.getText().toString();
            String nazwaFirmy = nazwaFirmyField.getText().toString();
            String email = emailField.getText().toString();
            String haslo = hasloField.getText().toString();
            String powtorzHaslo = powtorzHasloField.getText().toString();

            // Walidacja
            if (imie.isEmpty() || nazwisko.isEmpty() || adres.isEmpty() ||
                    nazwaFirmy.isEmpty() || email.isEmpty() || haslo.isEmpty()) {
                Toast.makeText(this, "Wszystkie pola są wymagane!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!haslo.equals(powtorzHaslo)) {
                Toast.makeText(this, "Hasła nie pasują do siebie!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Wstawienie do bazy danych (w osobnym wątku)
            new Thread(() -> {
                ContactDatabase db = ContactDatabase.getInstance(getApplicationContext());

                // Sprawdzanie, czy użytkownik o podanym e-mailu już istnieje
                User existingUser = db.userDao().getUserByEmail(email);

                // Jeśli użytkownik istnieje, wyświetlamy komunikat i kończymy
                if (existingUser != null) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Konto z tym adresem e-mail już istnieje!", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    // Tworzenie nowego użytkownika
                    User user = new User();
                    user.setImie(imie);
                    user.setNazwisko(nazwisko);
                    user.setAdres(adres);
                    user.setNazwaFirmy(nazwaFirmy);
                    user.setEmail(email);
                    user.setHaslo(haslo);

                    // Wstawienie do bazy danych
                    db.userDao().insert(user);

                    // Powiadomienie o utworzeniu konta
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Konto zostało utworzone!", Toast.LENGTH_SHORT).show();
                    });
                    finish();
                }
            }).start();
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
