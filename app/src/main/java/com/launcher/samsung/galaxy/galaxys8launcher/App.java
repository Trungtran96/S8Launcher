package com.launcher.samsung.galaxy.galaxys8launcher;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Trung Tran Thanh on 10/3/2017.
 */

public class App implements Serializable{
    private Drawable icon;
    private String name;
    private String packageName;
    private int icon2;

    public App(Drawable icon, String name, String packageName) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
    }

    public App(Drawable icon, String name, String packageName, int icon2) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
        this.icon2 = icon2;
    }

    public App(String name, int icon2) {
        this.name = name;
        this.icon2 = icon2;
    }

    public int getIcon2() {
        return icon2;
    }

    public void setIcon2(int icon2) {
        this.icon2 = icon2;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
