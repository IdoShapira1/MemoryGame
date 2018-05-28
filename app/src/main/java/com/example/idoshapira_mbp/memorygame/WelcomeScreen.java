package com.example.idoshapira_mbp.memorygame;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class WelcomeScreen extends AppCompatActivity  {

    final String TAG = "WelcomeScreen";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        final EditText name = (EditText) findViewById(R.id.namePicker); // Pick up name
        final EditText age = (EditText) findViewById(R.id.agePicker); // Pick up date of birth
        getLocationPermission();
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
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            boolean mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}

