 package com.pranjal.accountauthenticator.Activity;

 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.os.Bundle;
 import android.os.Handler;
 import android.support.v7.app.AppCompatActivity;

 import com.pranjal.accountauthenticator.R;
 import com.pranjal.accountauthenticator.Utils.Constants;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        final SharedPreferences sharedPreferences = getSharedPreferences("Session", MODE_PRIVATE);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false)){
                    Intent i = new Intent(MainActivity.this, LoggedInActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };
        handler.postDelayed(runnable, 2000);
    }
}
