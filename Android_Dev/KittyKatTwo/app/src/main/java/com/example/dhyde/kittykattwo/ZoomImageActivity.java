package com.example.dhyde.kittykattwo;


import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
// UI Components
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.graphics.Matrix;
import android.widget.ImageView.ScaleType;



public class ZoomImageActivity extends AppCompatActivity implements OnTouchListener {
    // Global Vars
    float TX = 0;
    float TY = 0;
    float GScale = 0;
    boolean PanMode = false;
    ImageView image;
    int view_width = 0;
    int view_height = 0;
    Matrix savedMatrix = new Matrix();   // saved current matrix
    float tTX = 0;                       // translation X temporary value
    float tTY = 0;                       // translation Y temporary value
    float dist0, distCurrent,GScale0,TX0,TY0;
    PointF start = new PointF();         // start position of first touch boolean
    boolean MultiTouch = false;          // MultiTouch mode ON/OFF


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        Button ZoomIn = (Button)findViewById(R.id.button2);
        Button ZoomOut = (Button)findViewById(R.id.button3);
        ZoomIn.setOnClickListener(new ButtonZoomInHandler());
        ZoomOut.setOnClickListener(new ButtonZoomOutHandler());

        image.setOnTouchListener(new View.OnTouchListener() {
            float distx, disty, ttscale;
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    start.set(event.getX(), event.getY());
                    if (PanMode) {
                        Matrix mat = image.getImageMatrix();
                        savedMatrix.set(mat);
                        image.setScaleType(ScaleType.MATRIX);
                        tTX = TX;
                        tTY = TY;
                    } // -- END PanMode
                }// -- END if getAction

                else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) {
                    int numpt = event.getPointerCount();
                    if (numpt > 1) {
                        MultiTouch = true;
                        PanMode = false;
                        distx = event.getX(0) - event.getX(1);
                        disty = event.getY(0) - event.getY(1);
                        dist0 = (float) Math.sqrt(distx * distx + disty * disty);
                        GScale0 = GScale;
                        TX0 = TX;
                        TY0 = TY;
                        Matrix mat = image.getImageMatrix();
                        savedMatrix.set(mat);
                        image.setScaleType(ScaleType.MATRIX);
                    } // -- END if(numpt>1)
                } // -- END else if

