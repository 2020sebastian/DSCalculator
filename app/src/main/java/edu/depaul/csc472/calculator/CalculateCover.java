package edu.depaul.csc472.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateCover extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_cover);


        Button back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.main);
                Intent intent = new Intent(CalculateCover.this, MyActivity.class);
                startActivity(intent);
                finish();

            }
        });


        TextView result = (TextView) findViewById(R.id.view1);
        result.setText(Singleton.getStep1result());
        result.setMovementMethod(new ScrollingMovementMethod());
        sgd = new ScaleGestureDetector(this,new ScaleListener());
    }

    ScaleGestureDetector sgd;
    public boolean onTouchEvent(MotionEvent ev){
        Toast.makeText(getApplicationContext(), "Pinch to go back to main screen",
                Toast.LENGTH_SHORT).show();
        sgd.onTouchEvent(ev);
        return true;
    }
    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            setContentView(R.layout.main);
            Intent intent = new Intent(CalculateCover.this, MyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculate_cover, menu);
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
