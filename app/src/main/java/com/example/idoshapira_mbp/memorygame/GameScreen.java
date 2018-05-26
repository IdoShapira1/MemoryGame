package com.example.idoshapira_mbp.memorygame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

import tyrantgit.explosionfield.ExplosionField;

public class GameScreen extends AppCompatActivity implements SensorService.SensorServiceListener {

    final String TAG = "GameScreen";


    CountDownTimer ct;
    TextView name;
    TextView timerText;
    private ImageView buttonsImages[][];

    long animationDuration = 2000; //milliseconds
    ObjectAnimator rotateAnimation;
    ObjectAnimator fallAnimation;
    AnimatorSet animatorSet;
    ExplosionField explosion;

    int imageId1 = -1; //helps with finding a match
    int winCounter = 0;
    private ArrayList imagesIds1 = new ArrayList();
    private ArrayList imagesIds2= new ArrayList();
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this,SensorService.class);
        bindService(i,serviceConnection, Context.BIND_AUTO_CREATE);
        setContentView(R.layout.activity_game_screen);
        int diff = getIntent().getIntExtra("diff",0); // get diff
        name = (TextView) findViewById(R.id.nameGameScreen);
        timerText = (TextView) findViewById(R.id.timerGameScreen);
        name.setText(getIntent().getStringExtra("name"));
        animatorSet = new AnimatorSet();
        size = getAmountOfButtons(diff);
        createButtonsImages();
        startTimer(diff);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ct.cancel();
    }




    private void createButtonsImages() {
        TableLayout table = (TableLayout) findViewById(R.id.tableLay);
        buttonsImages = new ImageView[size][size];
        for(int row =0 ;row<size;row++){
            TableRow tableRow = new TableRow(this); // add row
            tableRow.setLayoutParams(new TableLayout.LayoutParams( // scaling for buttonsImages
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    4.0f
            ));
            table.addView(tableRow); // add Row to Table
            for(int col = 0; col < size;col++ ){
                final ImageView buttonImage = new ImageView(this);
                buttonImage.setPadding(500,500,500,500);
                buttonImage.setLayoutParams(new TableRow.LayoutParams( // scaling for buttonsImages
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        4.0f
                ));
                int id = setPictureForButton((size*size)/2);
                buttonImage.setId(id); // set the picture as the ID
                buttonImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setButtonActivity(buttonImage,buttonImage.getId());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkMatch(buttonImage);
                            }
                        },200);

                    }
                });
                buttonsImages[row][col] = buttonImage;
                explosion = ExplosionField.attach2Window(this);
                tableRow.addView(buttonImage);
                buttonImage.setBackgroundResource(R.drawable.squarebutton);
            }
        }

    }



    private void checkMatch(ImageView imagePressed){
        if(imageId1 == -1)
            imageId1 = imagePressed.getId();
        else if (imageId1 == imagePressed.getId()){ //match!
            imagePressed.setId(-1);
            for(int i =0; i<size ; i++){
                for(int j =0; j<size ; j++){
                    if (buttonsImages[i][j].getId()== imageId1)
                    {
                        buttonsImages[i][j].setId(-1);
                        break;
                    }
                }
            }
            winChecker();
            imageId1 = -1;
        }else{ // no match
            setButtonActivity(imagePressed,R.drawable.squarebutton);
            imagePressed.setClickable(true);
            for(int i =0; i<size ; i++){
                for(int j =0; j<size ; j++){
                    if (buttonsImages[i][j].getId()== imageId1)
                    {
                        setButtonActivity(buttonsImages[i][j],R.drawable.squarebutton);
                        buttonsImages[i][j].setClickable(true);

                    }
                }
            }
            imageId1 = -1;

        }

    }

    private void winChecker(){
        winCounter++;
        if(winCounter== (size*size)/2){
            Toast.makeText(getApplicationContext(),"YOU WIN!",Toast.LENGTH_LONG).show();
            final Animation hyperAnimation = AnimationUtils.loadAnimation(GameScreen.this, R.anim.explosion);
            for(int i =0; i<size ; i++){
                for(int j =0; j<size ; j++){
                    rotateAnimation = ObjectAnimator.ofFloat(buttonsImages[i][j],"rotation",0f,1080f);
                    rotateAnimation.setDuration(animationDuration*2);
                    rotateAnimation.start();
                    buttonsImages[i][j].startAnimation(hyperAnimation);



                }
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ct.cancel();
                    finish();
                }
            },2000);

        }
    }
    private int setPictureForButton(int size){
        int id = getRandomImage(size);
        // add 2 images of the same picture
        while( imagesIds1.contains(id) && imagesIds2.contains(id)){
            id = getRandomImage(size);
        }
        if(imagesIds1.contains(id)){
            imagesIds2.add(id);
        }else
            imagesIds1.add(id);
        return id;
    }

    private void setButtonActivity(ImageView button,int pictureId){ // add picture and turn unclickable

        //set background with scaling
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),pictureId);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,newWidth,newHeight,true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource,scaledBitmap));
        button.setClickable(false);
    }



    private int getRandomImage(int size) {
        TypedArray imgs = getResources().obtainTypedArray(R.array.random_imgs);
        int id = imgs.getResourceId(new Random().nextInt(size), -1); //-1 is default if nothing is found (we don't care)
        imgs.recycle();
        return id;
    }



    private int getAmountOfButtons(int diff){
        switch (diff){
            case 60:
                return 6;
            case 45:
                return 4;
            case 30:
                return 2;
        }
        return 0;
    }


    private void startTimer(int time){

        ct = new CountDownTimer(time*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText("seconds remaining: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                for(int i =0; i<size ; i++){
                    for(int j =0; j<size ; j++){

                        fallAnimation = ObjectAnimator.ofFloat(buttonsImages[i][j],"y",1000f);
                        fallAnimation.setDuration(animationDuration);
                        fallAnimation.start();
                        explosion.explode(buttonsImages[i][j]);
                    }
                }

                Toast.makeText(getApplicationContext(),"YOU LOSE!",Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },2000);
            }
        }.start();
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof SensorService.SensorServiceBinder) {
                SensorService.SensorServiceBinder sensorServiceBinder = (SensorService.SensorServiceBinder) iBinder;
                sensorServiceBinder.setListener(GameScreen.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


    @Override
    public void onSensorChanged(float[] values) {
        Log.d(TAG,"X: "+values[0]+" Y:"+values[1]+" Z:"+values[2]);

    }

}
