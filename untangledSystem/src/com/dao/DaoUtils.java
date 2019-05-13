package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.entities.Play;
import com.entities.RecommendInfo;
import com.entities.Story;

public class DaoUtils {
    SessionFactory sFactory;
    Session session;
    Transaction tran;

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
    //查出注册用户数量
    public long countUser() {
        begin();
        Long num =0L;
        try{
            num=(long)session.createQuery(" select count(*) from User").uniqueResult();
        }catch(Exception e){
            e.printStackTrace();

        }finally{
            end();
        }
        return num;
    }

    //查出所有的纠结记录
    @SuppressWarnings("unchecked")
    public List<Play> findPlays() {
        begin();
        List<Play> plays=new ArrayList<Play>();
        try{
            plays=session.createQuery("from Play").list();
        }catch(Exception e){
            e.printStackTrace();

        }finally{
            end();
        }
        return plays;

    }
    //查出所有推荐的内容
    @SuppressWarnings("unchecked")
    public List<RecommendInfo> findRecommends() {
        begin();
        List<RecommendInfo> recommends=new ArrayList<RecommendInfo>();
        try{
            recommends=session.createQuery(" from RecommendInfo").list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
        return recommends;
    }
    //添加推荐内容
    public void addRecommend(String title, String content,String classify) {
        begin();
        RecommendInfo recommend=new RecommendInfo();
        recommend.setRec_title(title);
        recommend.setClassify(classify);
        recommend.setRec_content(content);
        try{
            session.save(recommend);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
    }
    //添加文章
    public void addStory(String title, String content, String classify) {
        begin();
        Story story=new Story();
        story.setClassify(classify);
        story.setStory_title(title);
        story.setStory_content(content);
        story.setCreate_time(new Date());
        try{
            session.save(story);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }

    }
    //查出所有的文章
    @SuppressWarnings("unchecked")
    public List<Story> findStorys() {
        begin();
        List<Story> storys=new ArrayList<Story>();
        try{
            storys=session.createQuery(" from Story").list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
        return storys;
    }
    //通过标题找纠结记录
    @SuppressWarnings("unchecked")
    public List<Play> findPlayByTitle(String title) {
        begin();
        List<Play> plays=new ArrayList<Play>();
        try{
            plays=session.createQuery("select play from Play play where play_title like '%"+title+"%'").list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
        return plays;
    }
    //通过分类找文章
    @SuppressWarnings("unchecked")
    public List<Story> findStoryByClassify(String classily) {
        begin();
        List<Story> storys=new ArrayList<Story>();
        try{
            storys=session.createQuery("select story from Story story where classify like '%"+classily+"%'").list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
        return storys;
    }
    //删除一篇文章
    public void deleteStoty(int id) {
        begin();
        try {
            session.createQuery("delete from Story story where id='" + id + "'").executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            end();
        }
    }
    //通过id找文章
    public Story findStoryByTitle(int id) {
        begin();
        Story story=new Story();
        try{
            story=(Story)session.createQuery("select story from Story story where id='"+id+"'").uniqueResult();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
        return story;
    }
    //通过分类查找推荐
    @SuppressWarnings("unchecked")
    public List<RecommendInfo> findRecommendByClassify(String classily) {
        begin();
        List<RecommendInfo> recommends=new ArrayList<RecommendInfo>();
        try{
            recommends=session.createQuery("select recommendinfo from RecommendInfo recommendinfo where classify like '%"+classily+"%'").list();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            end();
        }
        return recommends;
    }
    //删除推荐
    public void deleteRecByTitle(int id) {
        begin();
        try {
            session.createQuery("delete from RecommendInfo recommendinfo where id='" + id + "'").executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            end();
        }
    }

}
