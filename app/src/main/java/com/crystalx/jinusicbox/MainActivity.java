package com.crystalx.jinusicbox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static MediaPlayer mp = new MediaPlayer();
    public static int count,surprise_count = 0;
    public static String lastClick = "nnnnnnnn";
    /**
     * 0：未发现，1：已发现
     */
    public int is_sur_A_founded = 0,is_sur_B_founded = 0,is_sur_C_founded = 0;

    /**
     * 所有musicButton的序列<br></br>id:唯一序列码
     * <p>第一位有固定格式，第二位以读音来<br></br>c : classic , 禁典;<br></br>j : japan , 异国风情;</p>
     */
    public musicButton[] musicButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation anim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_small);
        SharedPreferences sp = getSharedPreferences("BASIC_INFORMATION",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        count = sp.getInt("COUNT",0);
        surprise_count = sp.getInt("SURPRISE",0);
        is_sur_A_founded = sp.getInt("A_FOUNDED",0);
        is_sur_B_founded = sp.getInt("B_FOUNDED",0);
        is_sur_C_founded = sp.getInt("C_FOUNDED",0);

        musicButtons = new musicButton[]{
                new musicButton(findViewById(R.id.btn_rz), R.raw.bian_shi_ren_zheng),

                new musicButton(findViewById(R.id.btn_bian), R.raw.bian, "cb"),
                new musicButton(findViewById(R.id.btn_shi), R.raw.shi, "cs"),
                new musicButton(findViewById(R.id.btn_ren), R.raw.ren, "cr"),
                new musicButton(findViewById(R.id.btn_zhen), R.raw.zhen),

                new musicButton(findViewById(R.id.btn_right), R.raw.right),
                new musicButton(findViewById(R.id.btn_try), R.raw.try_it),
                new musicButton(findViewById(R.id.btn_too_young), R.raw.too_young),
                new musicButton(findViewById(R.id.btn_you_are_so_brave), R.raw.you_are_brave),

                new musicButton(findViewById(R.id.btn_hello_everyone), R.raw.hello_everyone),
                new musicButton(findViewById(R.id.btn_haha), R.raw.ha_ha_ha),
                new musicButton(findViewById(R.id.btn_fw), R.raw.fw),
                new musicButton(findViewById(R.id.btn_music), R.raw.music),

                new musicButton(findViewById(R.id.btn_xiao_huo_zi), R.raw.xiao_huo_zi),
                new musicButton(findViewById(R.id.btn_xiao_chou), R.raw.xiao_chou),
                new musicButton(findViewById(R.id.btn_very_well), R.raw.very_well),
                new musicButton(findViewById(R.id.btn_thank_you), R.raw.thank_you),

                new musicButton(findViewById(R.id.btn_call_father), R.raw.wo_jiao_ni_ba_ba),
                new musicButton(findViewById(R.id.btn_father), R.raw.ba_ba),
                new musicButton(findViewById(R.id.btn_self), R.raw.self_introduction),
                new musicButton(findViewById(R.id.btn_you_have), R.raw.how_much_do_you_hava,"ch"),
                new musicButton(findViewById(R.id.btn_i_have), R.raw.i_hava_no_idea),
                new musicButton(findViewById(R.id.btn_interest), R.raw.interest_is_the_best_teacher),

                new musicButton(findViewById(R.id.btn_jp_kmns), R.raw.jp_komenasai),
                new musicButton(findViewById(R.id.btn_jp_desu), R.raw.jp_dexuo),
                new musicButton(findViewById(R.id.btn_jp_nnxd), R.raw.jp_nanixide),
                new musicButton(findViewById(R.id.btn_jp_smms), R.raw.jp_simimase),
                new musicButton(findViewById(R.id.btn_jp_wdxw), R.raw.jp_wadaxiwa, "jw"),
        };
        for (musicButton mb : musicButtons) {
            mb.getButton().setOnClickListener(view -> {
                mb.getButton().startAnimation(anim);

                playSound(this,mb.getMusicID());
                this.checkCount();
                editor.putInt("COUNT",count).apply();

                this.surprise(mb.getId());
                if (is_sur_A_founded == 1)  editor.putInt("A_FOUNDED",1).apply();
                if (is_sur_B_founded == 1)  editor.putInt("B_FOUNDED",1).apply();
                if (is_sur_C_founded == 1)  editor.putInt("C_FOUNDED",1).apply();
                surprise_count = is_sur_A_founded + is_sur_B_founded + is_sur_C_founded;
                editor.putInt("SURPRISE",surprise_count).apply();
            });
        }

        ImageView menu = findViewById(R.id.im_menu);
        menu.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(i);
        });
    }

    public static void playSound(Context context, int id) {
        MediaPlayer mp_ps = new MediaPlayer();
        mp_ps = MediaPlayer.create(context, id);
        mp_ps.start();
        if(!mp_ps.isPlaying()) mp_ps.release();
        count++;
    }
    public synchronized static void playSoundSync (Context context, int id) {
        mp = MediaPlayer.create(context, id);
        mp.start();
        try {
            Thread.sleep(250L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!mp.isPlaying()) mp.release();
    }

    public void checkCount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("您是真爱禁");
        if (count == 10) {
            builder.setMessage("您已点击10次");
            builder.setPositiveButton("我已知晓", (dialogInterface, i) -> playSound(MainActivity.this,R.raw.bian_shi_ren_zheng));
            builder.create();
            builder.setCancelable(false);
            builder.show();
        }
        if (count == 100) {
            builder.setMessage("您已点击100次");
            builder.setPositiveButton("我已知晓", (dialogInterface, i) -> playSound(MainActivity.this,R.raw.bian_shi_ren_zheng));
            builder.create();
            builder.setCancelable(false);
            builder.show();
        }
        if (count == 1000) {
            builder.setMessage("您已点击1000次\n我们是如何走到这一步的？");
            builder.setPositiveButton("我已知晓", (dialogInterface, i) -> playSound(MainActivity.this,R.raw.bian_shi_ren_zheng));
            builder.create();
            builder.setCancelable(false);
            builder.show();
        }
        if (count == 10000) {
            builder.setMessage("您已点击10000次\n我们是如何走到这一步的？\n怎么可能？？？");
            builder.setPositiveButton("我已知晓", (dialogInterface, i) -> playSound(MainActivity.this,R.raw.bian_shi_ren_zheng));
            builder.create();
            builder.setCancelable(false);
            builder.show();
        }
    }

    public void surprise (String id) {
        if (lastClick.length() == 8) lastClick = lastClick.substring(2,8);
        lastClick += id;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("这是个彩蛋");
        if (lastClick.equals("crcscbcb")) {
            builder.setMessage("你也认识便便？");
            builder.setPositiveButton("我已知晓", (dialogInterface, i) -> {
                playSoundSync(MainActivity.this, R.raw.ren);
                playSoundSync(MainActivity.this, R.raw.shi);
                playSoundSync(MainActivity.this, R.raw.bian);
                playSoundSync(MainActivity.this, R.raw.bian);
            });
            builder.create();
            builder.setCancelable(false);
            builder.show();
            is_sur_A_founded = 1;
        }
        if (lastClick.substring(2,8).equals("jwcbcb")) {
            builder.setMessage("“瓦达西瓦便便”");
            builder.setPositiveButton("我已知晓", (dialogInterface, i) -> {
                playSoundSync(MainActivity.this, R.raw.jp_wadaxiwa);
                playSoundSync(MainActivity.this, R.raw.bian);
                playSoundSync(MainActivity.this, R.raw.bian);
            });
            builder.create();
            builder.setCancelable(false);
            builder.show();
            is_sur_B_founded = 1;
        }
        if (lastClick.substring(2,8).equals("chcbcb")) {
            builder.setMessage("“你还剩多少便便”");
            builder.setPositiveButton("我已知晓", (dialogInterface, i) -> {
                playSoundSync(MainActivity.this, R.raw.how_much_do_you_hava);
                playSoundSync(MainActivity.this, R.raw.bian);
                playSoundSync(MainActivity.this, R.raw.bian);
            });
            builder.create();
            builder.setCancelable(false);
            builder.show();
            is_sur_C_founded = 1;
        }
    }
}

/**
 * 所有的主要按钮
 */
class musicButton {
    private final Button btn;
    private final int musicID;
    /**
     * 唯一标准编码
     */
    private String id = "nn";

    public musicButton (Button btn,int id){
        this.btn = btn;
        this.musicID = id;
    }
    public musicButton (Button btn,int MusicID,String id){
        this.btn = btn;
        this.musicID = MusicID;
        this.id = id;
    }
    public Button getButton() {
        return btn;
    }

    public int getMusicID() {
        return musicID;
    }

    public String getId() {
        return id;
    }
}