package pl.dawid.yourmotobudget;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class List3_1 extends AppCompatActivity {

    private LinearLayout dynamicFieldsContainer;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Zapisz liczbę dynamicznych pól
        outState.putInt("field_count", dynamicFieldsContainer.getChildCount());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Przywróć dynamiczne pola
        int fieldCount = savedInstanceState.getInt("field_count", 0);
        for (int i = 0; i < fieldCount; i++) {
            addNewFields();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list3_1);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List3_1.this, List3.class);
                startActivity(intent);
            }
        });

        Button buttonSave = findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List3_1.this, List3.class);
                startActivity(intent);
            }
        });

        dynamicFieldsContainer = findViewById(R.id.dynamicContainer);
        Button addItemButton = findViewById(R.id.addItemButton);
        
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSION);
        }

        // Obsługa przycisku dodawania pozycji
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewFields();
            }
        });

        Button takePhotoButton = findViewById(R.id.takePhotoButton);
    }

    private void addNewFields() {
        // Utwórz nowy wiersz (kontener) dla pól EditText
        LinearLayout newRow = new LinearLayout(this);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        newRow.setPadding(8, 8, 8, 0);

        // Pierwsze pole EditText (signature5.1a)
        EditText firstField = new EditText(this);
        LinearLayout.LayoutParams firstFieldParams = new LinearLayout.LayoutParams(
                130,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1); // Proporcja
        firstFieldParams.setMargins(16, 16, 16, 0); // Lewy, górny, prawy, dolny margines
        firstField.setLayoutParams(firstFieldParams);
        firstField.setPadding(20, 0, 0, 4);
        firstField.setBackgroundResource(R.drawable.rounded_edittext);
        firstField.setTextColor(getResources().getColor(R.color.black));
        firstField.setTextSize(30);
        firstField.setMinLines(1);
        firstField.setMaxLines(5);
        firstField.setGravity(3);

        // Drugie pole EditText (signature5.1b)
        EditText secondField = new EditText(this);
        LinearLayout.LayoutParams secondFieldParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1); // Proporcja
        secondFieldParams.setMargins(16, 16, 16, 0); // Lewy, górny, prawy, dolny margines
        secondField.setLayoutParams(secondFieldParams);
        secondField.setPadding(25, 0, 0, 4);
        secondField.setBackgroundResource(R.drawable.rounded_edittext);
        secondField.setTextColor(getResources().getColor(R.color.black));
        secondField.setTextSize(30);
        secondField.setGravity(3);

        // Dodanie pól do wiersza
        newRow.addView(firstField);
        newRow.addView(secondField);

        // Dodanie wiersza do kontenera
        dynamicFieldsContainer.addView(newRow);
    }
}