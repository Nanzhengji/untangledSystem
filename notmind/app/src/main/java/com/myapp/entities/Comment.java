package com.myapp.entities;

import java.util.Date;

public class Comment {
    public int id;
    public String user_id;
    public String c_content;// 评论内容
    public String comment_id;
    public Date comment_date;
public Comment(String user_id,String comment_id,String c_content,Date comment_date){
    this.user_id=user_id;
    this.comment_id = comment_id;
    this.c_content=c_content;
    this.comment_date=comment_date;
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getC_content() {
        return c_content;
    }

    public String getComment_id() {
        return comment_id;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }


}
