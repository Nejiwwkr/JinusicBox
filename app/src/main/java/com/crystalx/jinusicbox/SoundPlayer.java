package com.crystalx.jinusicbox;

import android.content.res.AssetManager;

public class SoundPlayer {
    static {
        System.loadLibrary("jinusicbox");
    }

    public static native void playSound(AssetManager assetManager, int id);
}
