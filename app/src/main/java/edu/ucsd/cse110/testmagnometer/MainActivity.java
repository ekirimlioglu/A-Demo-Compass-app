package edu.ucsd.cse110.testmagnometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grab the sensor manager
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //grab the sensor
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //create a listener
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //put the event data to the screen
                String toDisplaySensor = event.values[0]+","+event.values[1]+","+event.values[2];
                String toDisplayAccuracy = event.accuracy+"";

                TextView sensorData = (TextView) findViewById(R.id.sensor);
                TextView accuracyData = (TextView) findViewById(R.id.accuracy);
                TextView degreeData = (TextView) findViewById(R.id.direction);
                ImageView compassImg = (ImageView) findViewById(R.id.imageView);

                double degree = getDegreeFromNorth(event.values);

                sensorData.setText(toDisplaySensor);
                accuracyData.setText(toDisplayAccuracy);

                if(degree<0)
                    degree+=360;
                degreeData.setText(degree+"");
                compassImg.setRotation((int) degree);


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //do something with the accuracy
            }
        };
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
    // An Async task always executes in new thread
        new Thread(new Runnable() {
            public void run()
            {

                // perform any operation
                System.out.println("Performing operation in Asynchronous Task");

                // check if listener is registered.
                if (listener != null) {

                    // invoke the callback method of class A
                    listener.onLocationUpdate();
                }
            }
        }).start();
     */

    /**
     *
     * @author created by private suggestion of github copilot 1.2.0.2322
     * @param magneticFieldValues
     * @return
     */
    protected double getDegreeFromNorth(float[] magneticFieldValues) {
        return Math.toDegrees((float) Math.atan2(magneticFieldValues[0], magneticFieldValues[1]));
    }

}