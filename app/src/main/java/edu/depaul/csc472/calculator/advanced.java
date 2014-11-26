package edu.depaul.csc472.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;


public class advanced extends Activity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    public Button keyExiststs;
    public Button superKeyExists;
    public Button determine;
    public Button infer;
    public EditText keyText;
    public EditText superKeyText;
    public EditText determineElementOneText;
    public EditText determineElementTwoText;
    public EditText inferElementOneText;
    public EditText inferElementTwoText;
    public TextView resultKey;
    public TextView resultSuperKey;
    public TextView resultDetermine;
    public TextView resultInfer;
    static String[] RR = {"A","B","C", "D"};
    static String[] FD = {"A-BC", "AD-B", "CD-AB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        keyExiststs = (Button) findViewById(R.id.calculateKey);
        superKeyExists = (Button) findViewById(R.id.calculateSuperKey);
        determine = (Button) findViewById(R.id.calculateDetermination);
        infer = (Button) findViewById(R.id.calculateInference);
        keyText = (EditText) findViewById(R.id.keyInput);
        superKeyText = (EditText) findViewById(R.id.superKeyInput);
        determineElementOneText = (EditText) findViewById(R.id.det1);
        determineElementTwoText = (EditText) findViewById(R.id.det2);
        inferElementOneText = (EditText) findViewById(R.id.inf1);
        inferElementTwoText = (EditText) findViewById(R.id.inf2);
        resultKey = (TextView) findViewById(R.id.keyResult);
        resultSuperKey = (TextView) findViewById(R.id.superKeyResult);
        resultDetermine = (TextView) findViewById(R.id.determinationResult);
        resultInfer = (TextView) findViewById(R.id.inferenceResult);


        Button back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.main);
                Intent intent = new Intent(advanced.this, MyActivity.class);
                startActivity(intent);
            }
        });
        sgd = new ScaleGestureDetector(this,new ScaleListener());
    }

    ScaleGestureDetector sgd;
    public boolean onTouchEvent(MotionEvent ev){
        sgd.onTouchEvent(ev);
        return true;
    }
    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            setContentView(R.layout.main);
            Intent intent = new Intent(advanced.this, MyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
    }

    public void advancedButton_click(View w){
        switch (w.getId()){
            case R.id.calculateKey:
                checkKey();
                break;
            case R.id.calculateSuperKey:
                checkSuperKey();
                break;
            case R.id.calculateDetermination:
                checkDetermination();
                break;
            case R.id.calculateInference:
                checkInference();
                break;

        }
    }
    public void checkKey(){
        String checkingKey =  keyText.getText().toString();

        Decomposition.breakLHSandRHS(FD);

        String temp = "";
        for (int i = 0; RR.length > i; i++)
        {
            temp = temp + RR[i];
        }


        Decomposition.key.addAll(Decomposition.key.size(), Decomposition.permutation(temp));


        Decomposition.getSuperKeys();
        Decomposition.calculateKeys();
        Collections.sort(Decomposition.realKey);


        if (Decomposition.realKey.contains(checkingKey))
        {
            resultKey.setText("True");
        }
        else
        {
            resultKey.setText("False");
        }
        Decomposition.ClearAll();
    }

    public void checkSuperKey(){
        String checkingKey =  superKeyText.getText().toString();
        Decomposition.breakLHSandRHS(FD);

        String temp = "";
        for (int i = 0; RR.length > i; i++)
        {
            temp = temp + RR[i];
        }


        Decomposition.key.addAll(Decomposition.key.size(), Decomposition.permutation(temp));


        Decomposition.getSuperKeys();
        Decomposition.calculateKeys();
        Collections.sort(Decomposition.realKey);


        if (Decomposition.superKey.contains(checkingKey))
        {
            resultSuperKey.setText("True");
        }
        else
        {
            resultSuperKey.setText("False");
        }
        Decomposition.ClearAll();
    }

    public void checkDetermination(){
        String closureA =  determineElementOneText.getText().toString();
        String closureB  =  determineElementTwoText.getText().toString();

        Decomposition.breakLHSandRHS(FD);


        if (Decomposition.closure(closureA, closureB) == true)
        {
            resultDetermine.setText("True");
        }
        else
        {
            resultDetermine.setText("False");
        }
        Decomposition.ClearAll();

    }

    public void checkInference(){
        String inferenceA  = inferElementOneText.getText().toString();
        String inferenceB  =  inferElementTwoText.getText().toString();

        Decomposition.breakLHSandRHS(FD);

        if (Decomposition.inferance(inferenceA, inferenceB) == true)
        {
            resultInfer.setText("True");
        }
        else
        {
            resultInfer.setText("False");
        }
        Decomposition.ClearAll();
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                    Singleton.clearAllInput();

                    resetFields();

                    Toast.makeText(getApplicationContext(), "Input Cleared",
                            Toast.LENGTH_SHORT).show();

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    private void resetFields() {
        keyText.setText("");
        superKeyText.setText("");
        determineElementOneText.setText("");
        determineElementTwoText.setText("");
        inferElementOneText.setText("");
        inferElementTwoText.setText("");
        resultKey.setText("Result");
        resultSuperKey.setText("Result");
        resultDetermine.setText("Result");
        resultInfer.setText("Result");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advanced, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
