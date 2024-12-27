package pl.dawid.yourmotobudget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.data.ContactDatabase;
import pl.dawid.yourmotobudget.data.UserData;

public class List3_1 extends AppCompatActivity {

    private LinearLayout dynamicFieldsContainer;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath = "";

    private EditText nameField, plateField, vinField, taskField, buyItemField, priceItemField, priceHourField;
    private Button saveButton, backButton;
    private ContactDatabase database;

    private List<String> photoPaths = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("field_count", dynamicFieldsContainer.getChildCount());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int fieldCount = savedInstanceState.getInt("field_count", 0);
        for (int i = 0; i < fieldCount; i++) {
            addNewFields();
        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list3_1);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List3_1.this, BookMarks.class);
                startActivity(intent);
            }
        });

        /*dynamicFieldsContainer = findViewById(R.id.dynamicContainer);
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
        }); */

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSION);
        }

        Button takePhotoButton = findViewById(R.id.takePhotoButton);

        takePhotoButton.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        nameField = findViewById(R.id.signature1_1);
        plateField = findViewById(R.id.signature2_1);
        vinField = findViewById(R.id.signature3_1);
        taskField = findViewById(R.id.signature4_1);
        buyItemField = findViewById(R.id.signature5_1a);
        priceItemField = findViewById(R.id.signature5_1b);
        priceHourField = findViewById(R.id.signature6_1);

        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        database = ContactDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> saveUserData());
        backButton.setOnClickListener(v -> finish());
    }

    private void saveUserData() {
        new Thread(() -> {
            String loggedInEmail = getLoggedInEmail();

            String priceItemFieldString = priceItemField.getText().toString().trim();
            String priceHourFieldString = priceHourField.getText().toString().trim();

            if (nameField == null || nameField.getText().toString().isEmpty() ||
                    taskField == null || taskField.getText().toString().isEmpty() ||
                        priceHourField == null || priceHourField.getText().toString().isEmpty()) {

                runOnUiThread(() ->
                        Toast.makeText(this, "Uzupełnij wszystkie pola z gwiazdką!", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            if (!priceItemFieldString.matches("^[0-9]+([.,][0-9]{1,2})?$")) {

                runOnUiThread(() -> Toast.makeText(this, "Nieprawidłowa cena!", Toast.LENGTH_SHORT).show());
                return;
            }

            if (!priceHourFieldString.matches("^[0-9]+([.,][0-9]{1,2})?$")) {

                runOnUiThread(() -> Toast.makeText(this, "Nieprawidłowa cena!", Toast.LENGTH_SHORT).show());
                return;
            }

            priceItemFieldString = priceItemFieldString.replace(",", ".");
            priceHourFieldString = priceHourFieldString.replace(",", ".");

            Double priceItem = Double.parseDouble(priceItemFieldString);
            Double priceHour = Double.parseDouble(priceHourFieldString);

            UserData user = new UserData();
            user.setName(nameField.getText().toString());
            user.setPlate(plateField.getText().toString());
            user.setVin(vinField.getText().toString());
            user.setTask(taskField.getText().toString());
            user.setBuyItem(buyItemField.getText().toString());
            user.setPriceItem(priceItem);
            user.setPriceHour(priceHour);
            user.setEmail(loggedInEmail);

            if (!currentPhotoPath.isEmpty()) {
                user.setImagePath(currentPhotoPath);
            }

            database.userDataDao().insert(user);
            runOnUiThread(() -> Toast.makeText(this, "Dane zapisane!", Toast.LENGTH_SHORT).show());

        }).start();
    }

    private String getLoggedInEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("loggedInEmail", null);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File photoFile = new File(currentPhotoPath);

            if (photoFile.exists()) {
                photoPaths.add(currentPhotoPath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);

                if (bitmap != null) {
                    addPhotoToContainer(bitmap);

                    Log.d("PhotoAdded", "Dodano zdjęcie: " + photoFile.getName());
                } else {
                    Log.e("Error", "Nie udało się załadować bitmapy.");
                }
            } else {
                Log.e("Error", "Plik zdjęcia nie istnieje: " + currentPhotoPath);
            }
        }
    }

    private void addPhotoToContainer(Bitmap bitmap) {
        LinearLayout photoContainer = findViewById(R.id.photoContainer);

        ImageView newPhotoView = new ImageView(this);

        newPhotoView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newPhotoView.setImageBitmap(bitmap);
        newPhotoView.setAdjustViewBounds(true);
        newPhotoView.setPadding(0, 16, 0, 16);

        photoContainer.addView(newPhotoView);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Kamera wymaga uprawnień do działania.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addNewFields() {
        LinearLayout newRow = new LinearLayout(this);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        newRow.setPadding(8, 8, 8, 0);

        EditText firstField = new EditText(this);
        LinearLayout.LayoutParams firstFieldParams = new LinearLayout.LayoutParams(
                130,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        firstFieldParams.setMargins(16, 16, 16, 0); // Lewy, górny, prawy, dolny margines
        firstField.setLayoutParams(firstFieldParams);
        firstField.setPadding(20, 0, 0, 4);
        firstField.setBackgroundResource(R.drawable.rounded_edittext);
        firstField.setTextColor(getResources().getColor(R.color.black));
        firstField.setTextSize(30);
        firstField.setMinLines(1);
        firstField.setMaxLines(5);
        firstField.setGravity(3);

        EditText secondField = new EditText(this);
        LinearLayout.LayoutParams secondFieldParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        secondFieldParams.setMargins(16, 16, 16, 0); // Lewy, górny, prawy, dolny margines
        secondField.setLayoutParams(secondFieldParams);
        secondField.setPadding(25, 0, 0, 4);
        secondField.setBackgroundResource(R.drawable.rounded_edittext);
        secondField.setTextColor(getResources().getColor(R.color.black));
        secondField.setTextSize(30);
        secondField.setGravity(3);

        newRow.addView(firstField);
        newRow.addView(secondField);

        dynamicFieldsContainer.addView(newRow);
    }
}