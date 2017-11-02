package com.example.ibooks.apkchangeinfo;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        new FakeBattery().fakePinStt(lpparam);
        new FakeGPU().FakeDisplay(lpparam);
       // new HideRoot().handleLoadPackage(lpparam);
        FakeHW fakeHW = new FakeHW(lpparam);
      //  new FakeGmail().fakeGmail(lpparam);
        FakeBuildClass fakeBuildClass = new FakeBuildClass(lpparam);
      //  new SetStatusClock().SetClock(lpparam);
      //  new TestPackage().Test(lpparam);
    }
}