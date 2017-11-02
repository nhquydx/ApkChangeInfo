package com.example.ibooks.apkchangeinfo;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OkHttp";
    SharedPrefs mySP;
    String  myResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            run();
            writeToMemi();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        TextView textView = (TextView)findViewById(R.id.Test);
        textView.setText(tmDevice);
    }
    private void writeToFile(String data,String fileName) {
        try {
            File file = new File("data/data/com.example.ibooks.apkchangeinfo/"+fileName);
            FileOutputStream outputStreamWriter = new FileOutputStream(file);
            outputStreamWriter.write(data.getBytes());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private void writeToMemi() {
        try {
            Random r = new Random();
            int i1 = r.nextInt(3 - 1 + 1) + 1;
            int MemTotal=(i1*1024*1024);
            int i11 = r.nextInt(50 - 30 + 1) + 30;
            String Memfree=""+(MemTotal-((MemTotal*i11)/100));
            if(Memfree.length()==7){
                Memfree=Memfree;
            }else {
                Memfree=" "+Memfree;
            }
            String str="MemTotal:        "+MemTotal+" kB\n" +
            "MemFree:         "+Memfree+" kB\n" +
            "Buffers:           15604 kB\n" +
            "Cached:           397096 kB\n" +
            "SwapCached:            0 kB\n" +
            "Active:           51"+i1+"720 kB\n" +
            "Inactive:         202228 kB\n" +
            "Active(anon):     304"+i1+"52 kB\n" +
            "Inactive(anon):     1992 kB\n" +
            "Active(file):     210"+i1+"68 kB\n" +
            "Inactive(file):   1829"+i1+"6 kB\n" +
            "Unevictable:           0 kB\n" +
            "Mlocked:               0 kB\n" +
            "SwapTotal:             0 kB\n" +
            "SwapFree:              0 kB\n" +
            "Dirty:                "+i1+"6 kB\n" +
            "Writeback:             0 kB\n" +
            "AnonPages:        304248 kB\n" +
            "Mapped:           126872 kB\n" +
            "Shmem:             196"+i11+" kB\n" +
            "Slab:              16744 kB\n" +
            "SReclaimable:       7424 kB\n" +
            "SUnreclaim:         93"+i1+"0 kB\n" +
            "KernelStack:        4624 kB\n" +
            "PageTables:         5452 kB\n" +
            "NFS_Unstable:          0 kB\n" +
            "Bounce:                0 kB\n" +
            "WritebackTmp:          0 kB\n" +
            "CommitLimit:      756968 kB\n" +
            "Committed_AS:   10717"+i11+"0 kB\n" +
            "VmallocTotal:     520188 kB\n" +
            "VmallocUsed:       3"+i11+"24 kB\n" +
            "VmallocChunk:     456700 kB\n" +
            "DirectMap4k:           0 kB\n" +
            "DirectMap4M:           0 kB\n";
            File file = new File("data/data/com.example.ibooks.apkchangeinfo/meminfo");
            FileOutputStream outputStreamWriter = new FileOutputStream(file);
            outputStreamWriter.write(str.getBytes());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    @SuppressLint({"ShowToast"})
    void run() throws IOException {
        mySP = new SharedPrefs(getApplicationContext());
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://qn.mobistore.xyz/infodevices.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                myResponse   = response.body().string();

                JSONObject json = null;
                try {

                    json = new JSONObject(myResponse);
                    mySP.setSharedPrefs("GAID" ,json.getString("GAID"));
                    mySP.setSharedPrefs("SubID",json.getString("SubID"));
                    mySP.setSharedPrefs("Serial",json.getString("Serial"));
                    mySP.setSharedPrefs("Brand",json.getString("Brand"));
                    mySP.setSharedPrefs("SerialSim",json.getString("SerialSim"));
                    mySP.setSharedPrefs("MacAddress",json.getString("MacAddress"));
                    mySP.setSharedPrefs("BlueAddress",json.getString("BlueAddress"));

                    mySP.setSharedPrefs("AndroidID",json.getString("AndroidID"));
                    mySP.setSharedPrefs("IMEI",json.getString("IMEI"));
                    mySP.setSharedPrefs("Baseband" ,json.getString("Incremental"));
                    mySP.setSharedPrefs("Board",json.getString("Board"));
                    mySP.setSharedPrefs("Bootloader",json.getString("Incremental"));
                    mySP.setSharedPrefs("Brand",json.getString("Brand"));
                    mySP.setSharedPrefs("Codename",json.getString("Codename"));
                    mySP.setSharedPrefs("CpuAbi",json.getString("CpuAbi"));
                    mySP.setSharedPrefs("CpuAbi2",json.getString("CpuAbi2"));
                    mySP.setSharedPrefs("Description",json.getString("Description"));
                    mySP.setSharedPrefs("Device",json.getString("Device"));
                    mySP.setSharedPrefs("Display",json.getString("Display"));
                    mySP.setSharedPrefs("Fingerprint",json.getString("Fingerprint"));
                    mySP.setSharedPrefs("GlRenderer",json.getString("GlRenderer"));
                    mySP.setSharedPrefs("GlVendor",json.getString("GlVendor"));
                    mySP.setSharedPrefs("Hardware",json.getString("Hardware"));
                    mySP.setSharedPrefs("Host",json.getString("Host"));
                    mySP.setSharedPrefs("Id",json.getString("Id"));
                    mySP.setSharedPrefs("Incremental",json.getString("Incremental"));
                    mySP.setSharedPrefs("Manufacturer",json.getString("Manufacturer"));
                    mySP.setSharedPrefs("Model",json.getString("Model"));
                    mySP.setSharedPrefs("OsName",json.getString("OsName"));
                    mySP.setSharedPrefs("OsVersion",json.getString("OsVersion"));
                    mySP.setSharedPrefs("Release",json.getString("Release"));
                    mySP.setSharedPrefs("ScreenX",json.getString("ScreenX"));
                    mySP.setSharedPrefs("ScreenY",json.getString("ScreenY"));
                    mySP.setSharedPrefs("Sdk","19");
                    mySP.setSharedPrefs("DeviceInch",json.getString("DeviceInch"));
                    mySP.setSharedPrefs("DensityDpi",json.getString("DensityDpi"));
                    mySP.setSharedPrefs("Tags","release-keys");
                    mySP.setSharedPrefs("Type","user");
                    mySP.setSharedPrefs("NetworkCountryIso",json.getString("NetworkCountryIso"));
                    mySP.setSharedPrefs("NetworkOperator",json.getString("NetworkOperator"));
                    mySP.setSharedPrefs("NetworkOperatorName",json.getString("NetworkOperatorName"));
                    mySP.setSharedPrefs("TimeZone",json.getString("TimeZone"));
                    mySP.setSharedPrefs("Latitude",json.getString("Latitude"));
                    mySP.setSharedPrefs("Longitude",json.getString("Longitude"));
                    mySP.setSharedPrefs("Isp",json.getString("Isp"));
                    mySP.setSharedPrefs("Org",json.getString("Org"));
                    mySP.setSharedPrefs("WifiName",json.getString("WifiName"));
                    String PhoneNum=json.getString("PhoneNumber")+phoneNumber();
                    mySP.setSharedPrefs("PhoneNumber",PhoneNum);
                    mySP.setSharedPrefs("Language",json.getString("Language"));
                    writeToFile(json.getString("CPUInfo"),"cpuinfo");
                    CreateBuild(json.getString("Id"),json.getString("Display"),json.getString("Incremental"),json.getString("Release"),"user",json.getString("Host"),json.getString("Model"),json.getString("Brand"),json.getString("Device"),json.getString("Board"),json.getString("CpuAbi"),json.getString("CpuAbi2"),json.getString("Manufacturer"),json.getString("Language"),json.getString("Description"),json.getString("Fingerprint"),json.getString("NetworkOperator"),json.getString("NetworkOperatorName"),json.getString("NetworkCountryIso"),PhoneNum);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }
    public void CreateBuild(String id,String Display,String Incremental,String Release,String Type,String Host,String Model,String Brand,String Device,String Board,String CpuAbi,String CpuAbi2,String Manufacturer,String Language,String Description,String Fingerprint,String NetworkOperator,String NetworkOperatorName,String NetworkCountryIso,String PhoneNumber ) {
        StringBuilder sb = new StringBuilder();
        sb.append("# begin build properties\n# autogenerated by buildinfo.sh");
        sb.append("\nro.build.id=").append(id);
        sb.append("\nro.build.display.id=").append(Display);
        sb.append("\nro.build.version.incremental=").append(Incremental);
        sb.append("\nro.build.version.sdk=19");
        sb.append("\nro.build.version.codename=REL");
        sb.append("\nro.build.version.release=").append(Release);
        sb.append("\nro.build.date=Wed Apr  9 02:52:29 KST 2014");
        sb.append("\nro.build.date.utc=1396979549");
        sb.append("\nro.build.type=").append(Type);
        sb.append("\nro.build.user=").append("dpi");
        sb.append("\nro.build.host=").append(Host);
        sb.append("\nro.build.tags=release-keys");
        sb.append("\nro.product.model=").append(Model);
        sb.append("\nro.product.brand=").append(Brand);
        sb.append("\nro.product.name=").append(Model);
        sb.append("\nro.product.device=").append(Device);
        sb.append("\nro.product.board=").append(Board);
        sb.append("\nro.product.cpu.abi=").append(CpuAbi);
        sb.append("\nro.product.cpu.abi2=").append(CpuAbi2);
        sb.append("\nro.product.manufacturer=").append(Manufacturer);
        String[] separated = Language.split("-");
        String langue="";
        String region="";
        if(separated.length-1>0){
            langue=separated[0];
            region=separated[1];
        }else {
            langue=Language;
            region=Language;
        }
        sb.append("\nro.product.locale.language=").append(langue);
        sb.append("\nro.product.locale.region=").append(region);
        sb.append("\nro.wifi.channels=\nro.board.platform=exynos4\n# ro.build.product is obsolete; use ro.product.device\nro.build.product=t03g\n# Do not try to parse ro.build.description or .fingerprint\nro.sf.lcd_density=160");
        sb.append("\nro.build.description=").append(Description);
        sb.append("\nro.build.fingerprint=").append(NetworkOperator);
        sb.append("\nro.build.characteristics=phone");
        sb.append("\nro.build.hidden_ver=N7100XXUFND3\nro.build.changelist=1280411\nro.product_ship=true\nro.chipname=smdk4x12\n# end build properties\n\n#\n# ADDITIONAL_BUILD_PROPERTIES\n#\ndalvik.vm.heapstartsize=8m\ndalvik.vm.heapmaxfree=8m\ndalvik.vm.heapgrowthlimit=96m\ndalvik.vm.heapsize=256m\ndalvik.vm.heapminfree=512k\ndalvik.vm.dexopt-flags=m=y\ndalvik.vm.heaptargetutilization=0.75\ndalvik.vm.stack-trace-file=/data/anr/traces.txt\nro.opengles.version=131072\nro.ril.hsxpa=1\nro.ril.gprsclass=10\nro.config.ringtone=Spectrum.ogg\nro.config.notification_sound=Zirconium.ogg\nro.config.alarm_alert=Scandium.ogg\nro.config.media_sound=Spectrum.ogg\nkeyguard.no_require_sim=true\nro.com.android.dataroaming=true\nro.com.android.dateformat=MM-dd-yyyy\nro.carrier=unknown\nro.com.google.clientidbase=android-samsung\nro.setupwizard.mode=OPTIONAL\npersist.sys.dalvik.vm.lib=libdvm.so\nnet.bt.name=Android\n\n#\n# Misc Properties\n#\nro.com.google.gmsversion=4.4.2_r1\nro.com.google.clientidbase=android-samsung\ngsm.version.ril-impl=\"Samsung RIL(IPC) v1.0\"");
        sb.append("\ngsm.sim.operator.numeric=").append(NetworkOperator);
        sb.append("\ngsm.sim.operator.alpha=").append(NetworkOperatorName);
        sb.append("\ngsm.sim.operator.iso-country=").append(NetworkCountryIso);
        sb.append("\ngsm.sim.state=READY");
        sb.append("\ngsm.current.phone-type=1");
        sb.append("\ngsm.operator.alpha=").append(NetworkOperatorName);
        sb.append("\ngsm.operator.numeric=").append(NetworkOperator);
        sb.append("\ngsm.operator.iso-country=").append(NetworkCountryIso);
        sb.append("\ngsm.operator.isroaming=false");
        sb.append("\ngsm.network.type=UMTS");
        sb.append("\ngsm.sim.bstserial=").append(PhoneNumber);

        try {
            Writer out = new OutputStreamWriter(new FileOutputStream("/data/data/com.example.ibooks.apkchangeinfo/build.prop.new"));
            out.write(sb.toString());
            out.close();
            DataOutputStream os = new DataOutputStream(Runtime.getRuntime().exec("su").getOutputStream());
            os.writeBytes("mount -o remount,rw /system\n");
            os.flush();
            os.writeBytes("chmod 777 /system\n");
            os.flush();
            os.writeBytes("cp  /data/data/com.example.ibooks.apkchangeinfo/build.prop.new /system/build.prop\n");
            os.flush();
            os.writeBytes("chmod 644 /system/build.propp\n");
            os.flush();
            os.writeBytes("pm clear com.google.android.gms\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            os.close();
        } catch (Exception e) {

        }
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream("/data/data/com.example.ibooks.apkchangeinfo/build.prop.new"));
            out.write(sb.toString());
            out.close();
            DataOutputStream os = new DataOutputStream(Runtime.getRuntime().exec("su").getOutputStream());
            os.writeBytes("mount -o remount,rw /data\n");
            os.flush();
            os.writeBytes("chmod 777 /data\n");
            os.flush();
            os.writeBytes("cp  /data/data/com.example.ibooks.apkchangeinfo/build.prop.new /data/.bluestacks.prop\n");
            os.flush();
            os.writeBytes("chmod 644 /data/.bluestacks.prop\n");
            os.flush();
            os.writeBytes("pm clear com.google.android.gms\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            os.close();
        } catch (Exception e) {

        }
    }
    public void CreateBuild1(String id,String Display,String Incremental,String Release,String Type,String Host,String Model,String Brand,String Device,String Board,String CpuAbi,String CpuAbi2,String Manufacturer,String Language,String Description,String Fingerprint,String NetworkOperator,String NetworkOperatorName,String NetworkCountryIso,String PhoneNumber ) {
        StringBuilder sb = new StringBuilder();
        sb.append("# begin build properties\n# autogenerated by buildinfo.sh");
        sb.append("\nro.build.id=").append(id);
        sb.append("\nro.build.display.id=").append(Display);
        sb.append("\nro.build.version.incremental=").append(Incremental);
        sb.append("\nro.build.version.sdk=19");
        sb.append("\nro.build.version.codename=REL");
        sb.append("\nro.build.version.release=").append(Release);
        sb.append("\nro.build.date=Wed Apr  9 02:52:29 KST 2014");
        sb.append("\nro.build.date.utc=1396979549");
        sb.append("\nro.build.type=").append(Type);
        sb.append("\nro.build.user=").append("dpi");
        sb.append("\nro.build.host=").append(Host);
        sb.append("\nro.build.tags=release-keys");
        sb.append("\nro.product.model=").append(Model);
        sb.append("\nro.product.brand=").append(Brand);
        sb.append("\nro.product.name=").append(Model);
        sb.append("\nro.product.device=").append(Device);
        sb.append("\nro.product.board=").append(Board);
        sb.append("\nro.product.cpu.abi=").append(CpuAbi);
        sb.append("\nro.product.cpu.abi2=").append(CpuAbi2);
        sb.append("\nro.product.manufacturer=").append(Manufacturer);
        String[] separated = Language.split("-");
        String langue="";
        String region="";
        if(separated.length-1>0){
            langue=separated[0];
            region=separated[1];
        }else {
            langue=Language;
            region=Language;
        }
        sb.append("\nro.product.locale.language=").append(langue);
        sb.append("\nro.product.locale.region=").append(region);
        sb.append("\nro.wifi.channels=\nro.board.platform=exynos4\n# ro.build.product is obsolete; use ro.product.device\nro.build.product=t03g\n# Do not try to parse ro.build.description or .fingerprint\nro.sf.lcd_density=160");
        sb.append("\nro.build.description=").append(Description);
        sb.append("\nro.build.fingerprint=").append(NetworkOperator);
        sb.append("\nro.build.characteristics=phone");
        sb.append("\nro.config.rm_preload_enable=0\nro.build.knox.container=\n#persist.sys.storage_perload=1\n# end build properties\n\n#\n# ADDITIONAL_BUILD_PROPERTIES\n#\ndalvik.vm.heapstartsize=8m\ndalvik.vm.heapmaxfree=8m\ndalvik.vm.heapgrowthlimit=96m\ndalvik.vm.heapsize=256m\ndalvik.vm.heapminfree=512k\ndalvik.vm.dexopt-flags=m=y\ndalvik.vm.heaptargetutilization=0.75\ndalvik.vm.stack-trace-file=/data/anr/traces.txt\nro.opengles.version=131072\nro.ril.hsxpa=1\nro.ril.gprsclass=10\nro.config.ringtone=Spectrum.ogg\nro.config.notification_sound=Zirconium.ogg\nro.config.alarm_alert=Scandium.ogg\nro.config.media_sound=Spectrum.ogg\nkeyguard.no_require_sim=true\nro.com.android.dataroaming=true\nro.com.android.dateformat=MM-dd-yyyy\nro.carrier=unknown\nro.com.google.clientidbase=android-samsung\nro.setupwizard.mode=OPTIONAL\npersist.sys.dalvik.vm.lib=libdvm.so\nnet.bt.name=Android");

        try {
            Writer out = new OutputStreamWriter(new FileOutputStream("/data/data/com.example.ibooks.apkchangeinfo/build.prop.new1"));
            out.write(sb.toString());
            out.close();
            DataOutputStream os = new DataOutputStream(Runtime.getRuntime().exec("su").getOutputStream());
            os.writeBytes("cat /data/data/com.example.ibooks.apkchangeinfo/build.prop.new >/system/build.prop\n");
            os.flush();
            os.writeBytes("pm clear com.google.android.gms\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
        } catch (Exception e) {

        }
    }


    public String phoneNumber() {
        Random rand = new Random();
        int num1 = (((rand.nextInt(7) + 1) * 100) + (rand.nextInt(8) * 10)) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);
        DecimalFormat df3 = new DecimalFormat("000");
        return df3.format((long) num1) + df3.format((long) num2) + new DecimalFormat("0000").format((long) num3);
    }

}










