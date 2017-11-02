package com.example.ibooks.apkchangeinfo;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.util.Arrays;

public class FakeGPU {
    public void FakeDisplay(LoadPackageParam loadPackageParam) {
        try {
            XposedHelpers.findAndHookMethod("com.google.android.gles_jni.GLImpl", loadPackageParam.classLoader, "glGetString", new Object[]{Integer.TYPE, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args[0] == null) {
                        return;
                    }
                    if (param.args[0].equals(Integer.valueOf(7936))) {
                        param.setResult(SharedPrefs.getXValue("GlVendor"));
                    } else if (param.args[0].equals(Integer.valueOf(7937))) {
                        param.setResult(SharedPrefs.getXValue("GlRenderer"));
                    }
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Error when fake GL_VENDOR|GL_RENDERER");
        }
        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPackageParam.classLoader, "getMetrics", new Object[]{DisplayMetrics.class, new XC_MethodHook(-10000) {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    int dpi = FakeGPU.tryParseInt(SharedPrefs.getXValue("DensityDpi"));
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.densityDpi = dpi;
                    metrics.density = ((float) dpi) / 160.0f;
                    metrics.heightPixels = FakeGPU.tryParseInt(SharedPrefs.getXValue("ScreenX"));
                    metrics.widthPixels = FakeGPU.tryParseInt(SharedPrefs.getXValue("ScreenY"));
                    float f2 = ((float) Math.sqrt((double) ((metrics.widthPixels * metrics.widthPixels) + (metrics.heightPixels * metrics.heightPixels)))) / Float.parseFloat(SharedPrefs.getXValue("DeviceInch"));
                    metrics.ydpi = f2;
                    metrics.xdpi = f2;
                }
            }});
        } catch (Exception e2) {
            XposedBridge.log("Fake DPI ERROR: " + e2.getMessage());
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.Sensor", loadPackageParam.classLoader, "getVendor", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (!param.getResult().toString().contains("Google") && !param.getResult().toString().contains("samsung")) {
                        return;
                    }
                    if (FakeGPU.RandomNumber(0, 2) == 1) {
                        param.setResult("BOSCH");
                    } else {
                        param.setResult("AVAGO");
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("android.hardware.Sensor", loadPackageParam.classLoader, "getName", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (param.getResult().toString().equals("Accelerometer Sensor")) {
                        param.setResult("Accelerometer/Temperature/Double-tap");
                    }
                    if (param.getResult().toString().equals("Gyroscope Sensor")) {
                        param.setResult("Gyroscope");
                    }
                    if (param.getResult().toString().equals("Megnetic Field Sensor")) {
                        param.setResult("Magnetometer");
                    }
                    if (param.getResult().toString().equals("Rotation Vector Sensor")) {
                        param.setResult("Rotation Vector");
                    }
                    if (param.getResult().toString().equals("Gravity Sensor")) {
                        param.setResult("Gravity");
                    }
                    if (param.getResult().toString().equals("Linear Acceleration Sensor")) {
                        param.setResult("Linear Acceleration");
                    }
                    if (param.getResult().toString().equals("Orientation Sensor")) {
                        param.setResult("Orientation");
                    }
                    if (param.getResult().toString().equals("Corrected Gyroscope Sensor")) {
                        param.setResult("APDS-9930/QPDS-T930 Proximity & Light||QTI@@Step Detector||QTI@@Gravity||QTI@@Linear Acceleration||QTI@@Rotation Vector||QTI@@Step Detector||QTI@@Step Counter||QTI@@Significant Motion Detector||QTI@@Game Rotation Vector||QTI@@GeoMagnetic Rotation Vector||QTI@@Orientation||QTI@@Tilt Detector");
                    }
                }
            }});
        } catch (Exception e22) {
            XposedBridge.log("Fake Sensor ERROR: " + e22.getMessage());
        }
    }

    public static boolean getPackage(String lisPkg, String pkg) {
        if (TextUtils.isEmpty(lisPkg)) {
            return false;
        }
        return Arrays.asList(TextUtils.split(lisPkg.replace(" ", ""), ",")).contains(pkg);
    }

    public static int RandomNumber(int i, int i2) {
        return (int) (Math.random() * ((double) (i2 - i)));
    }

    private static int tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 320;
        }
    }
}