//
// Created by Administrator on 2023/10/28.
//

#ifndef JINUSICBOX_SOUNDPLAYER_H
#define JINUSICBOX_SOUNDPLAYER_H
#include <jni.h>
#include <android/log.h>
#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>
#include <cstring>
#include <cstdlib>
#include <unistd.h>
#include <iostream>
#include <thread>

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>

static SLObjectItf engineObject = nullptr;
static SLEngineItf engineEngine = nullptr;
static SLObjectItf outputMixObject = nullptr;

// 音频播放回调方法
// 音频播放回调方法
void playCallback(SLAndroidSimpleBufferQueueItf caller, void *context) {
    SLPlayItf player = static_cast<SLPlayItf>(context); // 将context转换为SLPlayItf
    SLuint32 event;
    (*player)->GetPlayState(player, &event);  // 获取播放状态

    if (event == SL_PLAYSTATE_STOPPED) {
        // 播放结束时的处理
        (*player)->SetPlayState(player, SL_PLAYSTATE_STOPPED);
    }
}



#endif //JINUSICBOX_SOUNDPLAYER_H
