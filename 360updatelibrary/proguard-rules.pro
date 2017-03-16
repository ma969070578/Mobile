-optimizationpasses 5
-dontusemixedcaseclassnames

-dontskipnonpubliclibraryclasses
-dontpreverify

-renamesourcefileattribute AppStore
-keepattributes SourceFile,LineNumberTable,Exceptions

-verbose
-dontwarn com.android.internal.telephony.**
-keep interface android.os.*** {
	<methods>;
	<fields>;
}

-keep class android.os.*** {
	<methods>;
	<fields>;
}

-keep interface com.android.*** {
	<methods>;
	<fields>;
}

-keep class com.android.*** {
	<methods>;
	<fields>;
}

#for universalimageloader
-dontwarn com.nostra13.universalimageloader.core.decode.ImageDecodingInfo

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# Android隐藏API的Aidl都不能混淆
# AppStore项目中的
-keep public class android.content.pm.IPackageInstallObserver2{*; }

# HideApiLib项目中的

-dontwarn android.content.pm.**
-dontwarn android.content.**
-dontwarn android.app.**
-dontwarn android.os.storage.**
-dontwarn com.android.internal.app.**

-keep class com.qihoo.appstore.webview.JavascriptInterface {
    <methods>;
    <fields>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Keep serializable classes and necessary members for serializable classes
# Copied from the ProGuard manual at http://proguard.sourceforge.net/manual/examples.html#serializable
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Copied from the ProGuard manual at http://proguard.sourceforge.net/manual/examples.html#resourcefiles
-adaptresourcefilenames    **.properties,**.gif,**.jpg
-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF

#WebView 4.0以上和4.0以下 支持上传文件
-keep public class * extends android.webkit.WebChromeClient {
	public void openFileChooser(android.webkit.ValueCallback, java.lang.String, java.lang.String);
	public void openFileChooser(android.webkit.ValueCallback);
}

-keep class com.chameleonui.circular.progress.button.* { *; }

# 打点sdk接口
-dontwarn com.qihoo.sdk.report.**
-keep class com.qihoo.sdk.report.** {*;}

# 双卡SDK接口 begin
-keep class com.qihoo360.mobilesafe.dual.DualMainEntry {
  public *;
}
-keep class com.qihoo360.mobilesafe.dual.base.** {
    *;
}
-keep interface com.qihoo360.mobilesafe.dual.IDualModule {
    *;
}

-dontwarn com.qihoo360.mobilesafe.dual.**

-keep class com.tencent.mm.sdk.**{*;}
-dontwarn com.tencent.mm.sdk.**

-keep class com.qihoo.appstore.utils.AppstoreSharePref {*;}
-keep class com.qihoo.appstore.utils.ApplicationConfig {*;}

-dontwarn com.qihoo.appstore.install.**
-keep class com.qihoo.appstore.install.** {*;}

-dontwarn com.qihoo.appstore.rooter.**
-keep class com.qihoo.appstore.rooter.** {*;}

-dontwarn com.qihoo.exec.**
-keep class com.qihoo.exec.** {*;}

-dontwarn com.qihoo.permmgr.**
-keep class com.qihoo.permmgr.** {*;}

-dontwarn android.support.multidex.**
-keep class android.support.multidex.** {*;}
-keep class com.qihoo360.mobilesafe.shell.Cmd{
    public static boolean hasSuCmd();
}
# VolleyPro
-keepclassmembers class com.android.volley.Request { *** mUrl; *** mTag; *** mErrorListener; }
-keepclassmembers class * extends com.android.volley.Request { *** mListener; }
-keepclassmembers class com.android.volley.RequestQueue { *** mWaitingRequests; }
-keepclassmembers class com.android.volley.DefaultRetryPolicy { *** mCurrentRetryCount; }
-keepclassmembers class com.android.volley.toolbox.DiskBasedCache { *** mRootDirectory; *** putEntry(java.lang.String, com.android.volley.toolbox.DiskBasedCache$CacheHeader); }
-keepclassmembers class com.android.volley.toolbox.DiskBasedCache$CacheHeader { *** size; *** key; *** readHeader(java.io.InputStream); }

-keep class com.chameleonui.circular.WaveDrawable { *; }
-keep class com.chameleonui.arcanim.* { *; }
-keep class com.chameleonui.pullrefresh.* { *; }

#for uninstall inject

-keep public class com.qihoo.xhook.Jar{
    public static <methods>;
}

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

-keepclasseswithmembers public class com.morgoo.hook.NativeHelper {
      *** nativeHandleHookedMethod(...);
      *** nativeHa(...);
      *** nativeHb(...);
      *** nativeHc(...);
      *** nativeHd(...);
      *** nativeHe(...);
}
-keep class cn.hiroz.uninstallfeedback.** { *; }

# 腾讯分享SDK
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

#WebView 4.0以上和4.0以下 支持上传文件
-keep public class * extends android.webkit.WebChromeClient {
	public void openFileChooser(android.webkit.ValueCallback, java.lang.String, java.lang.String);
	public void openFileChooser(android.webkit.ValueCallback);
}

#守护进程
-keep class com.qihoo.appstore.persistent.CoreDaemon {
	<methods>;
}

#拉活SDK
-dontwarn com.qihoo.alliance.**
-keep class com.qihoo.alliance.AppInfo { *;}

#root模块要打点,反射调用StatHelper.onEvent方法
-keep public class com.qihoo.appstore.stat.StatHelper{
    public static void onRootEvent(java.lang.String, java.lang.String);
}
#root模块要打点,反射调用StatHelper_3.onEvent方法(从3.0版本移植过来的)
-keep public class com.qihoo.appstore.stat.StatHelper_3{
    public static void onEvent(android.content.Context, java.lang.String, java.lang.String);
}

#语音识别模块
-dontwarn com.iflytek.**
-keep class com.iflytek.** { *;}

#艾瑞统计模块
-dontwarn cn.com.iresearch.mapptracker.**
-keep class cn.com.iresearch.mapptracker.**{
*;
}

#TalkingData统计模块
-dontwarn com.tendcloud.tenddata.**
-keep public class com.tendcloud.tenddata.** { public protected *;}

# java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
-keepclassmembers class android.support.v4.app.FragmentActivity { *** mFragments; }
-keepclassmembers class * extends android.support.v4.app.FragmentManager { *** noteStateNotSaved(); }

-keep class com.qihoo.appstore.utils.LauncherHelper { *; }

#cocosplay_lib
-ignorewarnings
-keep class com.cocos.play.**{ *;}

-keep public class com.qihoo.appstore.download.gift.BonusActivity{
    public void onAcceptAwardSuccess(java.lang.Object);
    public void onAcceptAwardFailed();
}

