package com.crystalx.jinusicbox;

import static com.crystalx.jinusicbox.MainActivity.count;
import static com.crystalx.jinusicbox.MainActivity.editor;
import static com.crystalx.jinusicbox.MainActivity.playSound;
import static com.crystalx.jinusicbox.MainActivity.playSoundLegato;
import static com.crystalx.jinusicbox.Notes.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ImageView;

import com.crystalx.generalduty.util.ChordArraysUtil;

import java.util.Objects;

/**
 * 键盘活动类
 * @see MainActivity
 */
public class KeyboardActivity extends AppCompatActivity {
    private static musicButton[] musicButton_key;
    private static TypeButton[] typeButtons_chord;
    private static NoteButton[] noteButtons_chord;
    private static Notes selectedNote = Notes.C4;
    private static Chords.Type selectedType = Chords.Type.Major;
    private static Chords selectedChord;
    private static Button chord_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        //初始化
        chord_start = findViewById(R.id.btn_start_chord);
        refreshRecentChord();

        //退出按钮
        ImageView back = findViewById(R.id.back_keyboard);
        back.setOnClickListener(view -> KeyboardActivity.this.finish());

        musicButton_key = new musicButton[]{
                new musicButton(findViewById(R.id.key_c), Notes.C4 ),
                new musicButton(findViewById(R.id.key_cs),Notes.C4s),
                new musicButton(findViewById(R.id.key_d), Notes.D4 ),
                new musicButton(findViewById(R.id.key_ds),Notes.D4s),
                new musicButton(findViewById(R.id.key_e), Notes.E4 ),
                new musicButton(findViewById(R.id.key_f), Notes.F4 ),
                new musicButton(findViewById(R.id.key_fs),Notes.F4s),
                new musicButton(findViewById(R.id.key_g), Notes.G4 ),
                new musicButton(findViewById(R.id.key_gs),Notes.G4s),
                new musicButton(findViewById(R.id.key_a), Notes.A4 ),
                new musicButton(findViewById(R.id.key_as),Notes.A4s),
                new musicButton(findViewById(R.id.key_b), Notes.B4 ),
        };

        for (musicButton mb : musicButton_key) {
            mb.getButton().setOnClickListener(view -> {
                refreshKeyColor(getResources().getColor(R.color.key_black),getResources().getColor(R.color.key_white));
                mb.getButton().setBackgroundColor(getResources().getColor(R.color.key_selected));
                //播放音乐
                playSoundLegato(this, mb.getMusicID());
                //更新点击数
                count++;
                editor.putInt("COUNT", count).apply();
            });
        }

        typeButtons_chord = new TypeButton[]{
                new TypeButton(findViewById(R.id.chord_maj), Chords.Type.Major),
                new TypeButton(findViewById(R.id.chord_min), Chords.Type.Minor),
                new TypeButton(findViewById(R.id.chord_dim), Chords.Type.Diminished),
                new TypeButton(findViewById(R.id.chord_aug), Chords.Type.Augmented),
                new TypeButton(findViewById(R.id.chord_sus2),Chords.Type.Suspension2),
                new TypeButton(findViewById(R.id.chord_sus4),Chords.Type.Suspension4),
        };
        noteButtons_chord = new NoteButton[]{
                new NoteButton(findViewById(R.id.chord_c ),Notes.C4 ),
                new NoteButton(findViewById(R.id.chord_cs),Notes.C4s),
                new NoteButton(findViewById(R.id.chord_d ),Notes.D4 ),
                new NoteButton(findViewById(R.id.chord_ds),Notes.D4s),
                new NoteButton(findViewById(R.id.chord_e ),Notes.E4 ),
                new NoteButton(findViewById(R.id.chord_f ),Notes.F4 ),
                new NoteButton(findViewById(R.id.chord_fs),Notes.F4s),
                new NoteButton(findViewById(R.id.chord_g ),Notes.G4 ),
                new NoteButton(findViewById(R.id.chord_gs),Notes.G4s),
                new NoteButton(findViewById(R.id.chord_a ),Notes.A4 ),
                new NoteButton(findViewById(R.id.chord_as),Notes.A4s),
                new NoteButton(findViewById(R.id.chord_b ),Notes.B4 ),
        };

