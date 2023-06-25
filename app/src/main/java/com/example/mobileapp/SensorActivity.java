
package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView messageTextView;

    private TextView speedTextView, degreetxt;

    ImageView compassing;

    private long lastTimestamp;
    private float[] velocity = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_activity);

        degreetxt = findViewById(R.id.tvDegrees);
        compassing = findViewById(R.id.compassimg);

        messageTextView = findViewById(R.id.messageTextView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        speedTextView = findViewById(R.id.speedTextView);

        if (accelerometer == null) {
            messageTextView.setText("Your device does not have a linear acceleration sensor.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accelerometer != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            degreetxt.setText(String.format("Degree: %.2f", event.values[0]));
            compassing.setRotation(-event.values[0]);

            messageTextView.setText(String.format("X: %.2f, Y: %.2f, Z: %.2f", x, y, z));

            if (lastTimestamp != 0) {
                float dt = (event.timestamp - lastTimestamp) / 1_000_000_000.0f;
                velocity[0] += x * dt;
                velocity[1] += y * dt;
                velocity[2] += z * dt;

                double speed = Math.sqrt(velocity[0] * velocity[0] + velocity[1] * velocity[1] + velocity[2] * velocity[2]);
                speedTextView.setText(String.format("Speed: %.2f", speed));
            }

            lastTimestamp = event.timestamp;
        } else if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            degreetxt.setText(String.format("Degree: %.2f", event.values[0]));
            compassing.setRotation(-event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}



