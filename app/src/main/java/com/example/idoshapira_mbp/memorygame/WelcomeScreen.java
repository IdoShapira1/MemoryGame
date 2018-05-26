package com.example.idoshapira_mbp.memorygame;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;


public class WelcomeScreen extends AppCompatActivity  {

    final String TAG = "WelcomeScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        final EditText name = (EditText) findViewById(R.id.namePicker); // Pick up name
        final EditText age = (EditText) findViewById(R.id.agePicker); // Pick up date of birth
        Button confirmButton = (Button) findViewById(R.id.confirmAttrButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeScreen.this,DifficultyPicker.class);
                intent.putExtra("userName",name.getText().toString()); // Send name to next screen
                intent.putExtra("age",age.getText().toString()); // send age
                startActivity(intent);
            }
        });

    }




}

