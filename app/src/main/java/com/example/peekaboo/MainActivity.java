package com.example.peekaboo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Sensor sp;
    private SensorManager sm;
    ObjectAnimator an1,an2;
    public ImageView tuImageView;

    SensorEventListener sel=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.values[0]<sp.getMaximumRange()){
                tuImageView.setImageResource(R.drawable.aqui_ta);
            }
            else{
                tuImageView.setImageResource(R.drawable.onta_bb);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tuImageView=(ImageView)findViewById(R.id.imageView);
        obtenerSensor();
        an1=ObjectAnimator.ofFloat(tuImageView,"translationX",300f);
        an1.setDuration(1000);
        an2=ObjectAnimator.ofFloat(tuImageView,"translationX",-300f);
        an2.setDuration(1000);
    }

    private void obtenerSensor(){

        sm= (SensorManager)getSystemService(SENSOR_SERVICE);
        sp=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(sp==null){
            Toast.makeText(this,"No tienes sensor",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sel,sp,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sel);
    }
}
