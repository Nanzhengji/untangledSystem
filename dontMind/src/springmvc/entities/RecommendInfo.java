package springmvc.entities;

public class RecommendInfo {
    private int id;
    private String classify;
    private String rec_title;
    private String rec_content;

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

    public String getRec_title() {
        return rec_title;
    }

    public void setRec_title(String rec_title) {
        this.rec_title = rec_title;
    }

    public String getRec_content() {
        return rec_content;
    }

    public void setRec_content(String rec_content) {
        this.rec_content = rec_content;
    }

    @Override
    public String toString() {
        return "{\"id\"=\""+id+"\",\"rec_title\"=\""+rec_title+"\",\"rec_content\"=\""+rec_content+"\"}";
    }

}
