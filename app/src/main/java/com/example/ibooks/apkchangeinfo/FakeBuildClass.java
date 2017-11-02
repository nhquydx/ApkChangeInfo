package com.example.ibooks.apkchangeinfo;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Parcel;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class FakeBuildClass extends MainActivity{
    private static final String TAG = "OkHttp";

    public FakeBuildClass(LoadPackageParam sharePkgParam) {



        FakeGPS(sharePkgParam);
        FakeAndroidID(sharePkgParam);
        FakeAndroidSerial(sharePkgParam);
        FakeIMEI(sharePkgParam);
        FakeBaseBand(sharePkgParam);
        FakeBuildProp(sharePkgParam);
        FakeUserAgent(sharePkgParam);
        FakeGoogleAdsID(sharePkgParam);
    }

    public void FakeUserAgent(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("com.android.webview.chromium.ContentSettingsAdapter", loadPkgParam.classLoader, "setUserAgentString", new Object[]{String.class, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.args[0] = SharedPrefs.getXValue("UserAgent");
                }
            }});
        } catch (ClassNotFoundError e) {
            XposedBridge.log("Fake UA ERROR: " + e.getMessage());
        }
        try {
            Method loadUrl1 = WebView.class.getDeclaredMethod("loadUrl", new Class[]{String.class});
            Method loadUrl2 = WebView.class.getDeclaredMethod("loadUrl", new Class[]{String.class, Map.class});
            XposedBridge.hookMethod(loadUrl1, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log("Load Url: " + param.args[0]);
                    if (param.args.length > 0 && (param.thisObject instanceof WebView)) {

                        String ua = SharedPrefs.getXValue("UserAgent");
                        WebView webView = (WebView) param.thisObject;
                        if (webView.getSettings() != null) {
                            webView.getSettings().setUserAgentString(ua);
                        }
                    }
                }
            });
            XposedBridge.hookMethod(loadUrl2, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log("load url: " + param.args[0]);
                    if (param.args.length > 0 && (param.thisObject instanceof WebView)) {
                        String ua = SharedPrefs.getXValue("UserAgent");
                        WebView webView = (WebView) param.thisObject;
                        if (webView.getSettings() != null) {
                            webView.getSettings().setUserAgentString(ua);
                        }
                    }
                }
            });
        } catch (Exception e2) {
            XposedBridge.log("Fake User Agent ERROR: " + e2.getMessage());
        }
    }

    public void FakeGPS(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.location.Location", loadPkgParam.classLoader, "getLatitude", new Object[]{new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    param.setResult(Float.valueOf(Float.parseFloat(SharedPrefs.getXValue("Latitude"))));
                }
            }});
            XposedHelpers.findAndHookMethod("android.location.Location", loadPkgParam.classLoader, "getLongitude", new Object[]{new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(Float.valueOf(Float.parseFloat(SharedPrefs.getXValue("Longitude"))));
                }
            }});
            XposedHelpers.findAndHookMethod("android.location.Location", loadPkgParam.classLoader, "getAccuracy", new Object[]{new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(Float.valueOf(Float.parseFloat(random())));
                }
            }});
            XposedHelpers.findAndHookMethod("android.location.Location", loadPkgParam.classLoader, "getAltitude", new Object[]{new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(Float.valueOf(random()));
                }
            }});
            XposedHelpers.findAndHookMethod("android.location.Location", loadPkgParam.classLoader, "getSpeed", new Object[]{new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(Float.valueOf(SharedPrefs.getXValue("Speed")));
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake GPS ERROR: " + e.getMessage());
        }
    }
    public  String random(){
        Random r = new Random();
        int ik = r.nextInt(5000 - 100 + 1) + 100;
        return  ik+"."+r.nextInt(8 - 1 + 1) + 1;
    }

    public void FakeAndroidID(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.provider.Settings.Secure", loadPkgParam.classLoader, "getString", new Object[]{ContentResolver.class, String.class, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (param.args[1].equals("android_id")) {
                        param.setResult(SharedPrefs.getXValue("AndroidID"));
                    }
                }
            }});
        } catch (Exception ex) {
            XposedBridge.log("Fake Android ID ERROR: " + ex.getMessage());
        }
    }

    public void FakeAndroidSerial(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.setStaticObjectField(XposedHelpers.findClass("android.os.Build", loadPkgParam.classLoader), "SERIAL", SharedPrefs.getXValue("Serial"));
            Class<?> classSysProp = Class.forName("android.os.SystemProperties");
            XposedHelpers.findAndHookMethod(classSysProp, "get", new Object[]{String.class, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    String serialno = (String) param.args[0];
                    if (serialno.equals("ro.serialno") || serialno.equals("ro.boot.serialno") || serialno.equals("ril.serialnumber") || serialno.equals("sys.serialnumber")) {
                        param.setResult(SharedPrefs.getXValue("Serial"));
                    }
                }
            }});
            XposedHelpers.findAndHookMethod(classSysProp, "get", new Object[]{String.class, String.class, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    String serialno = (String) param.args[0];
                    if (serialno.equals("ro.serialno") || serialno.equals("ro.boot.serialno") || serialno.equals("ril.serialnumber") || serialno.equals("sys.serialnumber")) {
                        param.setResult(SharedPrefs.getXValue("Serial"));
                    }
                }
            }});
        } catch (IllegalArgumentException ex) {
            XposedBridge.log("Fake AndroidSerial ERROR: " + ex.getMessage());
        } catch (ClassNotFoundException ex2) {
            XposedBridge.log("Fake AndroidSerial ERROR: " + ex2.getMessage());
        }
    }

    public void FakeIMEI(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getDeviceId", new Object[]{XC_MethodReplacement.returnConstant(SharedPrefs.getXValue("IMEI"))});
            XposedHelpers.findAndHookMethod("com.android.internal.telephony.PhoneSubInfo", loadPkgParam.classLoader, "getDeviceId", new Object[]{XC_MethodReplacement.returnConstant(SharedPrefs.getXValue("IMEI"))});
            if (VERSION.SDK_INT < 22) {
                XposedHelpers.findAndHookMethod("com.android.internal.telephony.gsm.GSMPhone", loadPkgParam.classLoader, "getDeviceId", new Object[]{XC_MethodReplacement.returnConstant(SharedPrefs.getXValue("IMEI"))});
                XposedHelpers.findAndHookMethod("com.android.internal.telephony.PhoneProxy", loadPkgParam.classLoader, "getDeviceId", new Object[]{XC_MethodReplacement.returnConstant(SharedPrefs.getXValue("IMEI"))});
            }
        } catch (Exception ex) {
            XposedBridge.log("Fake IMEI ERROR: " + ex.getMessage());
        }
    }

    public void FakeGoogleAdsID(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.os.Binder", loadPkgParam.classLoader, "execTransact", new Object[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (((IBinder) param.thisObject).getInterfaceDescriptor().equals("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService") && ((Integer) param.args[0]).intValue() == 1) {
                        Parcel reply = null;
                        try {
                            Class cls = Parcel.class;
                            String str = "obtain";
                            Class[] clsArr = new Class[1];
                            clsArr[0] = VERSION.SDK_INT < 21 ? Integer.TYPE : Long.TYPE;
                            Method methodObtain = cls.getDeclaredMethod(str, clsArr);
                            methodObtain.setAccessible(true);
                            reply = (Parcel) methodObtain.invoke(null, new Object[]{param.args[2]});
                        } catch (NoSuchMethodException ex) {
                            XposedBridge.log("Fake Google Ads NoSuchMethodException ERROR: " + ex.getMessage());
                        } catch (NullPointerException e) {
                            XposedBridge.log("Fake Google Ads NullPointerException ERROR: " + e.getMessage());
                        }
                        if (reply != null) {
                            reply.setDataPosition(0);
                            reply.writeNoException();
                            reply.writeString(SharedPrefs.getXValue("GAID"));
                        }
                        param.setResult(Boolean.valueOf(true));
                    }
                }
            }});
        } catch (Exception ex) {
            XposedBridge.log("Fake Google Ads ID ERROR: " + ex.getMessage());
        }
    }

    public void FakeBaseBand(LoadPackageParam loadPkgParam) {
        try {
            if (VERSION.SDK_INT <= 14) {
                XposedHelpers.setStaticObjectField(XposedHelpers.findClass("android.os.Build", loadPkgParam.classLoader), "RADIO", SharedPrefs.getXValue("Baseband"));
                return;
            }
            XposedHelpers.findAndHookMethod("android.os.Build", loadPkgParam.classLoader, "getRadioVersion", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(SharedPrefs.getXValue("Baseband"));
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake BaseBand ERROR: " + e.getMessage());
        }
    }

    public void FakeBuildProp(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findField(Build.class, "BOARD").set(null,SharedPrefs.getXValue("Board"));
            XposedHelpers.findField(Build.class, "BRAND").set(null, SharedPrefs.getXValue("Brand"));
            XposedHelpers.findField(Build.class, "CPU_ABI").set(null, SharedPrefs.getXValue("CpuAbi"));
            XposedHelpers.findField(Build.class, "CPU_ABI2").set(null,SharedPrefs.getXValue("CpuAbi2"));
            XposedHelpers.findField(Build.class, "DEVICE").set(null, SharedPrefs.getXValue("Device"));
            XposedHelpers.findField(Build.class, "DISPLAY").set(null,SharedPrefs.getXValue("Display"));
            XposedHelpers.findField(Build.class, "FINGERPRINT").set(null, SharedPrefs.getXValue("Fingerprint"));
            XposedHelpers.findField(Build.class, "HARDWARE").set(null, SharedPrefs.getXValue("Hardware"));
            XposedHelpers.findField(Build.class, "ID").set(null,SharedPrefs.getXValue("Id"));
            XposedHelpers.findField(Build.class, "MANUFACTURER").set(null, SharedPrefs.getXValue("Manufacturer"));
            XposedHelpers.findField(Build.class, "MODEL").set(null,SharedPrefs.getXValue("Model"));
            XposedHelpers.findField(Build.class, "PRODUCT").set(null, SharedPrefs.getXValue("Device"));
            XposedHelpers.findField(Build.class, "BOOTLOADER").set(null, SharedPrefs.getXValue("Incremental"));
            XposedHelpers.findField(Build.class, "HOST").set(null, SharedPrefs.getXValue("Host"));
            XposedHelpers.findField(VERSION.class, "INCREMENTAL").set(null, SharedPrefs.getXValue("Incremental"));
            XposedHelpers.findField(VERSION.class, "RELEASE").set(null,SharedPrefs.getXValue("Release"));
            XposedHelpers.findField(VERSION.class, "SDK").set(null, SharedPrefs.getXValue("Sdk"));
            XposedHelpers.findField(VERSION.class, "CODENAME").set(null, SharedPrefs.getXValue("Codename"));
        } catch (IllegalAccessException e) {
            XposedBridge.log("Fake BuilProp ERROR: " + e.getMessage());
        } catch (IllegalArgumentException e2) {
            XposedBridge.log("Fake BuilProp ERROR: " + e2.getMessage());
        }
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            if (cls != null) {
                for (Member mem : cls.getDeclaredMethods()) {
                    XposedBridge.hookMethod(mem, new XC_MethodHook() {
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            if (param.args.length > 0 && param.args[0] != null && param.args[0].equals("ro.build.description")) {
                                param.setResult(SharedPrefs.getXValue("Description"));
                            }
                        }
                    });
                }
            }
        } catch (ClassNotFoundException e3) {
            XposedBridge.log("Fake DESCRIPTION ERROR: " + e3.getMessage());
        }
    }
}