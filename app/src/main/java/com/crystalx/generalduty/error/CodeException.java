package com.crystalx.generalduty.error;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class CodeException extends Exception {
    public void showDialog (AlertDialog.Builder b) {
        b.setTitle("提示");
        b.setPositiveButton("我已知晓",null);
        addContext(b);
        b.create();
        b.show();
    }

    void addContext (AlertDialog.Builder b){}
}
