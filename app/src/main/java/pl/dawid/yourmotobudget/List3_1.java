package pl.dawid.yourmotobudget;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class List3_1 extends AppCompatActivity {

    private LinearLayout dynamicFieldsContainer;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath = "";
    private TextView photoNameTextView;

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

        // Prośba o uprawnienia
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSION);
        }

        Button takePhotoButton = findViewById(R.id.takePhotoButton);
        photoNameTextView = findViewById(R.id.photoNameTextView);

        takePhotoButton.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Obsługa błędu
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "pl.dawid.yourmotobudget.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File photoFile = new File(currentPhotoPath);
            photoNameTextView.setText(photoFile.getName()); // Wyświetlanie nazwy zdjęcia
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Uprawnienie nadane, uruchom aparat
                dispatchTakePictureIntent();
            } else {
                // Uprawnienie odmówione
                Toast.makeText(this, "Kamera wymaga uprawnień do działania.", Toast.LENGTH_SHORT).show();
            }
        }
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