package com.entities;

import java.util.Date;

public class Play implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * id主键自增
     * play_title是纠结事件的主题（可以重复）
     * date是存入时间
     * */
    private int id;
    private String user_id;
    private String play_title;
    private Date date;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getPlay_title() {
        return play_title;
    }
    public void setPlay_title(String play_title) {
        this.play_title = play_title;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "{\"id\"=\"" + id + "\",\"user_id\"=\"" + user_id + "\",\"play_title\"=\"" + play_title + "\",\"date\"=\"" + date + "\"}";
    }



}
