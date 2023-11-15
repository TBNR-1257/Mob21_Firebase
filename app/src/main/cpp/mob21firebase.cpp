#include <jni.h>

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.

extern "C"
JNIEXPORT jstring JNICALL
Java_com_bryan1_mob21firebase_core_utils_NativeUtils_greet(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Hello from Native (CPP)");
}