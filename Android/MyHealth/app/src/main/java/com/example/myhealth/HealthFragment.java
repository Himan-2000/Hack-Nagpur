package com.example.myhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.SENSOR_SERVICE;

public class HealthFragment extends Fragment {
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    ProgressBar stepProgress, waterProgress, sleepProgress;
    TextView sleepTime, startTime, stopTime;
    ImageButton startBtn,stopBtn;
    ImageButton addWater, subWater;
    int waterG=0;
    String userInfo = "userInfo",
            date="dateStamp",
            steps_taken="steps",
            water_taken="water",
            start_time="startTime",
            start_timeHM="startTimeHM",
            stop_time="stopTime",
            bed_time="bedtime";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_health, null );
        final TextView stepCounter = v.findViewById( R.id.stepCounter );
        final TextView waterCounter = v.findViewById( R.id.waterCounter );
        stepProgress = v.findViewById( R.id.steps_progress );
        waterProgress = v.findViewById( R.id.water_progress );
        sleepProgress = v.findViewById( R.id.sleep_progress );
        addWater = v.findViewById( R.id.addButton );
        subWater = v.findViewById( R.id.subtractButton );
        sleepTime = v.findViewById( R.id.sleep_time );
        startTime = v.findViewById( R.id.startTime );
        stopTime = v.findViewById( R.id.stopTime );
        startBtn = v.findViewById( R.id.startTimeBtn );
        stopBtn = v.findViewById( R.id.stopTimeBtn );

        final SharedPreferences userData = getActivity().getSharedPreferences(userInfo, Context.MODE_PRIVATE );
        Calendar c = Calendar.getInstance();
        //SimpleDateFormat sf = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss a", Locale.getDefault());
        SimpleDateFormat sf = new SimpleDateFormat( "dd-MM-yyyy", Locale.getDefault());

        String d = sf.format( c.getTime() );

        String ts = userData.getString( date,"null" );
        if(ts.equalsIgnoreCase( "null" )|| !(ts.equalsIgnoreCase( d ))){
            userData.edit().putString( date,d ).apply();
            userData.edit().putInt( steps_taken,0 ).apply();
            stepCounter.setText("0 Steps\nToday");
            stepProgress.setProgress( 0 );
            userData.edit().putInt( water_taken,0 ).apply();
            waterCounter.setText( "0 Glasses of Water" );
            waterProgress.setProgress( waterG );
            sleepTime.setText( "No Information Available" );
            sleepProgress.setProgress( 0 );
            startTime.setText( "-- : --" );
            stopTime.setText( "-- : --" );
            //userData.edit().putString( start_time,"-- : --" );
            //userData.edit().putString( stop_time,"-- : --" );
            userData.edit().putInt( bed_time,0 );
        }
        else{
            waterG = userData.getInt( water_taken,0 );
            waterCounter.setText( waterG+" Glasses of Water" );
            waterProgress.setProgress( waterG );
            stepCount = userData.getInt( steps_taken,0 );
            stepCounter.setText(stepCount.toString()+" Steps\nToday");
            String st = userData.getString( start_timeHM, "-- : --" );
            startTime.setText( st );
            int bt = userData.getInt( bed_time,0 );
            if(bt == 0){
                sleepTime.setText( "No Information Available" );
                sleepProgress.setProgress( 0 );
            } else {
                sleepTime.setText( "You have slept for " + bt + " hours" );
                sleepProgress.setProgress( bt );
            }
        }

        startBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sf1 = new SimpleDateFormat( "HH", Locale.getDefault());
                SimpleDateFormat sf2 = new SimpleDateFormat( "HH:mm", Locale.getDefault());
                startTime.setText( sf2.format( c.getTime() ) );
                String h = sf1.format( c.getTime() );
                userData.edit().putString( start_time,h ).apply();
                userData.edit().putString( start_timeHM,sf2.format( c.getTime() ) ).apply();
            }
        } );
        stopBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hstart=0,hstop=0,diff=0;
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sf1 = new SimpleDateFormat( "HH", Locale.getDefault());
                SimpleDateFormat sf2 = new SimpleDateFormat( "HH:mm", Locale.getDefault());
                stopTime.setText( sf2.format( c.getTime() ) );
                String h = sf1.format( c.getTime() );
                String hs = userData.getString( start_time,"-- : --" );

                if(!hs.equals( "-- : --" )){
                    hstart = Integer.valueOf( hs );
                    hstop = Integer.valueOf( h );
                    if(hstart>hstop){
                        diff = 24-hstart;
                        diff = diff+hstop;
                    }
                    else{
                        diff = hstop-hstart;
                    }
                    if(diff<1){
                        sleepTime.setText( "You slept for less than 1 hour" );
                        sleepProgress.setProgress( 1 );
                    } else{
                        sleepTime.setText( "You slept for approx "+diff+" hours" );
                        sleepProgress.setProgress( diff );
                        userData.edit().putInt( bed_time,diff ).apply();
                    }
                }
                userData.edit().putString( start_timeHM,"-- : --" ).apply();

            }
        } );

        addWater.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterG = waterG+1;
                userData.edit().putInt( water_taken,waterG ).apply();
                waterG = userData.getInt( water_taken,0 );
                waterCounter.setText( waterG+" Glasses of Water" );
                waterProgress.setProgress( waterG );
            }
        } );
        subWater.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(waterG-1 >= 0) {
                    waterG = waterG - 1;
                    userData.edit().putInt( water_taken,waterG ).apply();
                    waterG = userData.getInt( water_taken,0 );
                    waterCounter.setText( waterG + " Glasses of Water" );
                    waterProgress.setProgress( waterG );
                }
            }
        } );
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent!= null){
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];

                    double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                    double MagnitudeDelta = Magnitude-MagnitudePrevious;
                    MagnitudePrevious = Magnitude;

                    if (MagnitudeDelta > 5){
                        stepCount++;
                        userData.edit().putInt( steps_taken,stepCount ).apply();
                        stepCount = userData.getInt( steps_taken,0 );
                    }
                    stepCounter.setText(stepCount.toString()+" Steps\nToday");
                    stepProgress.setProgress( (int) stepCount );
                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return v;
    }
}