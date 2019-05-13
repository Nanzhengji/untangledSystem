package springmvc.entities;

public class AcRelation {
    // 评论关系表
    public int id;
    public String a_id;
    public String c_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA_id() {
        return a_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    @Override
    public String toString() {
        return "AcRelation [a_id=" + a_id + ", c_id=" + c_id + "]";
    }

}
