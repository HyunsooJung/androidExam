package com.notes.recycler;

public class MainData {

    private String tv_name;
    private int  btn_del;


    public MainData(String tv_name, int btn_del) {
        this.tv_name = tv_name;
        this.btn_del = btn_del;
    }

    public MainData() {
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public int getBtn_del() {
        return btn_del;
    }

    public void setBtn_del(int btn_del) {
        this.btn_del = btn_del;
    }
}