                else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
                    int numpt = event.getPointerCount();
                    if (numpt > 1 && numpt < 3) {
                        MultiTouch = false;
                        Matrix mat = new Matrix();
                        mat.set(savedMatrix);
                        distx = event.getX(0) - event.getX(1);
                        disty = event.getY(0) - event.getY(1);
                        distCurrent = (float) Math.sqrt(distx * distx + disty * disty);
                        ttscale = distCurrent / dist0;
                        GScale = GScale0 * ttscale;
                        if (GScale < 1.0) {
                            image.setScaleType(ScaleType.FIT_CENTER);
                            GScale = 1;
                            TX = TY = 0;
                            PanMode = false;
                            mat = image.getImageMatrix();
                            savedMatrix.set(mat);
                            tTX = TX;
                            tTY = TY;
                            TX0 = TX;
                            TY0 = TY;
                            GScale0 = GScale;
                        } // end if(GScale<1.0)
                        else {
                            mat.postScale(ttscale, ttscale, view_width / 2, view_height / 2);
                            image.setScaleType(ScaleType.MATRIX);
                            image.setImageMatrix(mat);
                            TX = TX0 * ttscale;
                            TY = TY0 * ttscale;
                            PanMode = true;
                            mat = image.getImageMatrix();
                            savedMatrix.set(mat);
                            image.setScaleType(ScaleType.MATRIX);
                            tTX = TX;
                            tTY = TY;
                        } // end else
                        int p = event.getActionIndex();
                        if (p == 0) start.set(event.getX(1), event.getY(1));
                        if (p == 1) start.set(event.getX(0), event.getY(0));
                        image.invalidate();
                    } // -- END if(numpt>1&&numpt<3)
                } // -- END else if

                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    int numpt = event.getPointerCount();
                    if (MultiTouch && numpt > 1) {
                        distx = event.getX(0) - event.getX(1);
                        disty = event.getY(0) - event.getY(1);
                        distCurrent = (float) Math.sqrt(distx * distx + disty * disty);
                        ttscale = distCurrent / dist0;
                        GScale = GScale0 * ttscale;
                        if (GScale < 1.0) {
                            image.setScaleType(ScaleType.CENTER_INSIDE);
                            GScale = 1;
                            TX = TY = 0;
                            PanMode = false;
                            Matrix mat = new Matrix();
                            mat = image.getImageMatrix();
                            savedMatrix.set(mat);
                            tTX = TX;
                            tTY = TY;
                            TX0 = TX;
                            TY0 = TY;
                            GScale0 = GScale;
                        } // -- END if(GSCale<1.0)
                        else {
                            view_width = image.getWidth();
                            view_height = image.getHeight();
                            Matrix mat = new Matrix();
                            mat.set(savedMatrix);
                            mat.postScale(ttscale, ttscale, view_width / 2, view_height / 2);
                            image.setScaleType(ScaleType.MATRIX);
                            image.setImageMatrix(mat);
                            TX = TX0 * ttscale;
                            TY = TY0 * ttscale;
                        } // -- END else
                        image.invalidate();
                    } // -- END if(MultiTouch&&numpt>1)

                    else {
                        if (PanMode) {
                            float TranX = event.getX() - start.x;
                            float TranY = event.getY() - start.y;
                            Matrix mat = new Matrix();
                            mat.set(savedMatrix);
                            mat.postTranslate(TranX, TranY);
                            image.setImageMatrix(mat);
                            TX = tTX + TranX;
                            TY = tTY + TranY;
                            image.invalidate();
                        } // --END if(PanMode)
                    } // -- END else

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        start.set(event.getX(), event.getY());
                        if (PanMode) {
                            Matrix mat = image.getImageMatrix();
                            savedMatrix.set(mat);
                            image.setScaleType(ScaleType.MATRIX);
                            tTX = TX;
                            tTY = TY;
                        } // end if PanMode
                    } // -- END if getAction
                    else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (PanMode) {
                            float TranX = event.getX() - start.x;
                            float TranY = event.getY() - start.y;
                            Matrix mat = new Matrix();
                            mat.set(savedMatrix);
                            mat.postTranslate(TranX, TranY);
                            image.setImageMatrix(mat);
                            TX = tTX + TranX;
                            TY = tTY + TranY;
                            image.invalidate();
                        } // --END if PanMode
                    } // --END else if
                } // -- END onTouch
                return true;
            }
        });
    }// -- END onCreate
    class ButtonZoomInHandler implements OnClickListener {
        public void onClick(View view) {
            Toast.makeText(ZoomImageActivity.this, "Zoom In Pressed", Toast.LENGTH_SHORT).show();
            if(GScale<5.0) {
                view_width = image.getWidth();
                view_height = image.getHeight();
                Matrix mat = image.getImageMatrix();
                mat.postScale(1.5f, 1.5f, view_width/2, view_height/2);
                image.setScaleType(ScaleType.MATRIX);
                image.setImageMatrix(mat);
                GScale = GScale * 1.5f;
                TX = TX * 1.5f;
                TY = TY * 1.5f;
                PanMode = true;
                image.invalidate();
            }
        } // -- END onClick
    } // -- END ButtonZoomInHandler

    class ButtonZoomOutHandler implements OnClickListener {
        public void onClick(View view) {
            Toast.makeText(ZoomImageActivity.this, "Zoom Out Pressed", Toast.LENGTH_SHORT).show();
            image.setScaleType(ScaleType.FIT_CENTER);
            GScale = 1;
            TX = TY = 0;
            PanMode = false;
            image.invalidate();
        } // -- END onClick
    } // -- END ButtonZoomOutHandler


    @Override
    public boolean onTouch (View v, MotionEvent event){
        // TODO Auto-generated method stub
        return false;
    }

} // -- END ZoomImageActivity
