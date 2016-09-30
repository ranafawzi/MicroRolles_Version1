package com.example.rana.microrolls;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends AppCompatActivity {
    ArrayList<float[]> list = new ArrayList<>();
    float X, Y, X1, Y1, delta, LastX, LastY;
    ImageView imageView;
    TouchImageView ViewImg;
    RelativeLayout.LayoutParams params;
    int windowwidth;
    int windowheight;
    static final int NONE = 0;
    static final int Right = 1;
    static final int Left = 2;
    static final int Up = 3;
    static final int Down = 4;
    static final int Track = 5;
    int mode = NONE;
    float scale, dX = 0f, dY = 0f, rawx, rawy;


    private static final String DEBUG_TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TouchImageView ViewImg = new TouchImageView(this);
        ViewImg = (TouchImageView) findViewById(R.id.touchimageView);
        assert ViewImg != null;
        ViewImg.setImageResource(R.drawable.img);
        //  ViewImg.setMaxZoom(4f);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int windowwidth = size.x;
        int windowheight = size.y;


        // setContentView(ViewImg);

        // imageView = (ImageView) findViewById(R.id.imageView);
        // imageView.setImageResource(R.drawable.img);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int i;
        int action = MotionEventCompat.getActionMasked(event);
        ViewImg.setScaleType(ImageView.ScaleType.MATRIX);


        switch (action) {
            case (MotionEvent.ACTION_DOWN):

                X1 = event.getX(); //endX= 0.0f;
                Y1 = event.getY();
                dX = ViewImg.getX() - X1;
                dY = ViewImg.getY() - Y1;
                Log.d(DEBUG_TAG, "Action was DOWN" + X1 + "This y value  " + Y1);
                return true;

            case (MotionEvent.ACTION_UP):
                LastX = event.getX();
                LastY = event.getY();
                Log.d(DEBUG_TAG, "Action was UP");


            case (MotionEvent.ACTION_MOVE):

                // rawx= event.getRawX();
                //rawy=event.getRawY();
                rawx = (float) Math.pow((LastX - X1), 2);
                rawy = (float) Math.pow((LastY - Y1), 2);
                float dist = (float) Math.sqrt(rawx + rawy);

                Log.d(DEBUG_TAG, "Distance" + dist);
               if (dist < 80) {

                    if (LastX > X1) {
                        if (LastY > Y1) {
                            delta = (Math.abs(LastY - Y1) / LastY);

                        } else delta = (Math.abs(Y1 - LastY) / Y1);

                        if (delta < 0.05) {

                            Log.d(DEBUG_TAG, "Action was Right");
                           ObjectAnimator animObj=ObjectAnimator.ofFloat(ViewImg,"rotationY",0f,180f);
                            animObj.setDuration(3000);
                            animObj.start();


                            //  mode = Right;
                            // DoGestures(mode,rawx,rawy,event);

                        }/**else if (delta < 0.11 && delta >0.05)
                         { // DoGestures(Track,rawx,rawy,event);

                         ViewImg.animate()
                         .x(event.getRawX() + dX)
                         .y(event.getRawY() + dY)
                         .setDuration(0)
                         .start();

                         }**/

                        else if (LastY > Y1) {

                            Log.d(DEBUG_TAG, "Action was firstdownn");
                            // mode = Down;
                            // DoGestures(Down,rawx,rawy,event);

                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ViewImg, "scaleX", 0.5f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ViewImg, "scaleY", 0.5f);
                            scaleDownX.setDuration(1000);
                            scaleDownY.setDuration(1000);
                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);
                            scaleDown.start();
                        } else {
                            // DoGestures(Up,rawx,rawy,event);
                            Log.d(DEBUG_TAG, "Action was firstupp");

                            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(ViewImg, "scaleX", 1.5f);
                            ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(ViewImg, "scaleY", 1.5f);
                            scaleUpX.setDuration(1000);
                            scaleUpY.setDuration(1000);
                            AnimatorSet scaleUp = new AnimatorSet();
                            scaleUp.play(scaleUpX).with(scaleUpY);
                            scaleUp.start();
                        }

                    } else {

                        if (LastY > Y1) {
                            delta = (Math.abs(LastY - Y1) / LastY);

                        } else delta = (Math.abs(Y1 - LastY) / Y1);

                        if (delta < 0.05) {
                            Log.d(DEBUG_TAG, "Action was left");
                            /**  Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.img);
                             Matrix matrix = new Matrix();
                             matrix.postRotate(90);
                             Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
                             matrix, true);
                             ViewImg.setImageBitmap(rotated);**/
                            ObjectAnimator animObj=ObjectAnimator.ofFloat(ViewImg,"rotationY",0f,-180f);
                            animObj.setDuration(3000);

                            animObj.start();

//                      RotateAnimation rAnim = new RotateAnimation(0.0f, 360.0f,
//                              Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                              0.5f);
//                            //rAnim.setRepeatCount(Animation.INFINITE);
//                            rAnim.setInterpolator(new LinearInterpolator());
//                            rAnim.setFillAfter(true);
//                            rAnim.setDuration(0);
///* refreshIcon is object of an imageView*/
//                           ViewImg.startAnimation(rAnim);
                            /**   ViewImg.setRotation((float) 45.0);**/

                            /** TranslateAnimation animation = new TranslateAnimation(LastX,LastX-100f,0,0);
                             animation.setDuration(1000);

                             ViewImg.startAnimation(animation);
                             int ww= (int)ViewImg.getX();
                             int hh =(int)ViewImg.getY();

                             params = new RelativeLayout.LayoutParams(ww,hh);**/


                            //  DoGestures(Left,rawx,rawy,event); /**params = new RelativeLayout.LayoutParams(80,80); // 80 is just a number of my choice. There are many possibilities

                            /**    params.leftMargin = 5;

                             params.topMargin = 400;

                             ViewImg.setLayoutParams(params);**/

                        }/**else if (delta < 0.11 && delta >0.05)
                         {
                         // DoGestures(Track,rawx,rawy, event);
                         ViewImg.animate()
                         .x(event.getRawX() + dX)
                         .y(event.getRawY() + dY)
                         .setDuration(0)
                         .start();

                         }**/
                        else if (LastY > Y1) {

                            // DoGestures(Down,rawx,rawy,event);
                            Log.d(DEBUG_TAG, "Action was down");
                           ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ViewImg, "scaleX", 0.5f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ViewImg, "scaleY", 0.5f);
                            scaleDownX.setDuration(1000);
                            scaleDownY.setDuration(1000);
                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);
                            scaleDown.start();

                        } else {

                            Log.d(DEBUG_TAG, "Action was up");
                            //  DoGestures(Up,rawx,rawy, event);
                            // final ImageView zoom = (ImageView) findViewById(R.id.touchimageView);
                            //  final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
                            // assert zoom != null;
                            //   ViewImg.startAnimation(zoomAnimation);
                            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(ViewImg, "scaleX", 1.5f);
                            ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(ViewImg, "scaleY", 1.5f);
                            scaleUpX.setDuration(1000);
                            scaleUpY.setDuration(1000);
                            AnimatorSet scaleUp = new AnimatorSet();
                            scaleUp.play(scaleUpX).with(scaleUpY);
                            scaleUp.start();
                        }
                    }
              } else {
                   ViewImg.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
               }

                //X= event.getX(); //endX= 0.0f;
                //Y= event.getY();
                //float[] values={X,Y};
                //float[] Lastvalues={0.0f,0.0f};
                //  Log.d("Member name: ", "X"+X+"Y"+Y);
                //list.add(values);

                /*int listSize = list.size();
                for (i = 0; i<listSize; i++){
                    Log.d("Action was MOVE: ", Arrays.toString(list.get(i)));
                }

                if (list != null && !list.isEmpty()) {
                   Log.d( "The last element is " , Arrays.toString( list.get(list.size()-1)) +"first"+X1);
                }*/

                /*Lastvalues= list.get(list.size()-1);
                   float Lastx=Lastvalues[0];
                   float Lasty=Lastvalues[1];
                double result=(Lasty-Y1)/Y1;   // 1-(((Lasty*Y1)-Math.pow(Y1,2)-Math.pow(Lasty,2))/(Lasty*Y1));
                Log.d( "The last element is " , Arrays.toString( Lastvalues)+"  x "+Lastx+"  y  "+Lasty+ " result  " + result);*/


                //     if (Math.abs(Lasty / Y1)< 0.1)
                // if (Math.abs(( 1-( (Lasty*Y1)-Math.pow(Y1,2)-Math.pow(Lasty,2))))<0.50)
                //  if (Math.abs((Lasty-Y1)/Y1)< 0.1) {


                // Log.d(DEBUG_TAG,"Action was MOVE" +"Xvlaues"+endX+ "yy"+Y+Arrays.toString(Xcoor)+ Arrays.toString(Ycoor));
                return true;
            /*case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP" );
                return true;*/
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }


    }

/**void  DoGestures(int mode, float rawx, float rawy, MotionEvent event){


 if (mode==Up)
 {
 Log.d(DEBUG_TAG, "Action was up");
 ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(ViewImg, "scaleX", 1.5f);
 ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(ViewImg, "scaleY", 1.5f);
 scaleUpX.setDuration(1000);
 scaleUpY.setDuration(1000);
 AnimatorSet scaleUp = new AnimatorSet();
 scaleUp.play(scaleUpX).with(scaleUpY);
 scaleUp.start();

 }
 else if(mode == Down)
 {
 Log.d(DEBUG_TAG, "Action was down");
 ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ViewImg, "scaleX", 0.5f);
 ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ViewImg, "scaleY", 0.5f);
 scaleDownX.setDuration(1000);
 scaleDownY.setDuration(1000);
 AnimatorSet scaleDown = new AnimatorSet();
 scaleDown.play(scaleDownX).with(scaleDownY);
 scaleDown.start();
 }

 /**else if(mode == Track){

 ViewImg.animate()
 .x(event.getRawX() + dX)
 .y(event.getRawY() + dY)
 .setDuration(0)
 .start();


 }


 }**/
}
