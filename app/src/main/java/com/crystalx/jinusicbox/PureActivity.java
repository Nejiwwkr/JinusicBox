package com.crystalx.jinusicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import static com.crystalx.jinusicbox.MainActivity.count;
import static com.crystalx.jinusicbox.MainActivity.playSound;

public class PureActivity extends AppCompatActivity {
    public static String lastSelectedPureButtonText;
    public static int lastSelectedPureButtonMusicID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pure);

        //退出按钮
        ImageView back = findViewById(R.id.back_pure);
        back.setOnClickListener(view -> PureActivity.this.finish());

        Button pure = findViewById(R.id.pure);
        pure.setText(lastSelectedPureButtonText);
        pure.setOnClickListener(v -> {
            playSound(PureActivity.this,lastSelectedPureButtonMusicID);
            count++;
        });
    }
}