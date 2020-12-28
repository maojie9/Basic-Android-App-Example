package com.example.androidsamplecode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {

    private float lastX, lastY, lastZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        TextView currentX = findViewById(R.id.xAxisValue);
        TextView currentY = findViewById(R.id.yAxisValue);
        TextView currentZ = findViewById(R.id.zAxisValue);

        // get the change of the x,y,z values of the accelerometer
        float deltaX = Math.abs(lastX - sensorEvent.values[0]);
        float deltaY = Math.abs(lastY - sensorEvent.values[1]);
        float deltaZ = Math.abs(lastZ - sensorEvent.values[2]);

        // if the change is below 2, it is just plain noise
        if (deltaX < 2)
            deltaX = 0;
        if (deltaY < 2)
            deltaY = 0;
        if (deltaZ < 2)
            deltaZ = 0;

        // set the last know values of x,y,z
        lastX = sensorEvent.values[0];
        lastY = sensorEvent.values[1];
        lastZ = sensorEvent.values[2];

        //assign the values to display
        currentX.setText(String.format(Locale.ENGLISH, "%f", deltaX));
        currentY.setText(String.format(Locale.ENGLISH, "%f", deltaY));
        currentZ.setText(String.format(Locale.ENGLISH, "%f", deltaZ));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(this);
    }
}