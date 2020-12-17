package com.launcher.samsung.galaxy.galaxys8launcher;

/**
 * Created by Trung Tran Thanh on 9/20/2017.
 */

public class DefaultApp {
    private String name;
    private int icon;

    public DefaultApp(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
