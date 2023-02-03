package com.crystalx.generalduty.error;

import androidx.appcompat.app.AlertDialog;

public class CodePastDueException extends CodeException{
    String description = "兑换码已过期";
    @Override
    void addContext(AlertDialog.Builder b) {
        b.setMessage(description);
    }
}
