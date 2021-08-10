package sg.edu.rp.c346.id20024466.mysgisland;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Island> IslandList;

    public CustomAdapter(Context context, int resource, ArrayList<Island> objects)
    {
        super(context,resource,objects);

        this.parent_context = context;
        this.layout_id = resource;
        this.IslandList = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvKm = rowView.findViewById(R.id.textViewYear);
        TextView tvDesc = rowView.findViewById(R.id.textViewSinger);
        ImageView ivImage = rowView.findViewById(R.id.imageView);
        RatingBar rbrating = rowView.findViewById(R.id.ratingBar2);

        // Obtain the Android Version information based on the position
        Island currentSong = IslandList.get(position);

        // Set values to the TextView to display the corresponding information

        tvName.setText(currentSong.getName());
        tvKm.setText(currentSong.toStringKm());
        rbrating.setRating(currentSong.getStars());


        tvDesc.setText(currentSong.getDesc());

        if(currentSong.getKm() >= 3)
        {
            ivImage.setImageResource(R.drawable.wow);
        }
        else
        {
            ivImage.setVisibility(View.INVISIBLE);
        }

        return rowView;
    }
}

