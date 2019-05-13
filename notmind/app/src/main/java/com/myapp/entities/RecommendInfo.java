package com.myapp.entities;

public class RecommendInfo {
    public int id;
    public int imgid;
    public String rec_title;
    public String rec_content;
    public RecommendInfo(int imgid, String recomend_title, String recomend_content){
        this.imgid=imgid;
        this.rec_title=recomend_title;
        this.rec_content=recomend_content;
    }

    public String getRec_title() {
        return rec_title;
    }

    public String getRec_content() {
        return rec_content;
    }
    public int getImgid(){
        return imgid;
    }
}
