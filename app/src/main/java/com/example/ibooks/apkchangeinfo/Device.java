package com.example.ibooks.apkchangeinfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iBooks on 9/13/2017.
 */

public class Device {
    String Device;


    String Baseband;
    String Board;
    String Bootloader;
    String Brand;
    String Codename;
    String CpuAbi;
    String CpuAbi2;
    String Description;
    String Display;
    String Fingerprint;
    String GlRenderer;
    String GlVendor;
    String Hardware;
    String Host;
    String Id;
    String Incremental;
    String Manufacturer;
    String Model;
    String OsArch;
    String OsName;
    String OsVersion;
    String Product;
    String Release_data;
    String ScreenX;
    String ScreenY;
    String Sdk;
    String Tags;
    String Type;
    String UserAgent;

    String NetworkOperatorName;
    String NetworkCountryIso;
    String NetworkOperator;



    String MacAddress;
    String AndroidID;
    String TimeZone;
    String Latitude;
    String Longitude;
    String Isp;
    String Org;


    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getNetworkOperatorName() {
        return NetworkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        NetworkOperatorName = networkOperatorName;
    }
    public String getBaseband() {
        return Baseband;
    }

    public void setBaseband(String baseband) {
        Baseband = baseband;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getBootloader() {
        return Bootloader;
    }

    public void setBootloader(String bootloader) {
        Bootloader = bootloader;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCodename() {
        return Codename;
    }

    public void setCodename(String codename) {
        Codename = codename;
    }

    public String getCpuAbi() {
        return CpuAbi;
    }

    public void setCpuAbi(String cpuAbi) {
        CpuAbi = cpuAbi;
    }

    public String getCpuAbi2() {
        return CpuAbi2;
    }

    public void setCpuAbi2(String cpuAbi2) {
        CpuAbi2 = cpuAbi2;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }

    public String getFingerprint() {
        return Fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        Fingerprint = fingerprint;
    }

    public String getGlRenderer() {
        return GlRenderer;
    }

    public void setGlRenderer(String glRenderer) {
        GlRenderer = glRenderer;
    }

    public String getGlVendor() {
        return GlVendor;
    }

    public void setGlVendor(String glVendor) {
        GlVendor = glVendor;
    }

    public String getHardware() {
        return Hardware;
    }

    public void setHardware(String hardware) {
        Hardware = hardware;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIncremental() {
        return Incremental;
    }

    public void setIncremental(String incremental) {
        Incremental = incremental;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getOsArch() {
        return OsArch;
    }

    public void setOsArch(String osArch) {
        OsArch = osArch;
    }

    public String getOsName() {
        return OsName;
    }

    public void setOsName(String osName) {
        OsName = osName;
    }

    public String getOsVersion() {
        return OsVersion;
    }

    public void setOsVersion(String osVersion) {
        OsVersion = osVersion;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getRelease_data() {
        return Release_data;
    }

    public void setRelease_data(String release_data) {
        Release_data = release_data;
    }

    public String getScreenX() {
        return ScreenX;
    }

    public void setScreenX(String screenX) {
        ScreenX = screenX;
    }

    public String getScreenY() {
        return ScreenY;
    }

    public void setScreenY(String screenY) {
        ScreenY = screenY;
    }

    public String getSdk() {
        return Sdk;
    }

    public void setSdk(String sdk) {
        Sdk = sdk;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public String getNetworkCountryIso() {
        return NetworkCountryIso;
    }

    public void setNetworkCountryIso(String networkCountryIso) {
        NetworkCountryIso = networkCountryIso;
    }

    public String getNetworkOperator() {
        return NetworkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        NetworkOperator = networkOperator;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public String getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(String timeZone) {
        TimeZone = timeZone;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getIsp() {
        return Isp;
    }

    public void setIsp(String isp) {
        Isp = isp;
    }

    public String getOrg() {
        return Org;
    }

    public void setOrg(String org) {
        Org = org;
    }

}
