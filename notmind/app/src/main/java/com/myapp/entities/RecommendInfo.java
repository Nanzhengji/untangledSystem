package com.myapp.entities;

public class RecommendInfo {
    public int id;

    public String rec_title;
    public String rec_content;
    public RecommendInfo( String recomend_title, String recomend_content){

        this.rec_title=recomend_title;
        this.rec_content=recomend_content;
    }

    public String getRec_title() {
        return rec_title;
    }

    public String getRec_content() {
        return rec_content;
    }

}
