#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include "bitmap/BitmapOperation.h"
#include "skinsmooth/AmniXSkinBeauty.h"

#define  LOG_TAG    "Aman"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniInitBeauty(
        JNIEnv *env, jobject obj, jobject handle) {
    JniBitmap *jniBitmap = (JniBitmap *) env->GetDirectBufferAddress(handle);
    if (jniBitmap->_storedBitmapPixels == NULL) {
        LOGE("no bitmap data was stored. returning null...");
        return;
    }
    AmniXSkinBeauty::getInstance()->initBeauty(jniBitmap);
}

JNIEXPORT void JNICALL Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniStartWhiteSkin(
        JNIEnv *env, jobject obj, jfloat whiteLevel) {
    AmniXSkinBeauty::getInstance()->startWhiteSkin(whiteLevel);
}

JNIEXPORT void JNICALL
Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniStartFullBeauty(JNIEnv *env, jobject instance,
                                                                 jfloat skinSmoothLevel,
                                                                 jfloat whitenessLevel) {
    AmniXSkinBeauty::getInstance()->_startBeauty(skinSmoothLevel,whitenessLevel);
}

JNIEXPORT void JNICALL Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniStartSkinSmooth(
        JNIEnv *env, jobject obj, jfloat DenoiseLevel) {
    AmniXSkinBeauty::getInstance()->startSkinSmooth(DenoiseLevel);
}

JNIEXPORT void JNICALL Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniUninitBeauty(
        JNIEnv *env, jobject obj) {
    AmniXSkinBeauty::getInstance()->unInitBeauty();
}

JNIEXPORT jobject JNICALL Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniStoreBitmapData(
        JNIEnv *env, jobject obj, jobject bitmap) {
    return BitmapOperation::jniStoreBitmapData(env, obj, bitmap);
}

/**free bitmap*/
JNIEXPORT void JNICALL Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniFreeBitmapData(
        JNIEnv *env, jobject obj, jobject handle) {
    BitmapOperation::jniFreeBitmapData(env, obj, handle);
}

/**restore java bitmap (from JNI data)*/ //
JNIEXPORT jobject JNICALL Java_com_amnix_skinsmoothness_AmniXSkinSmooth_jniGetBitmapFromStoredBitmapData(
        JNIEnv *env, jobject obj, jobject handle) {
    return BitmapOperation::jniGetBitmapFromStoredBitmapData(env, obj, handle);
}
#ifdef __cplusplus
}
#endif
