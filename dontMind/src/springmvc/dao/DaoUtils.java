package springmvc.dao;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import springmvc.entities.AcRelation;
import springmvc.entities.Artical;
import springmvc.entities.Comment;
import springmvc.entities.Favor;
import springmvc.entities.Friendship;
import springmvc.entities.Play;
import springmvc.entities.RecommendInfo;
import springmvc.entities.User;
import springmvc.entities.UserArtical;

public class DaoUtils {
    SessionFactory sFactory;
    Session session;
    Transaction tran;

    // 判断user_id是否已经存在(user_id唯一)
    public Boolean exist(String id) {
        begin();
        User user = null;
        try {
            user = (User) session.createQuery("select user from User user " + "where user_id = '" + id + "'")
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            end();
        }
        if (user != null)
            return true;
        else {
            return false;
        }
    }

    //MD5加密，将密码转换成32位的大写字母加数字
    public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
         try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 注册（user_id,password）
    public String regist(String id, String password) {
        //TODO==
        try {
           /* id=URLDecoder.decode(id,"utf-8");
            System.out.println(id);*/
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (exist(id))
            return "500";
        else {
            begin();
            try {
                User user = new User();
                user.setUser_id(id);
                user.setPassword(MD5(password));//将密码加密存入数据库
                user.setAge(0);
                session.save(user);
                return "200";
            } catch (Exception e) {
                System.out.println(e);
                return "400";
            } finally {
                end();
            }
        }
    }

    // 登录（user_id,password）
    public String login(String id, String password) {
        begin();
        User user = null;
        try {
            user = (User) session.createQuery("select user from User user " + "where user_id = " + "'" + id
                    + "'and password = '" + MD5(password) + "'").uniqueResult();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(user);
        } finally {
            end();
        }
        if (user != null)
            return "200";
        else {
            return "400";
        }
    }

    /* ======================个人中心模块============================= */
    // 显示个人数据
    public String my_data(String id) {
        begin();
        User user = null;
        try {
            user = (User) session.createQuery("select user from User user " + "where user_id = " + "'" + id + "'")
                    .uniqueResult();
            return user.toString();
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }

    // 增加个人数据
    public String add_data(String id, String name, String sex, int age, String address, String motto) {
        begin();
        try {
            session.createQuery("update User user set user.user_name='" + name + "',user.sex='" + sex + "',user.address"
                    + "='" + address + "',user.motto='" + motto + "',user.age=" + age + " where user.user_id= '" + id
                    + "'").executeUpdate();
            return "200";
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }

    // 修改密码
    public String change_password(String id, String password1, String password2) {
        String result = login(id, password1);
        if (result.equals("400"))
            return "500";/* 密码错误 */
        else {
            begin();
            try {
                session.createQuery(
                        "update User user set user.password = '" + MD5(password2) + "'where user.user_id = '" + id + "'")
                        .executeUpdate();
                return "200";
            } catch (Exception e) {
                System.out.println(e);
                return "400";
            } finally {
                end();
            }
        }
    }
    //重置密码
    public String reset_password(String id){
        begin();
        try{
            String pwd = "123456";
            session.createQuery(
                    "update User user set user.password = '" + MD5(pwd) + "'where user.user_id = '" + id + "'")
                    .executeUpdate();
            return "200";
        }catch(Exception e){
            return "400";
        }finally{
            end();
        }
    }


    /* ======================朋友管理模块============================= */
    // 查找朋友
    public String find_friend(String id) {
        begin();
        User user = null;
        try {
            user = (User) session.createQuery("select user from User user " + "where user_id = " + "'" + id + "'")
                    .uniqueResult();
            System.out.println(user);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(user);
        } finally {
            end();
        }
        if (user != null)
            return user.toString();
        else {
            return "400";
        }
    }

    // 判断是否已经是朋友
    @SuppressWarnings("unchecked")
    public boolean if_friend(String id1, String id2) {
        try {
            begin();
            String relation = null;
            List<String> flist = new ArrayList<String>();
            flist = null;
            // 判断是否在表中(如果user1_id)有多个朋友即返回多个user2_id）
            flist = session
                    .createQuery("select user2_id from Friendship " + " where user1_id = '" + id1 + "'").list();
            if (flist != null) {
                for (String f : flist) {
                    if (id2.equals(f)) {
                        // 在表中，看relation是否为1
                        relation = (String) session.createQuery("select relation from Friendship "
                                + " where user1_id = '" + id1 + "'and user2_id = '" + id2 + "'").uniqueResult();
                        if ("1".equals(relation)) {
                            // 是朋友关系
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            end();
        }
    }

    // 添加朋友
    @SuppressWarnings("unchecked")
    public String add_friend(String id1, String id2) {
        // 要添加的朋友还未注册
        if ("400".equals(find_friend(id2))) {
            return "300";
        } else {
            if (if_friend(id1, id2)) {
                // 是朋友
                return "500";
            } else {
                // 不是朋友
                try {
                    begin();
                    String relation = null;
                    List<String> flist = new ArrayList<String>();
                    flist = null;
                    // 判断是否在表中(如果user1_id)有多个朋友即返回多个user2_id）
                    flist = session
                            .createQuery("select user2_id from Friendship where user1_id = '" + id1 + "'").list();
                    if (flist.size() != 0) {
                        for (String f : flist) {
                            if (id2.equals(f)) {
                                // 在表中，看relation是否为1
                                relation = (String) session.createQuery("select relation from Friendship "
                                        + " where user1_id = '" + id1 + "'and user2_id = '" + id2 + "'").uniqueResult();
                                if ("1".equals(relation)) {
                                    // 是朋友关系
                                    return "500";
                                } else {
                                    // 在表中，不是朋友关系，修改relation和power为1
                                    session.createQuery("update Friendship set relation='1',power='1' "
                                            + " where user1_id = '" + id1 + "'and user2_id = '" + id2 + "'")
                                            .executeUpdate();
                                    return "200";
                                }
                            }
                        }
                        // 不在表中
                        Friendship friendship = new Friendship();
                        friendship.setUser1_id(id1);
                        friendship.setUser2_id(id2);
                        friendship.setRelation("1");
                        friendship.setPower("1");
                        session.save(friendship);
                        return "200";
                    }
                    // 不在表中
                    Friendship friendship = new Friendship();
                    friendship.setUser1_id(id1);
                    friendship.setUser2_id(id2);
                    friendship.setRelation("1");
                    friendship.setPower("1");
                    session.save(friendship);
                    return "200";
                } catch (Exception e) {
                    System.out.println(e);
                    return "400";
                } finally {
                    end();
                }
            }
        }
    }

    // 显示好友列表
    @SuppressWarnings({ "unchecked" })
    public String show_friend(String id) {
        begin();
        List<User> users = new ArrayList<User>();
        List<String> flist = new ArrayList<String>();
        flist = null;
        try {
            // 判断是否在表中(如果user1_id)有多个朋友即返回多个user2_id）
            flist = session
                    .createQuery("select user2_id from Friendship " + " where user1_id = '" + id + "' and relation='1' order by id desc")
                    .list();
            if (flist.size() !=0) {
                // 通过user2_id查找user表
                User u = new User();
                u = null;
                for (String user_id : flist) {
                    u = (User) session.createQuery("select user from User user where user_id = '" + user_id + "'")
                            .uniqueResult();
                    if (u != null) {
                        users.add(u);
                    } else {
                    }
                }
            } else {
                return "500";/* 当前没有朋友 */
            }
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
        if (users.size() != 0) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("[");
            int i;
            for (i = 0; i < users.size() - 1; i++) {
                sBuffer.append(users.get(i).toString() + ",");
            }
            if (i == users.size() - 1) {
                sBuffer.append(users.get(i).toString());
            }
            sBuffer.append("]");
            return sBuffer.toString();
        } else {
            return "400";
        }
    }

    // 删除好友
    public String delete_friend(String id1, String id2) {
        begin();
        // 将relation和power字段改为null
        try {
            session.createQuery("update Friendship set relation='null',power='null' "
                    + " where user1_id = '" + id1 + "'and user2_id = '" + id2 + "'").executeUpdate();
            return "200";
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }

    //修改好友权限
    public String change_power(String user1_id,String user2_id){
        //查表，有权限修改为无权限，否则反之
        begin();
        String power = null;
        try{
            power = (String) session.createQuery("select power from Friendship where user1_id ='"+user1_id+"' and user2_id ='"+user2_id+"'").uniqueResult();
            if("1".equals(power)){
                session.createQuery("update Friendship set power='null' "
                    + " where user1_id = '" + user1_id + "'and user2_id = '" + user2_id + "'").executeUpdate();
                return "200";
            }else{
                session.createQuery("update Friendship set power='1' "
                        + " where user1_id = '" + user1_id + "'and user2_id = '" + user2_id + "'").executeUpdate();
                return "200";
            }
        } catch(Exception e){
            e.printStackTrace();
            return "400";
        } finally{
            end();
        }
    }
    //显示朋友的权限
    public String show_power(String user1_id,String user2_id){
        begin();
        String power = null;
        try{
            power = (String) session.createQuery("select power from Friendship where user1_id ='"+user1_id+"' and user2_id ='"+user2_id+"'").uniqueResult();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
        if("1".equals(power)){
            return "200";
         }else{
             return "400";
         }
    }

    /* ======================动态模块============================= */
    // 发布动态(user_id,artical_id,content)
    public String add_artical(String id, String artical_id, String content) {
        begin();
        UserArtical ur = new UserArtical();
        ur.setUser_id(id);
        ur.setArtical_id(artical_id);
        Artical a = new Artical();
        a.setArtical_id(artical_id);
        a.setContent(content);
        a.setArtical_date(new Date());
        try {
            session.save(a);
            session.save(ur);
            return "200";
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }
    //修改动态
    public String update_artical(String artical_id, String content) {
        begin();
        Date date = new Date();
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
        String  d = formatter.format(date);
        try {
            session.createQuery("update Artical artical set content='"+content+"' ,artical_date='"+d+"'  where artical_id = '" + artical_id + "'").executeUpdate();
            return "200";
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }
    //通过动态查用户
    public String find_user(String artical_id){
        begin();
        try{
            String uid =(String) session.createQuery("select user_id from UserArtical user_artical where artical_id ='" + artical_id + "'").uniqueResult();
            if(uid != null){
                User user =(User)session.createQuery("select user from User user where user_id = '" + uid + "'").uniqueResult();
                return user.toString();
            }else{
                return "500";
            }

        }catch(Exception e){
            System.out.println(e);
            return "400";
        }finally{
            end();
        }
    }
    // 查看我的动态列表(user_id)
    @SuppressWarnings({ "unchecked" })
    public String show_artical(String id) {
        begin();
        List<Artical> list = new ArrayList<Artical>();
        List<String> slist = new ArrayList<String>();
        try {
            //查看关系表是否有动态
            slist = session
                    .createQuery("select artical_id from UserArtical user_artical where user_id ='" + id + "'order by id desc").list();
            if(slist.size() != 0){
                Artical artical = new Artical();
                //从动态表里查出动态
                for(String str :slist){
                    artical = (Artical) session.createQuery("select artical from Artical artical where artical_id ='"+str+"'").uniqueResult();
                    if(artical != null){
                        list.add(artical);
                    }else{}
                }
            }else{
                //没有动态
                return "500";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        } finally {
            end();
        }
        if (list.size() != 0) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("[ ");
            int i;
            for (i = 0; i < list.size() - 1; i++) {
                sBuffer.append(list.get(i).toString() + " , ");
            }
            if (i == list.size() - 1) {
                sBuffer.append(list.get(i).toString());
            }
            sBuffer.append(" ]");
            return sBuffer.toString();
        }else{
            return "500";
        }
    }

    // 删除动态(user_id,artical_id)
    public String delete_artical(String id, String artical_id) {
        begin();
        try {
            session.createQuery(
                    "delete from UserArtical user_artical where user_id='" + id + "' and artical_id='" + artical_id + "'")
                    .executeUpdate();
            return "200";
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        } finally {
            end();
        }
    }
 //查找一条动态
    public String find_artical(String artical_id){
        Artical artical= new Artical();
        artical = null;
        begin();
        try{
            artical = (Artical)session.createQuery("select artical from Artical artical where artical_id='"+artical_id+"'").uniqueResult();
        }catch(Exception e){
            e.printStackTrace();

        }finally{
            end();
        }
        if (artical != null)
            return artical.toString();
        else {
            return "400";
        }

    }
    //查看我朋友的动态
    @SuppressWarnings({ "unchecked" })
    public String show_friend_artical(String user_id){
        begin();
        List<Artical> list = new ArrayList<Artical>();
        List<String> slist = new ArrayList<String>();
        List<String> flist = new ArrayList<String>();

        flist = null;
        try {
            //通过user_id查所有的朋友id
            flist = session
                    .createQuery("select user2_id from Friendship  where user1_id = '" + user_id + "' and relation='1'")
                    .list();
            System.out.println("flist====="+flist.size());
            if (flist.size() != 0) {
                for(String id :flist){
                    //看我有没有看他动态的权限
                    String power=(String)session.createQuery("select power from Friendship where user1_id='"+id+"' and user2_id='"+user_id+"' ").uniqueResult();
                    if("1".equals(power)){
                        //有权限，去查动态
                      //查看关系表是否有动态
                        slist = session
                                .createQuery("select artical_id from UserArtical user_artical where user_id ='" + id + "'").list();
                        if(slist.size() != 0){
                            Artical artical = new Artical();
                            //从动态表里查出动态
                            for(String str :slist){
                                artical = (Artical) session.createQuery("select artical from Artical artical where artical_id ='"+str+"'").uniqueResult();
                                if(artical != null){
                                    list.add(artical);
                                }else{}
                            }
                        }
                    }
                }
            }else{
                return "400";
            }
        } catch (Exception e) {
            return "400";
        } finally {
            end();
        }
        if (list.size() != 0) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("[ ");
            int i;
            for (i = 0; i < list.size() - 1; i++) {
                sBuffer.append(list.get(i).toString() + " , ");
            }
            if (i == list.size() - 1) {
                sBuffer.append(list.get(i).toString());
            }
            sBuffer.append(" ]");
            return sBuffer.toString();
        }else{
            return "500";
        }
    }

    /* ======================评论模块============================= */
    // 发表评论
    public String add_comment(String id, String artical_id, String comment_id, String content) {
        begin();

        // 将评论内容存入评论表
        Comment comment = new Comment();
        comment.setC_content(content);
        comment.setComment_id(comment_id);
        comment.setUser_id(id);
        comment.setComment_date(new Date());
        // 将评论id和动态id存入评论动态关系表进行关联
        AcRelation relation = new AcRelation();
        relation.setA_id(artical_id);
        relation.setC_id(comment_id);
        try {
            session.save(relation);
            session.save(comment);
            return "200";
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }

    // 删除评论
    public String delete_comment(String id, String artical_id, String comment_id) {
        begin();
        try {
            // 先在评论关系表中删除，再在评论表中删除
            session.createQuery(
                    "delete from AcRelation ac_relation where a_id='" + artical_id + "' and c_id='" + comment_id + "'")
                    .executeUpdate();
            session.createQuery(
                    "delete from Comment comment where user_id='" + id + "' and comment_id='" + comment_id + "'")
                    .executeUpdate();
            return "200";
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }

    // 显示评论
    @SuppressWarnings({ "unchecked" })
    public String show_comment(String artical_id) {
        begin();
        List<String> cids = new ArrayList<String>();
        List<Comment> comments = new ArrayList<Comment>();
        Comment comment = new Comment();
        comment = null;
        try {
            // 通过artical_id查评论关系表，得到c_id
            cids = session
                    .createQuery("select c_id from  AcRelation ac_relation where a_id ='" + artical_id + "' order by id desc").list();
            if (cids.size() != 0) {
                // 通过c_id查评论表，显示关于该动态的所有评论
                for (String cid : cids) {
                    comment = (Comment) session
                            .createQuery("select comment from  Comment comment where comment_id ='" + cid + "'")
                            .uniqueResult();
                    comments.add(comment);
                }
            } else {
                /* 没有评论 */
                return "500";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
        if (comments.size() != 0) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("[ ");
            int i;
            for (i = 0; i < comments.size() - 1; i++) {
                sBuffer.append(comments.get(i).toString() + " , ");
            }
            if (i == comments.size() - 1) {
                sBuffer.append(comments.get(i).toString());
            }
            sBuffer.append(" ]");
            return sBuffer.toString();
        } else {
            return "500";/* 没有评论 */
        }
    }

    // 点赞
    public String favor(String user_id, String artical_id) {
        begin();
        Favor favor = new Favor();
        favor.setArtical_id(artical_id);
        favor.setUser_id(user_id);
        try {
            session.save(favor);
            return "200";
        } catch (Exception e) {
            System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }
    //显示赞的数量
    public String count_favor(String artical_id){
        begin();
        try{
            long count = (long) session.createQuery("select count(*) from Favor where artical_id ='"+artical_id+"'").uniqueResult();
            String number =count+"";
            return number;
        }catch (Exception e) {
           System.out.println(e);
            return "400";
        } finally {
            end();
        }
    }
    /*======================不纠结模块=============================*/
    //保存一次纠结经历
    public String save_play(String user_id,String play_title){
        begin();
        Play play = new Play();
        play.setUser_id(user_id);
        play.setPlay_title(play_title);
        play.setDate(new Date());
        try{
            session.save(play);
            return "200";
        }catch(Exception e){
            System.out.println(e);
            return "400";
        }finally{
            end();
        }
    }
    //查询所有的纠结经历
    @SuppressWarnings("unchecked")
    public String show_play(String user_id){
        begin();
        List<Play> plays = new ArrayList<Play>();
        try{
           plays = session.createQuery("select play from Play play where user_id ='"+user_id+"' order by id ").list();
        }catch(Exception e){
            System.out.println(e);
            return "400";
        }finally{
            end();
        }
        if(plays.size() != 0){
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("[ ");
            int i;
            for (i = 0; i < plays.size() - 1; i++) {
                sBuffer.append(plays.get(i).toString() + " , ");
            }
            if (i == plays.size() - 1) {
                sBuffer.append(plays.get(i).toString());
            }
            sBuffer.append(" ]");
            return sBuffer.toString();
            } else {
                return "500";/* 没有纠结的事 */
            }
    }
   //查某个用户对某件事纠结的次数
   public String count_play(String user_id,String play_title){
       begin();
       long num ;
       num = (long)session.createQuery("select count(*) from Play play where user_id = '"+user_id+"' and play_title like '%"+play_title+"%' ").uniqueResult();
       end();
       return num+"";
   }
   //显示推荐
   @SuppressWarnings("unchecked")
   public String show_recommend() {
       begin();
       List<RecommendInfo> recommendList=new ArrayList<RecommendInfo>();
       try{
           recommendList=session.createQuery(" from RecommendInfo").list();
       }catch(Exception e){
           e.printStackTrace();
           return "400";
       }finally{
           end();
       }
       if(recommendList.size() != 0){
           StringBuffer sBuffer = new StringBuffer();
           sBuffer.append("[ ");
           int i;
           for (i = 0; i < recommendList.size() - 1; i++) {
               sBuffer.append(recommendList.get(i).toString() + " , ");
           }
           if (i == recommendList.size() - 1) {
               sBuffer.append(recommendList.get(i).toString());
           }
           sBuffer.append(" ]");
           return sBuffer.toString();
           } else {
               return "500";/* 没有推荐 */
           }
   }

    // 建立数据连接，开启事务
    public void begin() {
        try {
            Configuration cfg = new Configuration().configure();
            ServiceRegistry serviceRegistry = cfg.getStandardServiceRegistryBuilder().build();
            sFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            session = sFactory.openSession();
            tran = session.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 关闭数据库连接
    public void end() {
        try {
            tran.commit();
        } catch (Exception e) {
            tran.rollback();
        } finally {
            session.close();
            sFactory.close();
        }
    }



}
