package com.pranjal.accountauthenticator.Activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pranjal.accountauthenticator.Database.Person;
import com.pranjal.accountauthenticator.R;
import com.pranjal.accountauthenticator.Utils.Constants;

import android.os.Handler;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegisterActivity extends AppCompatActivity {

    EditText personName, personEmail, personPassword;
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        personName = (EditText) findViewById(R.id.editTextRegisterName);
        personEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        personPassword = (EditText) findViewById(R.id.editTextRegisterPassword);

        accountManager = AccountManager.get(this);
    }

    protected void toSignIn(View v){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    protected void register(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        Realm realm = Realm.getInstance(RegisterActivity.this);

        RealmResults<Person> personResult = realm
                .where(Person.class)
                .equalTo("email",personEmail.getText().toString())
                .findAll();

        if(personResult.size() != 0){
            Snackbar.make(v, "User already exists!", Snackbar.LENGTH_SHORT).show();
        }
        else{
            realm.beginTransaction();

            Person person = realm.createObject(Person.class);
            person.setPassword(personPassword.getText().toString());
            person.setEmail(personEmail.getText().toString());
            person.setName(personName.getText().toString());

            realm.commitTransaction();

            String authToken = personEmail.getText().toString() + "TestAuth";
            final Account account = new Account(personEmail.getText().toString(), Constants.ACCOUNT_TYPE);
            accountManager.addAccountExplicitly(account, personPassword.getText().toString(), null);
            accountManager.setAuthToken(account, Constants.AUTH_TOKEN_TYPE, authToken);

            Snackbar.make(v, "Successful", Snackbar.LENGTH_SHORT).show();

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            };
            handler.postDelayed(runnable, 2000);
        }
    }
}