        for (TypeButton t : typeButtons_chord) {
            t.getBtn().setOnClickListener(v -> {
                selectedType = t.getType();
                refreshRecentChord();
            });
        }
        for (NoteButton n : noteButtons_chord) {
            n.getBtn().setOnClickListener(v -> {
                selectedNote = n.getRootNote();
                refreshRecentChord();
            });
        }
        chord_start.setOnClickListener(v -> {
            //播放和弦
            selectedChord.play(KeyboardActivity.this);
            //更新点击数
            count++;
            editor.putInt("COUNT", count).apply();
        });
    }

    static void refreshKeyColor(int black,int white) {
        for (musicButton mb : musicButton_key) {
            if (mb.getId().contains("s")) {
                mb.getButton().setBackgroundColor(black);
            }else {
                mb.getButton().setBackgroundColor(white);
            }
        }
    }

    static void refreshRecentChord () {
        selectedChord = new Chords(selectedNote,selectedType);
        chord_start.setText(selectedChord.toString());
    }

    //TODO 在界面中实现typeButton和noteButton颜色
    static void refreshNoteKeyColor () {}
    static void refreshTypeKeyColor () {}
}

/**
 * 所有哎呀音符
 * @since 1.5c
 */
enum Notes {
    C4 (R.raw.ay_pitched_c ,"cc4",1 ,"C" ),
    C4s(R.raw.ay_pitched_cs,"cs4",2 ,"C#"),
    D4 (R.raw.ay_pitched_d ,"dd4",3 ,"D" ),
    D4s(R.raw.ay_pitched_ds,"ds4",4 ,"D#"),
    E4 (R.raw.ay_pitched_e ,"ee4",5 ,"E" ),
    F4 (R.raw.ay_pitched_f ,"ff4",6 ,"F" ),
    F4s(R.raw.ay_pitched_fs,"fs4",7 ,"F#"),
    G4 (R.raw.ay_pitched_g ,"gg4",8 ,"G" ),
    G4s(R.raw.ay_pitched_gs,"gs4",9 ,"G#"),
    A4 (R.raw.ay_pitched_a ,"aa4",10,"A" ),
    A4s(R.raw.ay_pitched_as,"as4",11,"A#"),
    B4 (R.raw.ay_pitched_b ,"bb4",12,"B" );

    public static final Notes[] notes = new Notes[]{C4 , C4s, D4 , D4s, E4 , F4 , F4s, G4 , G4s, A4 , A4s, B4 ,};
    private final int musicID;
    private final String buttonID,displayStyle;
    private final int absolutePitch;

    /**
     * 音符的构造函数
     * @param musicID 音频在raw对应值
     * @param buttonID 编码id
     * @param absolutePitch 绝对音高(用于计算和弦)
     * @param displayStyle 在和弦中的显示形式
     * @since 1.5a
     */
    Notes (int musicID,String buttonID,int absolutePitch,String displayStyle){
        this.musicID = musicID;
        this.buttonID = buttonID;
        this.absolutePitch = absolutePitch;
        this.displayStyle = displayStyle;
    }

    public int getMusicID() {
        return musicID;
    }

    public String getButtonID() {
        return buttonID;
    }

    public int getAbsolutePitch() {
        return absolutePitch;
    }

    public String getDisplayStyle() {
        return displayStyle;
    }

    public int getMusicIDByPitch(int pitch) throws NoteNotSupportedException{
        int res = 0;
        for (Notes e : notes) {
            if (e.getAbsolutePitch() == pitch) res = e.getMusicID();
        }
        if (res == 0) throw new NoteNotSupportedException();
        return res;
    }
}

/**
 * 以哎呀音符组成的和弦<p>构造函数:
 * {@link Chords#Chords(Notes, Type)}
 * @see Notes
 * @since 1.5c
 */
