package pl.dawid.yourmotobudget;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView currentDateText = findViewById(R.id.dataText);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentDateText.setText(currentDate);

        Button nextButton = findViewById(R.id.nextButton);
        Button createButton = findViewById(R.id.createButton);
        EditText nazwaFirmyField = findViewById(R.id.email);
        EditText hasloField = findViewById(R.id.haslo);

        nextButton.setOnClickListener(v -> {
            String nazwaFirmy = nazwaFirmyField.getText().toString();
            String haslo = hasloField.getText().toString();

            if (nazwaFirmy.isEmpty() || haslo.isEmpty()) {
                Toast.makeText(this, "Wszystkie pola są wymagane!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                ContactDatabase db = ContactDatabase.getInstance(getApplicationContext());
                User user = db.userDao().getUserByEmail(nazwaFirmy);

                if (user != null && user.getHaslo().equals(haslo)) {
                    saveLoggedInEmail(user.getEmail());

                    SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("loggedUserEmail", user.getEmail()); // Zakładamy, że email jest unikalny
                    editor.apply();

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Zalogowano pomyślnie!", Toast.LENGTH_SHORT).show();

                        // Przechodzimy do MainActivity
                        Intent intent = new Intent(MainActivity.this, BookMarks.class);
                        startActivity(intent);
                        finish(); // Zamyka bieżącą aktywność
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Nieprawidłowa nazwa firmy lub hasło!", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Create.class);
                startActivity(intent);
            }
        });

        ImageView imageView = findViewById(R.id.myImageView);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageView.getLayoutParams().width = 700;
            imageView.getLayoutParams().height = 600;
        } else {
            imageView.getLayoutParams().width = 800;
            imageView.getLayoutParams().height = 900;
        }
    }
    private void saveLoggedInEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loggedInEmail", email); // Zapisz ID użytkownika
        editor.apply();
    }

    private String getLoggedInEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("loggedInEmail", null); // Pobierz ID użytkownika
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("loggedInUserId"); // Usuń zapisane ID użytkownika
        editor.apply();

        // Przejdź do ekranu logowania
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

