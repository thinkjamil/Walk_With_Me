package com.hp.jam.walkwithme;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText ageEt,weightEt,heightEt;
    TextView textView1;
    int steps=0,displacement=7;
    float prevz=-400;
    double perStepCalorieBurn=0.00016159215;
    int age=0,weight=0;
    float height=0;
    SensorManager sm = null;
    List list;
    SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
			/* Isn't required for this example */

        }
        public void onSensorChanged(SensorEvent event) {
			/* Write the accelerometer values to the TextView */
            float[] values = event.values;
            textView1.setText("Total Steps : " + steps);
            if(prevz==-400);
            else if((prevz-values[2])>displacement){
                steps++;
            }
            prevz=values[2];
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ageEt= (EditText)findViewById(R.id.age);
        weightEt=(EditText)findViewById(R.id.weight);
        heightEt=(EditText)findViewById(R.id.height);
        textView1=(TextView)findViewById(R.id.textView);
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        Log.d("jam","Total acc sensors = "+list.size());
    }
    public void startClick(View v){
        if(list.size()>0){
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG);
        }
    }
    @Override
    protected void onStop() {
        if(list.size()>0){
            sm.unregisterListener(sel);
        }
        super.onStop();
    }
    public void stopClick(View v){
        age=Integer.parseInt(ageEt.getText().toString());
        weight=Integer.parseInt(weightEt.getText().toString());
        height=Float.parseFloat(heightEt.getText().toString());
        if(list.size()>0){
            sm.unregisterListener(sel);
        }
        textView1.setText("Total Calorie Burned : " + perStepCalorieBurn*steps*weight);
    }
}
