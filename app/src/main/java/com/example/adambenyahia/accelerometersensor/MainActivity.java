package com.example.adambenyahia.accelerometersensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Time;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    Sensor sensor;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    float xOld=0;
    float yOld=0;
    float zOld=0;
    float ThreaShold=3000;
    long OldTime=0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x=event.values[0];
        float y=event.values[1];
        float z=event.values[2];
        long CurrentTime= System.currentTimeMillis();

        if((CurrentTime-OldTime)>100){

            long TimeDiff=CurrentTime-OldTime;
            OldTime=CurrentTime;

            float speed=Math.abs(x+y+z-xOld-yOld-zOld)/TimeDiff *10000;
            if (speed>ThreaShold){
                Vibrator v=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(3000);
                Toast.makeText(getApplicationContext(),"Shock",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
