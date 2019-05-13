package com.entities;

import java.util.Date;

public class Story  implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String classify;
    private String story_title;
    private String story_content;
    private Date create_time;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getClassify() {
        return classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public String getStory_title() {
        return story_title;
    }
    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }
    public String getStory_content() {
        return story_content;
    }
    public void setStory_content(String story_content) {
        this.story_content = story_content;
    }
    public Date getCreate_time() {
        return create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }


}
