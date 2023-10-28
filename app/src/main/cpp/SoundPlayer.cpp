//
// Created by Administrator on 2023/10/28.
//

#include "SoundPlayer.h"
extern "C" JNIEXPORT void JNICALL
Java_com_crystalx_jinusicbox_SoundPlayer_playSound(JNIEnv *env, jclass clazz, jobject assetManager, jint id) {
    // 创建AssetManager
    AAssetManager *mgr = AAssetManager_fromJava(env, assetManager);

    // 获取音频文件名
    const char *audioFile = "sound.mp3";

    // 打开音频文件
    AAsset *asset = AAssetManager_open(mgr, audioFile, AASSET_MODE_UNKNOWN);

    // 获取文件大小
    off_t fileSize = AAsset_getLength(asset);

    // 创建缓冲区
    void *buffer = malloc(fileSize);
    AAsset_read(asset, buffer, fileSize);
    AAsset_close(asset);

    // 设置音频源格式
    SLDataLocator_AndroidFD loc_fd = {SL_DATALOCATOR_ANDROIDFD, -1, SL_DATALOCATOR_ANDROIDFD_USE_FILE_SIZE};
    SLDataFormat_MIME format_mime = {SL_DATAFORMAT_MIME, nullptr, SL_CONTAINERTYPE_UNSPECIFIED};
    SLDataSource audioSrc = {&loc_fd, &format_mime};

    // 设置音频目标格式
    SLDataLocator_OutputMix loc_outmix = {SL_DATALOCATOR_OUTPUTMIX, outputMixObject};
    SLDataSink audioSnk = {&loc_outmix, nullptr};

    // 创建音频播放器
    SLuint32 numInterfaces = 2;
    SLInterfaceID ids[2] = {SL_IID_PLAY, SL_IID_BUFFERQUEUE};
    SLboolean req[2] = {SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE};
    SLObjectItf playerObject = nullptr;
    SLEnvironmentalReverbSettings reverbSettings = SL_I3DL2_ENVIRONMENT_PRESET_STONECORRIDOR;
    const SLInterfaceID ids1[1] = {SL_IID_EFFECTSEND};
    const SLboolean req1[1] = {SL_BOOLEAN_FALSE};

    (*engineEngine)->CreateAudioPlayer(engineEngine, &playerObject, &audioSrc, &audioSnk, numInterfaces, ids, req);
    (*playerObject)->Realize(playerObject, SL_BOOLEAN_FALSE);
    SLPlayItf playerPlay;
    (*playerObject)->GetInterface(playerObject, SL_IID_PLAY, &playerPlay);

    // 注册回调方法
    //(*playerPlay)->RegisterCallback(playerPlay, playCallback, playerPlay);

    // 设置缓冲队列
    SLAndroidSimpleBufferQueueItf playerBufferQueue;
    (*playerObject)->GetInterface(playerObject, SL_IID_ANDROIDSIMPLEBUFFERQUEUE, &playerBufferQueue);
    (*playerBufferQueue)->Clear(playerBufferQueue);

    // 将音频数据加入缓冲队列
    (*playerBufferQueue)->Enqueue(playerBufferQueue, buffer, fileSize);
    (*playerPlay)->SetPlayState(playerPlay, SL_PLAYSTATE_PLAYING);

    // 注册回调方法
    (*playerBufferQueue)->RegisterCallback(playerBufferQueue, playCallback,(void *) playerBufferQueue);

    // 释放资源
    free(buffer);
}

