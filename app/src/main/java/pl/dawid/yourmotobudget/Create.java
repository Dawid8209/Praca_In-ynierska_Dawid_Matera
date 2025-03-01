package pl.dawid.yourmotobudget;

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

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.User;
import pl.dawid.yourmotobudget.data.UserViewModel;

public class Create extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

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
        EditText hasloField = findViewById(R.id.haslo);
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

            if (imie.isEmpty() || nazwisko.isEmpty() || adres.isEmpty() ||
                    nazwaFirmy.isEmpty() || email.isEmpty() || haslo.isEmpty()) {
                Toast.makeText(this, "Wszystkie pola są wymagane!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Podaj poprawny adres e-mail!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (haslo.length() <= 8) {
                Toast.makeText(this, "Hasło musi mieć więcej niż 8 znaków!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!haslo.equals(powtorzHaslo)) {
                Toast.makeText(this, "Hasła nie pasują do siebie!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!haslo.matches(".*[A-Z].*")) {
                Toast.makeText(this, "Hasło musi zawierać co najmniej jedną wielką literę!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!haslo.matches(".*[a-z].*")) {
                Toast.makeText(this, "Hasło musi zawierać co najmniej jedną małą literę!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!haslo.matches(".*\\d.*")) {
                Toast.makeText(this, "Hasło musi zawierać co najmniej jedną cyfrę!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!haslo.matches(".*[!@#\\$%^&*].*")) {
                Toast.makeText(this, "Hasło musi zawierać co najmniej jeden znak specjalny (!@#$%^&*)!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                ContactDatabase db = ContactDatabase.getInstance(getApplicationContext());

                User existingUser = db.userDao().getUserByEmail(email);

                if (existingUser != null) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Konto z tym adresem e-mail już istnieje!", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    User user = new User();
                    user.setImie(imie);
                    user.setNazwisko(nazwisko);
                    user.setAdres(adres);
                    user.setNazwaFirmy(nazwaFirmy);
                    user.setEmail(email);
                    user.setHaslo(haslo);

                    db.userDao().insert(user);

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
