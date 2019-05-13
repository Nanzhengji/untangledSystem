package com.myapp.entities;

public class Friend {
    public String fid;
    public String fname;
    public String fsex;
    public int fimgid;
public Friend(String fid,String fname,int fimgid){
    this.fid = fid;
    this.fname = fname;
    this.fimgid=fimgid;
}
    public void setFid(String fid){this.fid = fid;}
    public String getFid(){return  this.fid;}
    public void setFname(String fname){
        this.fname = fname;
    }
    public String getFname(){
        return this.fname;
    }
    public void setFsex(String fsex){
        this.fsex = fsex;
    }
    public String getFsex(){
        return this.fsex;
    }
    public int getFimgid() {
        return fimgid;
    }

    public void setFimgid(int fimgid) {
        this.fimgid = fimgid;
    }

}
