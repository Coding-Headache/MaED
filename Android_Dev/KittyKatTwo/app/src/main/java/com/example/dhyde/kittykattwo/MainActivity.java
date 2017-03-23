package com.example.dhyde.kittykattwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// UI components
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

// Gesture Functionality
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnTouchListener;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    // Global vars to store user input
    private int count = 0;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText)findViewById(R.id.editText);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new ButtonHandler());
        ImageButton image = (ImageButton) findViewById(R.id.imageButton);
        GestureHandler handler = new GestureHandler();
        final GestureDetectorCompat detector = new GestureDetectorCompat(this, handler);
        detector.setOnDoubleTapListener(handler);
        image.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                return detector.onTouchEvent(me);
            }
        });
    }
    class ButtonHandler implements OnClickListener {
        public void onClick(View view) {
            count++;
            text.setText(count + " Meeow");
        }
    }
    class GestureHandler extends SimpleOnGestureListener {
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Toast.makeText(MainActivity.this,
                    "Purrr",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        public boolean onDoubleTap(MotionEvent e) {
            Intent intent;
            intent = new Intent(MainActivity.this, ZoomImageActivity.class);
            startActivity(intent);
            return true;
        }
    }
}