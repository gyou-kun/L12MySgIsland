package sg.edu.rp.c346.id20024466.mysgisland;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Island> islandList;
    ArrayAdapter adapter;
    String islandCode;
    Button btn5Stars;
    Button insertIsland;

    ArrayList<String> km;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        islandList.clear();
        islandList.addAll(dbh.getAllIsland());
        adapter.notifyDataSetChanged();

        km.clear();
        km.addAll(dbh.getKm());
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        spinner = (Spinner) this.findViewById(R.id.spinnerYear);
        insertIsland = (Button) this.findViewById(R.id.buttonAdd);

        DBHelper dbh = new DBHelper(this);
        islandList = dbh.getAllIsland();
        km = dbh.getKm();
        dbh.close();


        adapter = new CustomAdapter(this,R.layout.row,islandList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("island", islandList.get(position));
                startActivity(i);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIslandByStars(5));
                adapter.notifyDataSetChanged();
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, km);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIslandByKm(Integer.valueOf(km.get(position))));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insertIsland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input, null);

                final EditText etName = viewDialog.findViewById(R.id.etName);
                final EditText etDesc = viewDialog.findViewById(R.id.etDesc);
                final EditText etKm = viewDialog.findViewById(R.id.etSquareKM);
                final RatingBar ratingBar = viewDialog.findViewById(R.id.ratingBarStars);
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("Insert Island");
                myBuilder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String desc = etDesc.getText().toString().trim();
                        if (name.length() == 0 || desc.length() == 0){
                            Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        String km_str = etKm.getText().toString().trim();
                        int km = 0;
                        try {
                            km = Integer.valueOf(km_str);
                        } catch (Exception e){
                            Toast.makeText(SecondActivity.this, "Invalid Square Kilometer", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DBHelper dbh = new DBHelper(SecondActivity.this);

                        //int stars = getStars();
                        int rating = (int) ratingBar.getRating();
                        dbh.insertIsland(name, desc, km, rating);
                        dbh.close();
                        Toast.makeText(SecondActivity.this, "Island Inserted", Toast.LENGTH_LONG).show();

                        etName.setText("");
                        etDesc.setText("");
                        etKm.setText("");
                    }
                });

                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }

}