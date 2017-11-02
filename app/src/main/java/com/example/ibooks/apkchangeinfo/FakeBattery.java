package com.example.ibooks.apkchangeinfo;


import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.util.Random;

public class FakeBattery {
    public void fakePinStt(LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.content.Intent", loadPkgParam.classLoader, "getIntExtra", new Object[]{String.class, Integer.TYPE, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args[0] != null) {
                        if (param.args[0] == "temperature") {
                            param.setResult(Integer.valueOf(FakeBattery.this.randomInt(400, 1000)));
                        }
                        if (param.args[0] == "level") {
                            param.setResult(Integer.valueOf(FakeBattery.this.randomInt(40, 100)));
                        }
                        if (param.args[0] == "plugged") {
                            param.setResult(Integer.valueOf(FakeBattery.this.random02()));
                        }
                        if (param.args[0] == "status") {
                            param.setResult(Integer.valueOf(FakeBattery.this.random24()));
                        }
                        if (param.args[0] == "health") {
                            param.setResult(Integer.valueOf("2"));
                        }
                    }
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake Pin ERROR: " + e.getMessage());
        }
    }

    private String random02() {
        String[] arrayValue = new String[]{"0", "1", "2"};
        return arrayValue[new Random().nextInt(arrayValue.length)];
    }

    private int randomInt(int max) {
        return new Random().nextInt(100);
    }

    private int randomInt(int min, int max) {
        return new Random().nextInt(min + max);
    }

    private String random24() {
        String[] arrayValue = new String[]{"2", "3", "4"};
        return arrayValue[new Random().nextInt(arrayValue.length)];
    }
}