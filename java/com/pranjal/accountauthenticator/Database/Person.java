package com.pranjal.accountauthenticator.Database;

import io.realm.RealmObject;

/**
 * Created by royalpranjal on 8/7/2016.
 */
public class Person extends RealmObject {
    private String name;
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}

