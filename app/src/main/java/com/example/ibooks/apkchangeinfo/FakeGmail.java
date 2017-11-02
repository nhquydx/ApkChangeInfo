package com.example.ibooks.apkchangeinfo;

import android.accounts.Account;
import android.text.TextUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.util.Arrays;

public class FakeGmail {
    public void fakeGmail(final LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.accounts.AccountManager", loadPkgParam.classLoader, "getAccounts", new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (FakeGmail.getPackage(SharedPrefs.getXValue("FAKE_GMAIL"), loadPkgParam.packageName)) {
                        param.setResult(new Account[]{new Account(SharedPrefs.getXValue("GMAIL_INPUT"), "com.google")});
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("android.accounts.AccountManager", loadPkgParam.classLoader, "getAccountsByType", new Object[]{String.class, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (FakeGmail.getPackage(SharedPrefs.getXValue("FAKE_GMAIL"), loadPkgParam.packageName)) {
                        param.setResult(new Account[]{new Account(SharedPrefs.getXValue("GMAIL_INPUT"), "com.google")});
                    }
                }
            }});
        } catch (Exception e) {
            XposedBridge.log("Fake Email ERROR: " + e.getMessage());
        }
    }

    public static boolean getPackage(String lisPkg, String pkg) {
        if (TextUtils.isEmpty(lisPkg)) {
            return false;
        }
        return Arrays.asList(TextUtils.split(lisPkg.replace(" ", ""), ",")).contains(pkg);
    }
}