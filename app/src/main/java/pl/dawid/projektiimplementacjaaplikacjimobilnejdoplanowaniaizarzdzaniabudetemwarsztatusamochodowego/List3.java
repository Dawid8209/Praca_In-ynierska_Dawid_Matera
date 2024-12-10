package pl.dawid.projektiimplementacjaaplikacjimobilnejdoplanowaniaizarzdzaniabudetemwarsztatusamochodowego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.dawid.yourmotobudget.R;

public class List3 extends AppCompatActivity {
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

        Button buttonAddJobButton = findViewById(R.id.addJobButton);
        buttonAddJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List3.this, List3_1.class);
                startActivity(intent);
            }
        });
    }
}