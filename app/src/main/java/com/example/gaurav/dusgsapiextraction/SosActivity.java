package com.example.gaurav.dusgsapiextraction;

import android.Manifest;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;

public class SosActivity extends Activity implements LocationListener {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    MobileServiceClient mClient;
    LocationManager locationManager;
    Location currentLocation;
    String phoneNo;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        try {
            mClient = new MobileServiceClient(
                    "https://earthquake-sos.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        sendBtn = (Button) findViewById(R.id.button);
        txtphoneNo = (EditText) findViewById(R.id.editText);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getLocation();
                sendSMSMessage();
            }
        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TodoItem item = new TodoItem();
        item.Text = "Location: "+location.getLatitude()+","+location.getLongitude();
        mClient.getTable(TodoItem.class).insert(item, new TableOperationCallback<TodoItem>() {
            public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                } else {
                    // Insert failed
                }
            }
        });
        currentLocation = location;
    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(SosActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        if(currentLocation == null) {
            message = "Please Help Me. I have been stuck by an EarthQuake here!";
        }
        else {
            message = "Please Help Me. I have been stuck by an EarthQuake here!" + "http://maps.google.com?q="+currentLocation.getLatitude()+","+currentLocation.getLongitude();
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}