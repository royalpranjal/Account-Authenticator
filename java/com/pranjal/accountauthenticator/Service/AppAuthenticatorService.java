package com.pranjal.accountauthenticator.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.pranjal.accountauthenticator.Authenticator.AppAuthenticator;

/**
 * Created by royalpranjal on 8/7/2016.
 */
public class AppAuthenticatorService extends Service {
    private AppAuthenticator authenticator;

    @Override
    public void onCreate(){
        authenticator = new AppAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
