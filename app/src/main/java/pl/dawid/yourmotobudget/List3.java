package pl.dawid.yourmotobudget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.UserData;
import pl.dawid.yourmotobudget.data.UserDataAdapter;

public class List3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserDataAdapter adapter;
    private ContactDatabase database;
    private List<UserData> userDataList;

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
                List<UserData> userDataList = database.userDataDao().getAllUserData();

                // Przykład: Ustaw adapter w głównym wątku
            runOnUiThread(() -> {
                if (userDataList != null) {
                    adapter = new UserDataAdapter(userDataList);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Obsługuje przypadek, gdy dane nie są dostępne
                    Log.e("LoadUserData", "Brak danych w bazie.");
                }
            });
        }).start();
    }
}