package com.crystalx.jinusicbox;

import static com.crystalx.jinusicbox.MainActivity.count;
import static com.crystalx.jinusicbox.MainActivity.surprise_count;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //初始化
        TextView tv_count = findViewById(R.id.tv_count),tv_surprise = findViewById(R.id.tv_surprise);
        tv_count.setText(String.valueOf(count));
        tv_surprise.setText(surprise_count + " ");

        //退出按钮
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(view -> SettingActivity.this.finish());

        Drawable d_more = ResourcesCompat.getDrawable(getResources(),R.drawable.more,null);
        Drawable d_less = ResourcesCompat.getDrawable(getResources(),R.drawable.less,null);

        Foldable[] foldableSequences = {
                new Foldable(findViewById(R.id.im_unfold_1),findViewById(R.id.unfold_1_sign),findViewById(R.id.unfold_1_context)),
                new Foldable(findViewById(R.id.im_unfold_2),findViewById(R.id.unfold_2_sign),findViewById(R.id.unfold_2_context)),
                new Foldable(findViewById(R.id.im_unfold_3),findViewById(R.id.unfold_3_sign),findViewById(R.id.unfold_3_context))
        };
        for (Foldable f : foldableSequences) {
            f.getTouch().setOnClickListener(view -> {
                f.unfold(d_more,d_less);
            });
        }

        //显示日期
        Date date = new Date();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TextView tv_date = findViewById(R.id.date);
        String d = " " + spf.format(date);
        tv_date.setText(d);

        //NEVER GONNA GIVE YOU UP
        View give_up = findViewById(R.id.report_of_experience);
        give_up.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://b23.tv/uXhAVDF");
            Intent i = new Intent();
            i.setData(uri);

            AlertDialog.Builder b = new AlertDialog.Builder(SettingActivity.this);
            b.setTitle("提示");
            b.setMessage("该操作涉及跳转网页，是否继续？");
            b.setPositiveButton("请开始你的表演",(DialogInterface , di) -> startActivity(i));
            b.setNegativeButton("但是我拒绝",null);
            b.create();
            b.show();
        });

        Button cd_key_btn = findViewById(R.id.cd_key_btn);
        TextView cd_key_et = findViewById(R.id.cd_key_et);
        cd_key_btn.setOnClickListener(view -> {
            if ("3AQ7HVE".equals(cd_key_et.getText().toString())) {
                count += 100000;

                AlertDialog.Builder b = new AlertDialog.Builder(SettingActivity.this);
                b.setTitle("提示");
                b.setMessage("兑换成功");
                b.setPositiveButton("我已知晓",null);
                b.create();
                b.show();
            }
        });
    }

}

class Foldable {
    boolean is_folded = true;
    ImageView touch;
    View sign;
    View context;

    /**
     * @param touch 真正的触发事件的iv
     * @param sign 用于<strong>隐藏</strong>item的layout
     * @param context 用于<strong>盛放</strong>item的layout
     * @since v1.4c
     */
    public Foldable(ImageView touch,View sign,View context) {
        this.sign = sign;
        this.touch = touch;
        this.context = context;
    }
    public void unfold (Drawable d_more,Drawable d_less) {
        //切换按钮图标
        if (is_folded) {
            touch.setBackground(d_less);
        } else {
            touch.setBackground(d_more);
        }

        //展开或折叠
        ViewGroup.LayoutParams lp= sign.getLayoutParams();
        ViewGroup.LayoutParams con= context.getLayoutParams();
        if (is_folded) {
            lp.height = con.height;
            this.is_folded = false;
        } else {
            lp.height = 0;
            this.is_folded = true;
        }
        sign.setLayoutParams(lp);
    }

    public ImageView getTouch() {
        return touch;
    }
}