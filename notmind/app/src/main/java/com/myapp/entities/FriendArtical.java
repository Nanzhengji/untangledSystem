package com.myapp.entities;

import java.util.Date;

public class FriendArtical {
    public String user_id;
    public String user_name;
    public String artical_id;
    public String a_content;
    public Date a_date;
    public int img;
    public FriendArtical(String user_id, String user_name, String artical_id, String a_content, Date a_date){
        this.user_id = user_id;
        this.user_name = user_name;
        this.artical_id = artical_id;
        this.a_content = a_content;
        this.a_date = a_date;
    }
    public int getImg() {
        return img;
    }
    public void setImg(int img) {
        this.img = img;
    }
    public String getUser_id(){return user_id;}
    public void setUser_id(){this.user_id = user_id;}
    public String getUser_name(){
        return user_name;
    }
    public String getArtical_id(){return artical_id;}
    public String getA_content(){return a_content;}
    public Date getA_date(){return a_date;}
}
