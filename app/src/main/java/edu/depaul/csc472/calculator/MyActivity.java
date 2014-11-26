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
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


public class MyActivity extends Activity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    EditText functionalDependencies;
    EditText relations;
    Button calculateCover;
    Button breakDownFD;
    Button threeNF;
    Button simplifyLeft;
    Button simplifyRight;
    Button minimalCover;
    Button canonicalCover;
    Button keys;
    Button superKeys;
    Button advanced;
    Button help;


    private void setSingletonValues(){
        Singleton.setFdInput(functionalDependencies.getText().toString());
        Singleton.setRelationsInput(relations.getText().toString());
        Singleton.calculate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        functionalDependencies = (EditText) findViewById(R.id.fd);
        relations              = (EditText) findViewById(R.id.relations);

        //populate fields with sample input only at the beginning
        if (Singleton.getFdInput() == "" && Singleton.getRelationsInput() == "") {
            functionalDependencies.setText("A-BC, AD-B, CD-AB");
            relations.setText("A, B, C, D");
            Toast.makeText(getApplicationContext(), "Fields populated with sample input",
                    Toast.LENGTH_SHORT).show();
        } else {
            functionalDependencies.setText(Singleton.getFdInput());
            relations.setText(Singleton.getRelationsInput());
        }

        calculateCover = (Button) findViewById(R.id.calculate);
        breakDownFD = (Button) findViewById(R.id.breakDown);
        threeNF = (Button) findViewById(R.id.threeNF);
        simplifyLeft = (Button) findViewById(R.id.simplifyLeft);
        simplifyRight = (Button) findViewById(R.id.simplifyRight);
        minimalCover = (Button) findViewById(R.id.minimalCover);
        canonicalCover = (Button) findViewById(R.id.canonicalCover);
        keys = (Button) findViewById(R.id.allKeys);
        superKeys = (Button) findViewById(R.id.allSuperKeys);
        advanced = (Button) findViewById(R.id.advanced);
        help = (Button) findViewById(R.id.help);

        calculateCover.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.stepOne();

                setContentView(R.layout.activity_calculate_cover);
                Intent intent = new Intent(MyActivity.this, CalculateCover.class);
                startActivity(intent);
            }
        });
        breakDownFD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.stepTwo();

                setContentView(R.layout.activity_break_down_fd);
                Intent intent = new Intent(MyActivity.this, BreakDownFD.class);
                startActivity(intent);
            }
        });
        threeNF.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.stepSeven();

                setContentView(R.layout.activity_three_nf);
                Intent intent = new Intent(MyActivity.this, threeNF.class);
                startActivity(intent);
            }
        });
        simplifyLeft.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.stepThree();

                setContentView(R.layout.activity_simplify_left);
                Intent intent = new Intent(MyActivity.this, simplifyLeft.class);
                startActivity(intent);
            }
        });
        simplifyRight.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.stepFour();

                setContentView(R.layout.activity_simplify_right);
                Intent intent = new Intent(MyActivity.this, simplifyRight.class);
                startActivity(intent);
            }
        });
        minimalCover.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.stepFive();

                setContentView(R.layout.activity_minimal_cover);
                Intent intent = new Intent(MyActivity.this, minimalCover.class);
                startActivity(intent);
            }
        });
        canonicalCover.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.stepSix();

                setContentView(R.layout.activity_canonical_cover);
                Intent intent = new Intent(MyActivity.this, canonicalCover.class);
                startActivity(intent);
            }
        });
        keys.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.getAllKeys();

                setContentView(R.layout.activity_get_all_keys);
                Intent intent = new Intent(MyActivity.this, GetAllKeys.class);
                startActivity(intent);
            }
        });
        superKeys.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                Decomposition.getAllSuperKeys();

                setContentView(R.layout.activity_get_all_super_keys);
                Intent intent = new Intent(MyActivity.this, GetAllSuperKeys.class);
                startActivity(intent);
            }
        });
        advanced.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingletonValues();
                setContentView(R.layout.activity_advanced);
                Intent intent = new Intent(MyActivity.this, advanced.class);
                startActivity(intent);
            }
        });


        help.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_help);
                Intent intent = new Intent(MyActivity.this, help.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
                    functionalDependencies.setText("");
                    relations.setText("");
                    Toast.makeText(getApplicationContext(), "Input Cleared",
                            Toast.LENGTH_SHORT).show();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
