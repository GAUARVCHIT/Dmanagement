package com.example.gaurav.dusgsapiextraction;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
//    Long timeInMilliseconds;
//    Date dateObject;
//    String dateToDisplay;
//    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd \"h:mm a\"");

    Calendar cal = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String baseUrl = "https://earthquake.usgs.gov/fdsnws/event/1/";


//    public String originalLocation;
//    public String primaryLocation;
//    public String locationOffset;
//    public static final String LOCATION_SEPARATOR = " of ";

      private String originalLocation;
    private String primaryLocation;
    private String locationOffset;
    private static final String LOCATION_SEPARATOR = " of ";


    RecyclerView recyclerView;
    TextView magnitudeView;

    List<Feature> featureList;
    ArrayList<String> Latitude=new ArrayList<String>();
    ArrayList<String> Longitude=new ArrayList<String>();
    ArrayList<String> Dlocation=new ArrayList<String>();
    ArrayList<String> Magnitude=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getdata();

    }

    private void getdata() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        usgsapi dusgsapi = retrofit.create(usgsapi.class);
        Call<Example> call = dusgsapi.getpostlist("geojson", dateFormat.format(cal.getTime()), dateFormat.format(yesterday()), 1, 300);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d("RESPONSE", "onResponse: Server Response : " + response.toString());
                Log.d("PRINT", "onResponse: Received Response : " + response.body().toString());

                Example list= response.body();
                recyclerView.setAdapter(new PostAdapter(MainActivity.this,list.getFeatures()));
                featureList = response.body().getFeatures();
//                for (int i = 0; i < featureList.size(); i++) {
//                    timeInMilliseconds = featureList.get(i).getProperties().getTime();
//                    dateObject = new Date(timeInMilliseconds);
//                    dateToDisplay = dateFormatter.format(dateObject);
//                    Log.i("Time Simplified", dateToDisplay);


//                    originalLocation=featureList.get(i).getProperties().getPlace();
//                    if (originalLocation.contains(LOCATION_SEPARATOR)) {
//                        String[] parts = originalLocation.split(LOCATION_SEPARATOR);
//                        locationOffset = parts[0] + LOCATION_SEPARATOR;
//                        primaryLocation = parts[1];
//                    } else {
//                        locationOffset = getString(R.string.near_the);
//                        primaryLocation = originalLocation;
//                    }
//                    Log.d("offset", locationOffset+"\n"+"................................................................");
//                    Log.d("primaryLocation", primaryLocation);



//                    Log.d("AWESOME ", "onResponse: \n" + "mag =" + featureList.get(i).getProperties().getMag() + "\n" +
//                            "place =" + featureList.get(i).getProperties().getPlace() + "\n" + "time =" + featureList.get(i).getProperties().getTime() + "\n" +
//                            "latitude =" + featureList.get(i).getGeometry().getCoordinates().get(0) + "\n" + "longitude =" + featureList.get(i).getGeometry().getCoordinates().get(1) + "\n" +
//                            "..............................................................................\n\n\n");

                }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("ERROR", "SOMETHING WENT WRONG" + t.getMessage());
                Toast.makeText(MainActivity.this, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void list(){
        for(int i=0;i<featureList.size();i++){
            originalLocation=featureList.get(i).getProperties().getPlace();
                    if (originalLocation.contains(LOCATION_SEPARATOR)) {
                        String[] parts = originalLocation.split(LOCATION_SEPARATOR);
                        locationOffset = parts[0] + LOCATION_SEPARATOR;
                        primaryLocation = parts[1];
                    } else {
                        locationOffset = getString(R.string.near_the);
                        primaryLocation = originalLocation;
                    }
                    Log.d("offset", locationOffset+"\n"+"................................................................");
                    Log.d("primaryLocation", primaryLocation);



                    Log.d("AWESOME ", "onResponse: \n" + "mag =" + featureList.get(i).getProperties().getMag() + "\n" +
                            "place =" + featureList.get(i).getProperties().getPlace() + "\n" + "time =" + featureList.get(i).getProperties().getTime() + "\n" +
                            "latitude =" + featureList.get(i).getGeometry().getCoordinates().get(1) + "\n" + "longitude =" + featureList.get(0).getGeometry().getCoordinates().get(1) + "\n" +
                            "..............................................................................\n\n\n");
                    Latitude.add(featureList.get(i).getGeometry().getCoordinates().get(1).toString());
                    Longitude.add(featureList.get(i).getGeometry().getCoordinates().get(0).toString());
                    Dlocation.add(featureList.get(i).getProperties().getPlace());
                    Magnitude.add(featureList.get(i).getProperties().getMag().toString());



                        Log.d(" The ", "list: "+ Latitude.get(i)+"\n"+Longitude.get(i));


        }
    }


    private Date yesterday() {
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mapicon,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        list();
        intent.putStringArrayListExtra("latitude",Latitude);
        intent.putStringArrayListExtra("longitude",Longitude);
        intent.putStringArrayListExtra("Dlocation",Dlocation);
        intent.putStringArrayListExtra("Magnitude",Magnitude);


        startActivity(intent);

        return super.onOptionsItemSelected(item);



    }





}

