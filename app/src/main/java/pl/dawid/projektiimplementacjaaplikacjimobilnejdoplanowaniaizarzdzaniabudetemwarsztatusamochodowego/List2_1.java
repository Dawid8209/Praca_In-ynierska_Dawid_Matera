package pl.dawid.projektiimplementacjaaplikacjimobilnejdoplanowaniaizarzdzaniabudetemwarsztatusamochodowego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class List2_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list2_1);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List2_1.this, List2.class);
                startActivity(intent);
            }
        });

        Button buttonSave = findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List2_1.this, List2.class);
                startActivity(intent);
            }
        });
    }
}
