package com.android.notepad;

public class Memo {
    int seq;
    String mtitle;
    String mcontent;
    String mdate;
    int isdone;

    public Memo(int seq, String mtitle, String mcontent, String mdate, int isdone) {
        this.seq = seq;
        this.mtitle = mtitle;
        this.mcontent = mcontent;
        this.mdate = mdate;
        this.isdone = isdone;
    }

    public Memo(String mtitle, String mcontent, String mdate, int isdone) {
        this.mtitle = mtitle;
        this.mcontent = mcontent;
        this.mdate = mdate;
        this.isdone = isdone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }
}
