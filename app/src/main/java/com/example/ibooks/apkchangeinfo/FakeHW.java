package com.example.ibooks.apkchangeinfo;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.io.File;

public class FakeHW {
    public FakeHW(LoadPackageParam sharePkgParam) {
        FakeBluetooth(sharePkgParam);
        FakeWifi(sharePkgParam);
        FakeCPUFile(sharePkgParam);
        FakeTelephony(sharePkgParam);
    }

    public void FakeCPUFile(LoadPackageParam loadPkgParam) {
        try {
            XposedBridge.hookAllConstructors(File.class, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args.length == 1) {
                        XposedBridge.log("All constructor " + param.args[0].toString());
                        if (param.args[0].equals("/proc/cpuinfo")) {
                            param.args[0] = "/data/data/com.example.ibooks.apkchangeinfo/cpuinfo";
                        }
                        if (param.args[0].equals("/proc/meminfo")) {
                            param.args[0] = "/data/data/com.example.ibooks.apkchangeinfo/meminfo";
                        }
                    } else if (param.args.length == 2 && !File.class.isInstance(param.args[0])) {
                        int i = 0;
                        String str = "";
                        while (i < 2) {
                            String stringBuilder;
                            if (param.args[i] != null) {
                                XposedBridge.log("AS " + param.args[i].toString());
                                if (param.args[i].equals("/proc/cpuinfo")) {
                                    param.args[i] = "/data/data/com.example.ibooks.apkchangeinfo/cpuinfo";
                                }
                                if (param.args[i].equals("/proc/meminfo")) {
                                    param.args[i] = "/data/data/com.example.ibooks.apkchangeinfo/meminfo";
                                }
                                stringBuilder = new StringBuilder(String.valueOf(str)).append(param.args[i]).append(":").toString();
                            } else {
                                stringBuilder = str;
                            }
                            i++;
                            str = stringBuilder;
                        }
                    }
                }
            });
            XposedHelpers.findAndHookMethod("java.lang.Runtime", loadPkgParam.classLoader, "exec", new Object[]{String[].class, String[].class, File.class, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args.length == 1) {
                        XposedBridge.log(param.args[0].toString());
                        if (param.args[0].equals("/proc/cpuinfo")) {
                            param.args[0] = "/data/data/com.example.ibooks.apkchangeinfo/cpuinfo";
                    }
                        if (param.args[0].equals("/proc/meminfo")) {
                            param.args[0] = "/data/data/com.example.ibooks.apkchangeinfo/meminfo";
                        }
                    } else if (param.args.length == 2 && !File.class.isInstance(param.args[0])) {
                        int i = 0;
                        String str = "";
                        while (i < 2) {
                            String stringBuilder;
                            if (param.args[i] != null) {
                                XposedBridge.log("runtime " + param.args[i].toString());
                                if (param.args[i].equals("/proc/cpuinfo")) {
                                    param.args[i] = "/data/data/com.example.ibooks.apkchangeinfo/cpuinfo";
                                }
                                if (param.args[i].equals("/proc/meminfo")) {
                                    param.args[i] = "/data/data/com.example.ibooks.apkchangeinfo/meminfo";
                                }
                                stringBuilder = new StringBuilder(String.valueOf(str)).append(param.args[i]).append(":").toString();
                            } else {
                                stringBuilder = str;
                            }
                            i++;
                            str = stringBuilder;
                        }
                    }
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake CPUFile - 1 ERROR: " + e.getMessage());
        }
        try {
            XposedBridge.hookMethod(XposedHelpers.findConstructorExact(ProcessBuilder.class, new Class[]{String[].class}), new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args[0] != null) {
                        String[] strArr = (String[]) param.args[0];
                        String str = "";
                        for (String str2 : strArr) {
                            str = new StringBuilder(String.valueOf(str)).append(str2).append(":").toString();
                            XposedBridge.log("AS " + str2.toString());
                            if (str2 == "/proc/cpuinfo") {
                                strArr[1] = "/data/data/com.example.ibooks.apkchangeinfo/cpuinfo";
                            }
                            if (str2 == "/proc/meminfo") {
                                strArr[1] = "/data/data/com.example.ibooks.apkchangeinfo/meminfo";
                            }
                        }
                        param.args[0] = strArr;
                    }
                }
            });
        } catch (Exception e2) {
            XposedBridge.log("Fake CPUFile - 2 ERROR: " + e2.getMessage());
        }
        try {
            XposedHelpers.findAndHookMethod("java.util.regex.Pattern", loadPkgParam.classLoader, "matcher", new Object[]{CharSequence.class, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args.length == 1) {
                        XposedBridge.log("AS " + param.args[0].toString());
                        if (param.args[0].equals("/proc/cpuinfo")) {
                            param.args[0] = "/data/data/com.example.ibooks.apkchangeinfo/cpuinfo";
                        }
                        if (param.args[0].equals("/proc/meminfo")) {
                            param.args[0] = "/data/data/com.example.ibooks.apkchangeinfo/meminfo";
                        }
                    }
                }
            }});
        } catch (Exception e22) {
            XposedBridge.log("Fake CPU(Pattern) ERROR: " + e22.getMessage());
        }
    }

    public void FakeBluetooth(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.bluetooth.BluetoothAdapter", loadPkgParam.classLoader, "getAddress", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(SharedPrefs.getXValue("BlueAddress"));
                }
            }});
            XposedHelpers.findAndHookMethod("android.bluetooth.BluetoothDevice", loadPkgParam.classLoader, "getAddress", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(SharedPrefs.getXValue("BlueAddress"));
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake Bluetooth ERROR: " + e.getMessage());
        }
    }

    public void FakeWifi(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPkgParam.classLoader, "getMacAddress", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(SharedPrefs.getXValue("MacAddress"));
                }
            }});
            XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPkgParam.classLoader, "getSSID", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(SharedPrefs.getXValue("WifiName"));
                }
            }});
            XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPkgParam.classLoader, "getBSSID", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(SharedPrefs.getXValue("BlueAddress"));
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake Wifi ERROR: " + e.getMessage());
        }
    }

    public void FakeTelephony(LoadPackageParam loadPkgParam) {
        String TelePhone = "android.telephony.TelephonyManager";
        HookTelephony(TelePhone, loadPkgParam, "getDeviceId", SharedPrefs.getXValue("IMEI"));
        HookTelephony(TelePhone, loadPkgParam, "getSubscriberId", SharedPrefs.getXValue("SubID"));
        HookTelephony(TelePhone, loadPkgParam, "getLine1Number", SharedPrefs.getXValue("PhoneNumber"));
        HookTelephony(TelePhone, loadPkgParam, "getSimSerialNumber", SharedPrefs.getXValue("SerialSim"));
        HookTelephony(TelePhone, loadPkgParam, "getNetworkOperator", SharedPrefs.getXValue("NetworkOperator"));
        HookTelephony(TelePhone, loadPkgParam, "getNetworkOperatorName", SharedPrefs.getXValue("NetworkOperatorName"));
        HookTelephony(TelePhone, loadPkgParam, "getSimOperator", SharedPrefs.getXValue("NetworkOperator"));
        HookTelephony(TelePhone, loadPkgParam, "getSimOperatorName", SharedPrefs.getXValue("NetworkOperatorName"));
        HookTelephony(TelePhone, loadPkgParam, "getNetworkCountryIso", SharedPrefs.getXValue("NetworkCountryIso"));
        HookTelephony(TelePhone, loadPkgParam, "getSimCountryIso", SharedPrefs.getXValue("NetworkCountryIso"));
        try {
            XposedHelpers.findAndHookMethod(System.class, "getProperty", new Object[]{String.class, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (param.args[0] == "os.version") {
                        param.setResult(SharedPrefs.getXValue("OsVersion"));
                    }
                    if (param.args[0] == "os.name") {
                        param.setResult(SharedPrefs.getXValue("OsName"));
                    }
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake OS ERROR: " + e.getMessage());
        }
    }

    private void HookTelephony(String hookClass, LoadPackageParam loadPkgParam, String funcName, final String value) {
        try {
            XposedHelpers.findAndHookMethod(hookClass, loadPkgParam.classLoader, funcName, new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(value);
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake " + funcName + " ERROR: " + e.getMessage());
        }
    }

    private byte[] ConvertMactoByte(String _MacAdd) {
        String[] Mac_part = _MacAdd.split(":");
        byte[] macbyteadd = new byte[6];
        for (int i = 0; i < 6; i++) {
            macbyteadd[i] = Integer.valueOf(Integer.parseInt(Mac_part[i], 16)).byteValue();
        }
        return macbyteadd;
    }
}