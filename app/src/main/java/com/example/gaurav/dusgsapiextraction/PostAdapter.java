package com.example.gaurav.dusgsapiextraction;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public String originalLocation;
    public String primaryLocation;
    public String locationOffset;
    public static final String LOCATION_SEPARATOR = " of ";

    Long timeInMilliseconds;
    Date dateObject;
    String dateToDisplay;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy h:mma");
    public String originalTime;
    public static final String TIME_SEPARATOR = " ";
    public String presentDate;
    public String presentTime;

    //    DecimalFormat formatter = new DecimalFormat("0.0");
//   String output;
    TextView magnitudeView;



    private Context context;
    private List<Feature> featureList;

    public PostAdapter(Context context, List<Feature> featureList) {
        this.context = context;
        this.featureList = featureList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {


        final Feature featureLists = featureList.get(position);
//      output = formatter.format(featureLists.getProperties().getMag().toString());
        DecimalFormat df = new DecimalFormat("0.0");
        String formate = df.format(featureLists.getProperties().getMag());
//
        holder.magnitude.setText(formate);




//        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
//
//        // Get the appropriate background color based on the current earthquake magnitude
//        int magnitudeColor = getMagnitudeColor(featureLists.getProperties().getMag());
//
//        // Set the color on the magnitude circle
//        magnitudeCircle.setColor(magnitudeColor);




        originalLocation = featureLists.getProperties().getPlace();
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = "Near the";
            primaryLocation = originalLocation;
        }
        holder.location_offset.setText(locationOffset);
        holder.primary_location.setText(primaryLocation);


        
        timeInMilliseconds = featureLists.getProperties().getTime();
        dateObject = new Date(timeInMilliseconds);
        dateToDisplay = dateFormatter.format(dateObject);


        originalTime = dateToDisplay;
        Log.i("Time Simplified", originalTime);
        if (originalTime.contains(TIME_SEPARATOR)) {
            String[] parts = originalTime.split(TIME_SEPARATOR);
            presentDate = parts[0];
            presentTime = " " + parts[1];

        }
        Log.d(TAG, "present Date :" + presentDate + "\n" + "present Time :" + presentTime);
        holder.date.setText(presentDate);
        holder.time.setText(presentTime);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,web_view.class);
                intent.putExtra("url",featureLists.getProperties().getUrl());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return featureList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView magnitude;
        TextView location_offset;
        TextView primary_location;
        TextView date;
        TextView time;


        public PostViewHolder(View itemView) {
            super(itemView);

            magnitude = (TextView) itemView.findViewById(R.id.magnitude);
            location_offset = (TextView) itemView.findViewById(R.id.location_offset);
            primary_location = (TextView) itemView.findViewById(R.id.primary_location);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            magnitudeView = (TextView) itemView.findViewById(R.id.magnitude);
        }
    }

//    private int getMagnitudeColor(double magnitude) {
//        int magnitudeColorResourceId;
//        int magnitudeFloor = (int) Math.floor(magnitude);
//        switch (magnitudeFloor) {
//            case 0:
//            case 1:
//                magnitudeColorResourceId = R.color.magnitude1;
//                break;
//            case 2:
//                magnitudeColorResourceId = R.color.magnitude2;
//                break;
//            case 3:
//                magnitudeColorResourceId = R.color.magnitude3;
//                break;
//            case 4:
//                magnitudeColorResourceId = R.color.magnitude4;
//                break;
//            case 5:
//                magnitudeColorResourceId = R.color.magnitude5;
//                break;
//            case 6:
//                magnitudeColorResourceId = R.color.magnitude6;
//                break;
//            case 7:
//                magnitudeColorResourceId = R.color.magnitude7;
//                break;
//            case 8:
//                magnitudeColorResourceId = R.color.magnitude8;
//                break;
//            case 9:
//                magnitudeColorResourceId = R.color.magnitude9;
//                break;
//            default:
//                magnitudeColorResourceId = R.color.magnitude10plus;
//                break;
//        }
//        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
//    }


}
