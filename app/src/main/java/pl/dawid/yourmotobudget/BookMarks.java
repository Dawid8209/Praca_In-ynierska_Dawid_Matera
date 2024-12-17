package pl.dawid.yourmotobudget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.User;

public class BookMarks extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks);

        TextView currentDateText = findViewById(R.id.dataText);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentDateText.setText(currentDate);

        Button buttonCarRepairShop = findViewById(R.id.carRepairShop);
        buttonCarRepairShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List1_1.class);
                startActivity(intent);
            }
        });
        Button buttonViewCarRepairShop = findViewById(R.id.viewCarRepairShop);
        buttonViewCarRepairShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List1.class);
                startActivity(intent);
            }
        });

        Button buttonEmployeeSalary  = findViewById(R.id.employeeSalary);
        buttonEmployeeSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List2_1.class);
                startActivity(intent);
            }
        });
        Button buttonViewEmployeeSalary  = findViewById(R.id.viewEmployeeSalary);
        buttonViewEmployeeSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List2.class);
                startActivity(intent);
            }
        });

        Button buttonOrders = findViewById(R.id.orders);
        buttonOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List3_1.class);
                startActivity(intent);
            }
        });

        Button buttonViewOrders = findViewById(R.id.viewOrders);
        buttonViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List3.class);
                startActivity(intent);
            }
        });

        Button buttonOverallProfit = findViewById(R.id.overallProfit);
        buttonOverallProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List4.class);
                startActivity(intent);
            }
        });

        Button buttonhistory = findViewById(R.id.history);
        buttonhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BookMarks.this, List5.class);
                startActivity(intent);
            }
        });

        Button buttonBack = findViewById(R.id.logout);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                preferences.edit().remove("loggedUserEmail").apply();

                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("loggedInEmail"); // Usuń zapisane ID użytkownika
                editor.apply();

                Toast.makeText(BookMarks.this, "Wylogowano", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BookMarks.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Wyświetlenie okienka potwierdzającego
                new AlertDialog.Builder(BookMarks.this)
                    .setTitle("Potwierdzenie")
                    .setMessage("Czy na pewno chcesz usunąć swoje konto? Tego działania nie da się cofnąć.")
                    .setPositiveButton("Tak", (dialog, which) -> {
                        // Logika usuwania konta po potwierdzeniu
                        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                        String loggedUserEmail = preferences.getString("loggedUserEmail", null);

                        if (loggedUserEmail == null) {
                            Toast.makeText(BookMarks.this, "Nie znaleziono zalogowanego użytkownika!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        new Thread(() -> {
                            ContactDatabase db = ContactDatabase.getInstance(getApplicationContext());
                            User user = db.userDao().getUserByEmail(loggedUserEmail);

                            if (user != null) {
                                db.userDao().delete(user);

                                preferences.edit().remove("loggedUserEmail").apply();

                                runOnUiThread(() -> {
                                    Toast.makeText(BookMarks.this, "Konto zostało usunięte!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(BookMarks.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                });
                            } else {
                                runOnUiThread(() -> Toast.makeText(BookMarks.this, "Nie znaleziono konta w bazie!", Toast.LENGTH_SHORT).show());
                            }
                        }).start();
                    })
                    .setNegativeButton("Nie", (dialog, which) -> {
                        // Anulowanie usuwania
                        Toast.makeText(BookMarks.this, "Usunięcie konta zostało anulowane", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .show();
            }
        });
    }
}