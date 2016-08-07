package com.pranjal.accountauthenticator.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.pranjal.accountauthenticator.Database.Person;
import com.pranjal.accountauthenticator.R;
import com.pranjal.accountauthenticator.Utils.Constants;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoggedInActivity extends AppCompatActivity {

    TextView loggedIn;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.EMAIL, "Default");

        loggedIn = (TextView) findViewById(R.id.textViewLoggedIn);

        Realm realm = Realm.getInstance(this);
        RealmResults<Person> results = realm
                .where(Person.class)
                .equalTo("email",email)
                .findAll();

        loggedIn.setText(results.get(0).getName());
    }

    protected void Logout(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, false);
        editor.putString(Constants.EMAIL, "");
        editor.commit();

        Snackbar.make(v, "Logged out", Snackbar.LENGTH_SHORT).show();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoggedInActivity.this, LoginActivity.class);
                startActivity(i);
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
