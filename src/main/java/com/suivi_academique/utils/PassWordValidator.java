package com.suivi_academique.utils;

public class PassWordValidator {

    public boolean isvalid(String password) {
        if (password.length() >= 5 && password.length() <= 10)
            return true;
        else
            return false;
    }

}
