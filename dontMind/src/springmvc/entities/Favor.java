package springmvc.entities;

public class Favor {
    public int id;
    public String user_id;
    public String artical_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getArtical_id() {
        return artical_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setArtical_id(String artical_id) {
        this.artical_id = artical_id;
    }

    @Override
    public String toString() {
        return "Favor [user_id=" + user_id + ", artical_id=" + artical_id + "]";
    }

}
