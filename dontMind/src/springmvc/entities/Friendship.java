package springmvc.entities;

public class Friendship {
    public int id;
    public String user1_id;
    public String user2_id;
    public String relation;
    public String power;

    public String getUser1_id() {
        return user1_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser2_id() {
        return user2_id;
    }

    public String getRelation() {
        return relation;
    }

    public void setUser1_id(String user1_id) {
        this.user1_id = user1_id;
    }

    public void setUser2_id(String user2_id) {
        this.user2_id = user2_id;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "friend [ user2_id=" + user2_id + "]";
    }

}
