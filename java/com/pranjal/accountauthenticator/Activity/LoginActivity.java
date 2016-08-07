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
import android.widget.EditText;

import com.pranjal.accountauthenticator.Database.Person;
import com.pranjal.accountauthenticator.R;
import com.pranjal.accountauthenticator.Utils.Constants;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.editTextLoginEmail);
        password = (EditText) findViewById(R.id.editTextLoginPassword);

    }

    protected void signIn(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        Realm realm = Realm.getInstance(LoginActivity.this);

        RealmResults<Person> results = realm
                .where(Person.class)
                .equalTo("email", email.getText().toString())
                .findAll();

        if(results.size() == 0){
            Snackbar.make(v, "No such user exists!", Snackbar.LENGTH_SHORT).show();
        }
        else{
            SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.IS_LOGGED_IN, true);
            editor.putString(Constants.EMAIL, email.getText().toString());
            editor.commit();

            Snackbar.make(v, "Successful", Snackbar.LENGTH_SHORT).show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(LoginActivity.this, LoggedInActivity.class);
                    startActivity(i);
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    protected void toRegister(View v){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}