class Chords implements ChordArraysUtil{
    private final Notes rootNote;
    private final Type type;
    private ChordAddition[] additions;
    private boolean hasAddition = false;

    //TODO 把三，七和弦等用接口分类组织起来，便于管理
    enum Type {
        Major(root -> new int[]{root, root +4, root +7},"maj"),
        Minor(root -> new int[]{root, root +3, root +7},"min"),
        Augmented(root -> new int[]{root, root +4, root +8},"aug"),
        Diminished(root -> new int[]{root, root +3, root +6},"dim"),
        Suspension2(root -> new int[]{root, root +2, root +6},"sus2"),
        Suspension4(root -> new int[]{root, root +4, root +6},"sus4");

        private final String displayStyle;
        private final Sequencer intervalCalculator;

        /**
         * 和弦种类的构造函数
         * @param intervalCalculator 音程计算器(函数式接口实现): {@link Sequencer#get(int)}
         *                           <p>例如对大三和弦：</p>
         *                           <p><strong>数学形式：</strong>int[] f(x)={x,x+4,x+7}</p>
         *                           <p>f(x)中的数值对应都是绝对音高</p><p></p>
         * @param displayStyle 在和弦中的显示形式
         * @see Chords
         */
        Type (Sequencer intervalCalculator, String displayStyle) {
            this.intervalCalculator = intervalCalculator;
            this.displayStyle = displayStyle;
        }

        public Sequencer getIntervalCalculator() {
            return intervalCalculator;
        }
        public String getDisplayStyle() {
            return displayStyle;
        }
        public static final Type[] types = {Major,Minor,Augmented,Diminished,Suspension2,Suspension4};
    }

    static class ChordAddition{
        private final String displayStyle;
        private final Sequencer intervalCalculator;
        AdditionType type;
        public ChordAddition(String displayStyle, Sequencer intervalCalculator, AdditionType type) {
            this.displayStyle = displayStyle;
            this.intervalCalculator = intervalCalculator;
            this.type = type;
        }

        public String getDisplayStyle() {
            return displayStyle;
        }

        public Sequencer getIntervalCalculator() {
            return intervalCalculator;
        }

        public AdditionType getType() {
            return type;
        }


    }

    public int[] getSequence() {
        int[] res = type.getIntervalCalculator().get(rootNote.getAbsolutePitch());
        if(hasAddition){
            for (ChordAddition ca : additions) {
                res = ChordArraysUtil.combine(res,ca.getIntervalCalculator().get(rootNote.getAbsolutePitch()),ca.type);
            }
        }
        return res;
    }
    //TODO 完善add2，omit3，/E之类


    /**
     * 用于计算和弦的函数式接口
     */
    @FunctionalInterface
    public interface Sequencer {
        int[] get(int root);
    }
    public void play (Context context) {
        int[] NoteSequence = getSequence();
        try {
            for (int pitch : NoteSequence) playSound(context, rootNote.getMusicIDByPitch(pitch));
        }catch (NoteNotSupportedException e) {
            e.showDialog(context);
        }
    }

    /**
     * 和弦的直接构造函数
     * @param rootNote 根音
     * @param type 和弦种类
     * @see Notes
     * @see Type
     */
    public Chords(@NonNull Notes rootNote,@NonNull Type type){
        this.type = type;
        this.rootNote = rootNote;
    }

    /**
     * 和弦的直接构造函数
     * @param rootNote 根音
     * @param type 和弦种类
     * @param addition 额外说明
     * @see Notes
     * @see Type
     */
    public Chords(Notes rootNote,Type type,ChordAddition... addition){
        this.type = type;
        this.rootNote = rootNote;
        this.additions = addition;
        hasAddition = true;
    }

