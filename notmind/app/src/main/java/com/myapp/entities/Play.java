package com.myapp.entities;

import java.util.Date;

public class Play {
    /**
     * id主键自增
     * play_title是纠结事件的主题（可以重复）
     * paly_result是最终确定的结果
     * date是存入时间
     * */
    public int id;
    public String user_id;
    public String play_title;
    public Date date;

    public Play(String user_id,String play_title,Date date){
        this.user_id = user_id;
                this.play_title = play_title;
        this.date = date;
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

}