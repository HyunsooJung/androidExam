package com.notes.recycler;

public class ActivityData extends MainData {
    String appName;
    Class<?> cls;

    public ActivityData() {
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}