    @NonNull
    public String toString() {
        StringBuilder res = new StringBuilder();
        res = new StringBuilder(rootNote.getDisplayStyle() + type.getDisplayStyle());
        if (this.hasAddition) {
            for (ChordAddition ca : additions) {
                if (ca.type == AdditionType.Extra) res.append(ca.getDisplayStyle());
            }
            res.append("(");
            for (ChordAddition ca : additions) {
                if (ca.type != AdditionType.Extra) res.append(ca.getDisplayStyle());
                res.append(",");
            }
            res.append(")");
            if (res.toString().contains(",)")) res.replace(0, res.length(),")");
            if (res.toString().contains("()")) res.substring(0,res.length());
        }
        return res.toString();
    }

    //TODO 解析字符串成和弦
    private Chords ChordsFactory (@NonNull String src) {
        String Note,Type = null,Type_Addition;
        String[] Addition = new String[10];
        boolean hasAddition;

        //分离note和type_addition
        if (src.substring(0, 2).contains("#")) {
            Note = src.substring(0, 2);
            Type_Addition = src.substring(2);
        }else {
            Note = src.substring(0, 1);
            Type_Addition = src.substring(1);
        }

        //分离type和addition
        if (Type_Addition.length() == 3) {
            Type = Type_Addition;
            hasAddition = false;
            Type_Addition = Type_Addition.substring(3);
        }else if (Type_Addition.contains("sus")){
            Type = Type_Addition.substring(0,4);
            Type_Addition = Type_Addition.substring(4);
        }
        if (Type_Addition.length() != 0) {
            hasAddition = true;
            Type = Type_Addition.substring(0,3);
            Type_Addition = Type_Addition.substring(3);
            int index = 0;
            if (Type_Addition.contains("/")) {
                if (Type_Addition.contains("#")) {
                    Addition[index] = Type_Addition.substring(0,2);
                }else {
                    Addition[index] = Type_Addition.substring(0,1);
                }
                index++;
            }
            if (Type_Addition.contains("(")) {
                Type_Addition = Type_Addition.substring(1,Type_Addition.length() - 1);
                String[] trans = Type_Addition.split(",");
                for (String s : trans) {
                    Addition[index] = s;
                    index++;
                }
            }
        }

        //解析note，type，addition
        Notes realNote = Notes.C4;
        Type realType = Chords.Type.Major;
        ChordAddition[] realAddition = new ChordAddition[10];
        for (Notes n : notes) {
            if (Objects.equals(n.getDisplayStyle(), Note)) realNote = n;
        }
        for (Chords.Type t : Chords.Type.types) {
            if (t.getDisplayStyle().equalsIgnoreCase(Type)) realType = t;
        }
        //TODO 解析Addition
        //for (int i = 0;i < Addition.length;i++) {
        //    if (Addition[i].contains("/")) {
        //
        //    }
        //}


        return new Chords(realNote,realType);
    }
    public Chords[] praiseStringToChords (@NonNull String src){
        String[] tokens = src.split(" ");
        Chords[] res = new Chords[tokens.length];
        for (int i = 0;i < tokens.length - 1;i++) res[i] = ChordsFactory(tokens[i]);
        return res;
    }
}

/**
 * 音符不存在错误<p>
 * 显示对话框: {@link NoteNotSupportedException#showDialog(Context)}
 * @see Notes
 * @see Chords
 */
class NoteNotSupportedException extends Exception {
    public void showDialog (Context context) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle("提示");
        b.setPositiveButton("我已知晓",null);
        b.setMessage("部分音符不支持");
        b.create();
        b.show();
    }
}

class NoteButton {
    private final Notes rootNote;
    private final Button btn;

    public NoteButton (Button btn,Notes note){
        this.rootNote = note;
        this.btn = btn;
    }

    public Notes getRootNote() {
        return rootNote;
    }
    public Button getBtn() {
        return btn;
    }
}

class TypeButton {
    private final Chords.Type type;
    private final Button btn;
    public TypeButton (Button btn,Chords.Type type){
        this.type = type;
        this.btn = btn;
    }

    public Chords.Type getType() {
        return type;
    }
    public Button getBtn() {
        return btn;
    }
}
