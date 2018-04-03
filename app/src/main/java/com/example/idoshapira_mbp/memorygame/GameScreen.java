package com.example.idoshapira_mbp.memorygame;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Button;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    CountDownTimer ct;
    TextView name;
    TextView timerText;
    private Button buttons [][];
    int buttonId1 = -1;
    int winCounter = 0;
    private ArrayList imagesIds1 = new ArrayList();
    private ArrayList imagesIds2= new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        int diff = getIntent().getIntExtra("diff",0); // get diff
        name = (TextView) findViewById(R.id.nameGameScreen);
        timerText = (TextView) findViewById(R.id.timerGameScreen);
        name.setText(getIntent().getStringExtra("name"));
        final int size = getAmountOfButtons(diff);
        createButtons(size);
        startTimer(diff);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ct.cancel();
    }




    private void createButtons(final int size) {
        TableLayout table = (TableLayout) findViewById(R.id.tableLay);
        buttons = new Button[size][size];
        for(int row =0 ;row<size;row++){
            TableRow tableRow = new TableRow(this); // add row
            tableRow.setLayoutParams(new TableLayout.LayoutParams( // scaling for buttons
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    4.0f
            ));
            table.addView(tableRow); // add Row to Table
            for(int col = 0; col < size;col++ ){
                final Button button = new Button(this);
            //    button.setBackgroundResource(R.drawable.squarebutton);
                button.setBackgroundColor(Color.GRAY);
                button.setPadding(500,500,500,500);
                button.setLayoutParams(new TableRow.LayoutParams( // scaling for buttons
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        4.0f
                ));
                int id = setPictureForButton((size*size)/2);
                button.setId(id); // set the picture as the ID
                button.setBackgroundResource(R.drawable.squarebutton);
             //   setButtonActivity(size,button,R.drawable.squarebutton);
             //   button.setEnabled(true);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setButtonActivity(size,button,button.getId());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkMatch(button,size);
                            }
                        },200);

                    }
                });
                buttons[row][col] = button;
                tableRow.addView(button);
            }
        }

    }



    private void checkMatch(Button buttonPressed,int size){
        if(buttonId1 == -1)
            buttonId1 = buttonPressed.getId();
        else if (buttonId1 == buttonPressed.getId()){ //match!
            setButtonActivity(size,buttonPressed,R.drawable.match);
            for(int i =0; i<size ; i++){
                for(int j =0; j<size ; j++){
                    if (buttons[i][j].getId()== buttonId1)
                    {
                        setButtonActivity(size,buttons[i][j],R.drawable.match);
                        break;
                    }
                }
            }
            winChecker(size);
            buttonId1 = -1;
        }else{ // no match
            setButtonActivity(size,buttonPressed,R.drawable.squarebutton);
           // buttonPressed.setBackgroundResource(android.R.drawable.btn_default);
            //
            //buttonPressed.setBackgroundColor(Color.GRAY);
           //buttonPressed.setPadding(300,300,300,300);
            buttonPressed.setClickable(true);
            for(int i =0; i<size ; i++){
                for(int j =0; j<size ; j++){
                    if (buttons[i][j].getId()== buttonId1)
                    {
                        setButtonActivity(size,buttons[i][j],R.drawable.squarebutton);
                        //buttons[i][j].setBackgroundResource(android.R.drawable.btn_default);
                        //buttons[i][j].setPadding(300,300,300,300);
                        //buttons[i][j].setBackgroundColor(Color.GRAY);
                        buttons[i][j].setClickable(true);

                    }
                }
            }
            buttonId1 = -1;

        }

    }

    private void winChecker(int size){
        winCounter++;
        if(winCounter== (size*size)/2){
            Toast.makeText(getApplicationContext(),"YOU WIN!",Toast.LENGTH_LONG).show();
            ct.cancel();
            finish();
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

    private void setButtonActivity( int size,Button button,int pictureId){ // add picture and turn unclickable
        //lock button sizes
        //lockButtonsSizes(size);
        //set background with scaling
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),pictureId);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,newWidth,newHeight,true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource,scaledBitmap));
        button.setClickable(false);
    }

    private void lockButtonsSizes(int size){

        for(int row = 0; row<size;row++){
            for(int col =0;col<size;col++){
                Button button = buttons[row][col];
                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);
                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
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
                Toast.makeText(getApplicationContext(),"YOU LOSE!",Toast.LENGTH_LONG).show();
                finish();
            }
        }.start();
    }

}
