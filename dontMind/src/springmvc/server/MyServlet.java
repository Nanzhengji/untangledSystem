package springmvc.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springmvc.dao.DaoUtils;

@Controller
public class MyServlet {

    public static final String myview = "myView";
    DaoUtils daoUtils;




    /* ======================个人中心模块============================= */
    // 注册
    @RequestMapping(value = "/regist")
    public String regist(@RequestParam(value = "user_id") String id, @RequestParam(value = "password") String password,
             HttpServletRequest request) {
        System.out.println("regist");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.regist(id, password);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 登录
    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "user_id") String id, @RequestParam(value = "password") String password,
            HttpServletRequest request) {
        System.out.println("login");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.login(id, password);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 显示我的个人信息
    @RequestMapping(value = "/my_data")
    public String my_data(@RequestParam(value = "user_id") String id, HttpServletRequest request) {
        System.out.println("my_data");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.my_data(id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 添加个人信息
    @RequestMapping(value = "/add_data")
    public String add_data(@RequestParam(value = "user_id") String id, @RequestParam(value = "user_name") String name,
            @RequestParam(value = "sex") String sex, @RequestParam(value = "age") int age,
            @RequestParam(value = "address") String address, @RequestParam(value = "motto") String motto,
            HttpServletRequest request) {
        System.out.println("save_data");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.add_data(id, name, sex, age, address, motto);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 修改密码
    @RequestMapping(value = "/change_password")
    public String change_password(@RequestParam(value = "user_id") String id,
            @RequestParam(value = "password1") String password1, @RequestParam(value = "password2") String password2,
            HttpServletRequest request) {
        System.out.println("change_password");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.change_password(id, password1, password2);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //重置密码
    @RequestMapping(value = "/reset_password")
    public String reset_password(@RequestParam(value = "user_id") String id,
            HttpServletRequest request) {
        System.out.println("reset_password");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.reset_password(id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }


    /* ======================朋友管理模块============================= */
    // 查找朋友
    @RequestMapping(value = "/find_friend")
    public String find_friend(@RequestParam(value = "user_id") String id, HttpServletRequest request) {
        System.out.println("find_friend");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.find_friend(id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 添加朋友
    @RequestMapping(value = "/add_friend")
    public String add_friend(@RequestParam(value = "user1_id") String id1, @RequestParam(value = "user2_id") String id2,
            HttpServletRequest request) {
        System.out.println("add_friend");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.add_friend(id1, id2);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 删除朋友
    @RequestMapping(value = "/delete_friend")
    public String delete_friend(@RequestParam(value = "user1_id") String id1,
            @RequestParam(value = "user2_id") String id2, HttpServletRequest request) {
        System.out.println("delete_friend");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.delete_friend(id1, id2);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 显示我的朋友列表
    @RequestMapping(value = "/show_friend")
    public String show_friends(@RequestParam(value = "user_id") String id, HttpServletRequest request) {
        System.out.println("show_friend");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.show_friend(id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    //修改好友权限
    @RequestMapping(value = "/change_power")
    public String change_power(@RequestParam(value = "user1_id") String id1,@RequestParam(value = "user2_id") String id2, HttpServletRequest request) {
        System.out.println("change_power");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.change_power(id1,id2);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //显示好友权限
    @RequestMapping(value = "/show_power")
    public String show_power(@RequestParam(value = "user1_id") String id1,@RequestParam(value = "user2_id") String id2, HttpServletRequest request) {
        System.out.println("show_power");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.show_power(id1,id2);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    /* ======================动态模块============================= */
    // 发布动态
    @RequestMapping(value = "/add_artical")
    public String add_artical(@RequestParam(value = "user_id") String id,
            @RequestParam(value = "artical_id") String artical_id, @RequestParam(value = "content") String content,
            HttpServletRequest request) {
        System.out.println("add_atical");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.add_artical(id, artical_id, content);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //修改动态
    @RequestMapping(value = "/update_artical")
    public String update_artical(@RequestParam(value = "artical_id") String artical_id, @RequestParam(value = "content") String content,
            HttpServletRequest request) {
        System.out.println("update_artical");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.update_artical(artical_id, content);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    //显示一条确定的动态
    @RequestMapping(value = "/find_artical")
    public String find_artical(@RequestParam(value = "artical_id") String artical_id, HttpServletRequest request) {
        System.out.println("find_artical");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.find_artical(artical_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //通过动态id查找用户
    @RequestMapping(value = "/find_user")
    public String find_user(@RequestParam(value = "artical_id") String artical_id, HttpServletRequest request) {
        System.out.println("find_user");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.find_user(artical_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }


    // 显示我的动态列表
    @RequestMapping(value = "/show_artical")
    public String show_artical(@RequestParam(value = "user_id") String id, HttpServletRequest request) {
        System.out.println("show_artical");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.show_artical(id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //显示我朋友的动态列表
    @RequestMapping(value = "/show_friend_artical")
    public String show_friend_artical(@RequestParam(value = "user_id") String id, HttpServletRequest request) {
        System.out.println("show_friend_artical");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.show_friend_artical(id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    // 删除动态
    @RequestMapping(value = "/delete_artical")
    public String delete_artical(@RequestParam(value = "user_id") String id,
            @RequestParam(value = "artical_id") String artical_id, HttpServletRequest request) {
        System.out.println("delete_artical");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.delete_artical(id, artical_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 发表评论
    @RequestMapping(value = "/add_comment")
    public String add_comment(@RequestParam(value = "user_id") String id,
            @RequestParam(value = "artical_id") String artical_id,
            @RequestParam(value = "comment_id") String comment_id, @RequestParam(value = "c_content") String content,
            HttpServletRequest request) {
        System.out.println("add_comment");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.add_comment(id, artical_id, comment_id, content);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }


    // 删除评论
    @RequestMapping(value = "/delete_comment")
    public String delete_comment(@RequestParam(value = "user_id") String id,
            @RequestParam(value = "artical_id") String artical_id,
            @RequestParam(value = "comment_id") String comment_id, HttpServletRequest request) {
        System.out.println("delete_comment");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.delete_comment(id, artical_id, comment_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    // 显示评论
    @RequestMapping(value = "/show_comment")
    public String show_comment(@RequestParam(value = "artical_id") String artical_id, HttpServletRequest request) {
        System.out.println("show_comment");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.show_comment(artical_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    // 点赞
    @RequestMapping(value = "/favor")
    public String favor(@RequestParam(value = "user_id") String user_id,@RequestParam(value = "artical_id") String artical_id, HttpServletRequest request) {
        System.out.println("favor");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.favor(user_id,artical_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //显示赞的数量
    @RequestMapping(value = "/count_favor")
    public String count_favor(@RequestParam(value = "artical_id") String artical_id, HttpServletRequest request) {
        System.out.println("count_favor");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.count_favor(artical_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //添加纠结经历
    @RequestMapping(value = "/save_play")
    public String save_play(@RequestParam(value = "user_id") String user_id,@RequestParam(value = "play_title") String play_title, HttpServletRequest request) {
        System.out.println("save_play");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.save_play(user_id,play_title);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }
    //显示纠结经历
    @RequestMapping(value = "/show_play")
    public String show_play(@RequestParam(value = "user_id") String user_id , HttpServletRequest request) {
        System.out.println("show_play");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.show_play(user_id);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

    //显示某种纠结经历的次数
    @RequestMapping(value = "/count_play")
    public String count_play(@RequestParam(value = "user_id") String user_id ,@RequestParam(value = "play_title") String play_title , HttpServletRequest request) {
        System.out.println("count_play");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.count_play(user_id,play_title);
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }

  //显示推荐
    @RequestMapping(value = "/show_recommend")
    public String show_recommend( HttpServletRequest request) {
        System.out.println("show_recommend");
        String message = "";
        try {
            daoUtils = new DaoUtils();
            message = daoUtils.show_recommend();
        } catch (Exception e) {
            message = e.toString();
        }
        request.setAttribute("message", message);
        return myview;
    }


}
