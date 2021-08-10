package sg.edu.rp.c346.id20024466.mysgisland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etName, etDesc, etKm;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar RB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));


        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        etID = findViewById(R.id.etID);
        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etKm = findViewById(R.id.etSquareKM);
        RB = findViewById(R.id.ratingBar3);

        Intent i = getIntent();
        final Island currentIsland = (Island) i.getSerializableExtra("Island");

        etID.setText(currentIsland.getId()+"");
        etName.setText(currentIsland.getName());
        etDesc.setText(currentIsland.getDesc());
        etKm.setText(currentIsland.getKm()+"");

        RB.setRating(currentIsland.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentIsland.setName(etName.getText().toString().trim());
                currentIsland.setDesc(etDesc.getText().toString().trim());
                int km = 0;
                try {
                    km = Integer.valueOf(etKm.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(ThirdActivity.this, "Invalid Square km", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentIsland.setKm(km);

                int stars = Integer.valueOf(Math.round(RB.getRating()));
                currentIsland.setStars(stars);
                int result = dbh.updateIsland(currentIsland);
                if (result>0){
                    Toast.makeText(ThirdActivity.this, "Island updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                int result = dbh.deleteIsland(currentIsland.getId());
                if (result>0){
                    Toast.makeText(ThirdActivity.this, "Island deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}