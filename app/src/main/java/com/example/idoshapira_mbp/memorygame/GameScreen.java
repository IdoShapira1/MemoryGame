package com.example.idoshapira_mbp.memorygame;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    CountDownTimer ct;
    TextView name;
    TextView timerText;
    private Button buttons [][];
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
        createButtons(diff);
        startTimer(diff);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ct.cancel();
    }


    private void createButtons(int diff) {


        TableLayout table = (TableLayout) findViewById(R.id.tableLay);
        final int size = getAmountOfButtons(diff);
        buttons = new Button[size][size];
        for(int row =0 ;row<size;row++){
            TableRow tableRow = new TableRow(this); // add row
            tableRow.setLayoutParams(new TableLayout.LayoutParams( // scaling for buttons
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow); // add Row to Table
            for(int col = 0; col < size;col++ ){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams( // scaling for buttons
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                button.setPadding(4,4,4,4);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = setPictureForButton((size*size)/2);
                        setButtonActivity(FINAL_ROW,FINAL_COL,size,id);

                    }
                });
                buttons[row][col] = button;
                tableRow.addView(button);
            }
        }

    }
    private int setPictureForButton(int size){
        int id = getRandomImage(size);
        // add 2 images of the same picture
        while( imagesIds1.contains(id) && imagesIds2.contains(id)){
            id = getRandomImage((size*size)/2);
        }
        if(imagesIds1.contains(id)){
            imagesIds2.add(id);
        }else
            imagesIds1.add(id);

        return id;
    }

    private void setButtonActivity( final int row, final int col,int size,int id){
        Button button = buttons[row][col];
        //lock button sizes
        lockButtonsSizes(size);
        //set background with scaling
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),id);
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
                return 5;
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
