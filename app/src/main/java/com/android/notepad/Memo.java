package com.android.notepad;

import java.io.Serializable;
//객체로 넘기기위해 serializable implemets함
public class Memo implements Serializable {
    private static final long serialVersionUID = 1L;
    int seq;//메모에 필요한 primary값
    String mtitle;//제목
    String mcontent;//내용
    String mdate;//날짜
    int isdone;//테스트위한 변수

    public Memo(String mtitle, String mcontent, String mdate) {
        this.mtitle = mtitle;
        this.mcontent = mcontent;
        this.mdate = mdate;
    }

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
