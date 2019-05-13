package com.myapp.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DaoUtils {
        URL url = null;
        public static  final String fail = "400";
/*个人中心模块*/
    public String regist(String user_id,String password){
        String str = "http://101.200.52.170/dontMind/regist?user_id="+user_id+"&password="+password;
        return deal_url(str);
    }

    public String login(String user_id,String password){
        String str = "http://101.200.52.170/dontMind/login?user_id="+user_id+"&password="+password;
         return deal_url(str);
    }

    public String my_data(String user_id){
        String str = "http://101.200.52.170/dontMind/my_data?user_id="+user_id;
        return deal_url(str);
    }

    public String add_data(String user_id,String name,String sex,int age,String address,String motto){
      String str = "http://101.200.52.170/dontMind/add_data?user_id="+user_id+"&user_name="+name+"&sex="+sex+"&age="+age+"&address="+address+"&motto="+motto;
      return  deal_url(str);
    }

    public String change_password(String user_id,String password1,String password2){
        String str = "http://101.200.52.170/dontMind/change_password?user_id="+user_id+"&password1="+password1+"&password2="+password2;
        return deal_url(str);
    }
    public String reset_password(String user_id){
        String str = "http://101.200.52.170/dontMind/reset_password?user_id="+user_id;
        return deal_url(str);
    }
/*朋友管理模块*/
    public String find_friend(String user_id){
        String str = "http://101.200.52.170/dontMind/find_friend?user_id="+user_id;
        return  deal_url(str);
    }

    public String add_friend(String user1_id,String user2_id){
        String str = "http://101.200.52.170/dontMind/add_friend?user1_id="+user1_id+"&user2_id="+user2_id;
        return  deal_url(str);
    }

    public String delete_friend(String user1_id,String user2_id){
        String str = "http://101.200.52.170/dontMind/delete_friend?user1_id="+user1_id+"&user2_id="+user2_id;
        return  deal_url(str);
    }

    public String show_friend(String user_id){
        String str = "http://101.200.52.170/dontMind/show_friend?user_id="+user_id;
        return  deal_url(str);
    }

    public String change_power(String user1_id,String user2_id){
        String str = "http://101.200.52.170/dontMind/change_power?user1_id="+user1_id+"&user2_id="+user2_id;
        return deal_url(str);
    }
    public String show_power(String user1_id,String user2_id){
        String str = "http://101.200.52.170/dontMind/show_power?user1_id="+user1_id+"&user2_id="+user2_id;
        return deal_url(str);
    }
/*空间管理模块*/
    public String add_artical(String user_id, String artical_id, String content){
        String str = "http://101.200.52.170/dontMind/add_artical?user_id="+user_id+"&artical_id="+artical_id+"&content="+content;
        return  deal_url(str);
    }
    public String update_artical(String artical_id, String content){
        String str = "http://101.200.52.170/dontMind/update_artical?artical_id="+artical_id+"&content="+content;
        return  deal_url(str);
    }
    public String delete_artical(String user_id,String artical_id){
        String str = "http://101.200.52.170/dontMind/delete_artical?user_id="+user_id+"&artical_id="+artical_id;
        return  deal_url(str);
    }
    public String find_artical(String artical_id){
        String str ="http://101.200.52.170/dontMind/find_artical?artical_id="+artical_id;
        return deal_url(str);
    }
    public String find_user(String artical_id){
        String str ="http://101.200.52.170/dontMind/find_user?artical_id="+artical_id;
        return deal_url(str);
    }

    public String show_artical(String user_id){
        String str = "http://101.200.52.170/dontMind/show_artical?user_id="+user_id;
        return  deal_url(str);
    }
    public String show_friend_artical(String user_id){
        String str = "http://101.200.52.170/dontMind/show_friend_artical?user_id="+user_id;
        return  deal_url(str);
    }
    public String add_comment(String user_id, String artical_id, String comment_id,String c_content){
        String str = "http://101.200.52.170/dontMind/add_comment?user_id="+user_id+"&artical_id="+artical_id+"&comment_id="+comment_id+"&c_content="+c_content;
        return  deal_url(str);
    }

    public String delete_comment(String user_id,String artical_id,String comment_id){
        String str = "http://101.200.52.170/dontMind/delete_comment?user_id="+user_id+"&artical_id="+artical_id+"&comment_id="+comment_id;
        return  deal_url(str);
    }

    public String show_comment(String artical_id){
        String str = "http://101.200.52.170/dontMind/show_comment?artical_id="+artical_id;
        return  deal_url(str);
    }
    public String favor(String user_id,String artical_id){
        String str = "http://101.200.52.170/dontMind/favor?user_id="+user_id+"&artical_id="+artical_id;
        return deal_url(str);
    }
    public String count_favor(String artical_id){
        String str = "http://101.200.52.170/dontMind/count_favor?artical_id="+artical_id;
        return deal_url(str);
    }

    public String save_play(String user_id,String play_title){
        String str = "http://101.200.52.170/dontMind/save_play?user_id="+user_id+"&play_title="+play_title;
        return deal_url(str);
    }
    public String show_play(String user_id){
        String str = "http://101.200.52.170/dontMind/show_play?user_id="+user_id;
        return deal_url(str);
    }
    public String count_play(String user_id,String play_title){
        String str = "http://101.200.52.170/dontMind/count_play?user_id="+user_id+"&play_title="+play_title;
        return deal_url(str);
    }
    public String show_recommend(){
        String str = "http://101.200.52.170/dontMind/show_recommend";
        return deal_url(str);
    }
    //处理返回的数据
    public String deal_url(String str){
        try {
            url = new URL(str);
            URLConnection uc = url.openConnection();
            uc.connect();
            uc.setConnectTimeout(10000);
            BufferedReader br = new BufferedReader(new InputStreamReader( url.openStream()));
            return br.readLine();

        }catch (Exception e){
            //return fail;
            return  fail+e.toString();
        }
    }
}
