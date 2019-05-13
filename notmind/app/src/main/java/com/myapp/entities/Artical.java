package com.myapp.entities;

import java.util.Date;

/**
 * 动态表 id artical_id动态id
 * user_id发布动态的用户id（user_id和artical-id是一对多的关系，一个用户可以发布多条动态） content动态内容
 * artical_date发布时间
 * 
 */
public class Artical {
    public int id;
    public String artical_id;
    public String user_id;
    public String content;
    public Date artical_date;
    public int img;
    public Artical(String id,String content,Date data){
        this.artical_id= id;
        this.content = content;
        this.artical_date = data;
    }
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtical_id() {
        return artical_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getContent() {
        return content;
    }

    public Date getArtical_date() {
        return artical_date;
    }

    public void setArtical_id(String artical_id) {
        this.artical_id = artical_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setArtical_date(Date artical_date) {
        this.artical_date = artical_date;
    }

    @Override
    public String toString() {
        return "Artical [artical_id=" + artical_id + ", content=" + content + ", artical_date=" + artical_date + "]";
    }
}
