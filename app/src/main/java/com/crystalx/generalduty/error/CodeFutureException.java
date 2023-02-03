package com.crystalx.generalduty.error;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class CodeFutureException extends CodeException{
    String description = "这是未来的兑换码，你怎么可能拥有？";
    @Override
    void addContext(AlertDialog.Builder b) {
        b.setMessage(description);
    }
}
