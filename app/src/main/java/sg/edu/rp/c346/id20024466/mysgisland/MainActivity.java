package sg.edu.rp.c346.id20024466.mysgisland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText etName, etDesc, etKm;
    Button btnInsert, btnShowList;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etKm = findViewById(R.id.etSquareKM);
        btnInsert = findViewById(R.id.btnInsertIsland);
        btnShowList = findViewById(R.id.btnShowList);
        rb = findViewById(R.id.ratingBar);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etName.getText().toString().trim();
                String singers = etDesc.getText().toString().trim();
                if (title.length() == 0 || singers.length() == 0){
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }


                String year_str = etKm.getText().toString().trim();
                int year = 0;
                try {
                    year = Integer.valueOf(year_str);
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid Square km", Toast.LENGTH_SHORT).show();
                    return;
                }


                DBHelper dbh = new DBHelper(MainActivity.this);

                int stars = (int) getStars();
                dbh.insertIsland(title, singers, year, stars);
                dbh.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();

                etName.setText("");
                etDesc.setText("");
                etKm.setText("");

            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });


    }
    private float getStars() {
        float stars = (int) rb.getRating();
        return stars;


    }







}
