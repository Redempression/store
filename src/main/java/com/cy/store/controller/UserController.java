package com.cy.store.controller;

import com.cy.store.controller.fileEx.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@RestController //其作用等同于@Controller+@ResponseBody
//@Controller
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /*@RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user) {
        //创建响应结果对象即JsonResult对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            //调用userService的reg方法时可能出现异常,所以需要捕获异常
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }*/

    /**
     * SpringBoot约定大于配置的开发思想来完成的，省略了大量的配置甚至注解的编写
     *
     * 两种接收数据方式：
     *
     * 1、请求处理方法的参数列表设置为pojo类型来接收前端数据，如下面reg方法
     *   SpringBoot会将前端url地址中的参数名和pojo类的属性名进行比较，
     *   如果这两个名称相同，则将值注入到pojo类中对应的属性上。
     *
     * 2、请求处理方法的参数列表设置为非pojo类型来接收前端数据，如下面login方法
     *   SpringBoot会直接将请求的参数名和方法的参数名进行比较，
     *   如果这两个名称相同，则自动完成值的依赖注入。
     *
     * */

    /** 优化设计*/
    /**
     * 注册
     */
    @PostMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给到前端
    /**
     * 由于前端通过ajax 的 data: $("#form-reg").serialize(),获取的数据
     * 如username=Tom&password=123会传入进User对象对应的username和password
     * 因为容器里有User的对象，所以从前端传过来的数据是直接找同名对象进行传参
     * */
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }


    /**
     * 登录
     */
    @PostMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session) {
        User data = userService.login(username, password);

        //向session对象中完成数据的绑定(这个session是全局的,项目的任何位置都可以访问)
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //测试能否正常获取session中存储的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<>(OK, data);
    }


    /**
     * 修改密码
     */
    @PostMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        userService.changePassword(uid, username, oldPassword, newPassword);

        return new JsonResult<>(OK);
    }

    /**
     * 获取用户基本信息
     */
    @GetMapping("get_by_uid")
    public JsonResult<User> getInfoByUid(HttpSession session) {
        User data = userService.getInfoByUid(getUidFromSession(session));

        return new JsonResult<User>(OK, data);
    }


    /**
     * 更新用户基本信息
     * */
    @PostMapping("change_info")
    public JsonResult<Void> changeInfo(HttpSession session,
                                       User user){
        //user对象中有四部分的数据：username、phone、email、gender
        //uid数据需要再次封装到user对象中
        userService.changeInfo(getUidFromSession(session),user);

        return new JsonResult<>(OK);
    }


    /**
     * 头像上传功能
     *
     * MultipartFile接口是SpringMVC提供的一个接口，这个接口为我们包装了
     * 获取文件类型的数据(任何类型的file都可以接收)，SpringBoot整合了
     * SpringMVC,只需要在处理请求的方法参数列表上声明一个参数类型为 MultipartFile
     * 的参数，然后SpringBoot自动将传递给服务的文件数据赋值给这个参数
     *
     * @RequestParam 表示请求中的参数，将请求中的参数注入到请求处理方法的某个参数上，
     * 如果名称不一致则可以使用@RequestParam 注解进行标记和映射
     *
     * */
    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){

        System.out.println(file);
        /**
         * 1.参数名为什么必须用file:在upload.html页面的147行<input type=
         * "file" name="file">中的name="file",所以必须有一个方法的参数名
         * 为file用于接收前端传递的该文件.如果想要参数名和前端的name不一
         * 样:@RequestParam("file")MultipartFile ffff:把表单中name=
         * "file"的控件值传递到变量ffff上
         * 2.参数类型为什么必须是MultipartFile:这是springmvc中封装的一个
         * 包装接口,如果类型是MultipartFile并且参数名和前端上传文件的name
         * 相同,则会自动把整体的数据包传递给file
         */


        //判断文件是否为null
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        //file.getSize()获取文件字节大小
        if (file.getSize()>AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        //如果集合包含某个元素则返回值为true
        //集合方法contains()表示集合中是否包含()里的对象
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }

        //上传的文件路径:.../upload/文件名.png
        /**
         * session.getServletContext()获取当前Web应用程序的上下文
         * 对象(每次启动tomcat都会创建一个新的上下文对象)
         * getRealPath("/upload")的/代表当前web应用程序的根目录,通过该相
         * 对路径获取绝对路径,返回一个路径字符串,如果不能进行映射返回null,单
         * 斜杠可要可不要
         */
        String parent =
                session.getServletContext().getRealPath("/upload");
        System.out.println(parent);//调试用

        //File对象指向这个路径,通过判断File是否存在得到该路径是否存在
        File dir = new File(parent);
        if (!dir.exists()) {//检测目录是否存在
            dir.mkdirs();//创建当前目录
        }

        //file.getOriginalFilename() 获取这个文件名称(文件名+后缀,如avatar01.png,不包含父目录结构)
        // 用UUID工具生成一个新的字符串作为文件名(好处:避免了因文件名重复发生的覆盖)
        String originalFilename = file.getOriginalFilename();
        System.out.println("OriginalFilename="+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        //截取文件的后缀，形如 .png
        String suffix = originalFilename.substring(index);

        //filename形如SAFS1-56JHIOHI-HIUGHUI-5565TYRF.png
        String filename =
                UUID.randomUUID().toString().toUpperCase()+suffix;

        //在dir目录下创建filename文件(此时是空文件)
        File dest = new File(dir, filename);

        //java可以把一个文件的数据直接写到同类型的文件中,这里将参数file中的数据写入到空文件dest中
        try {
            file.transferTo(dest);//transferTo是一个封装的方法,用来将file文件中的数据写入到dest文件

            /**
             * 先捕获FileStateException再捕获IOException是
             * 因为后者包含前者,如果先捕获IOException那么
             * FileStateException就永远不可能会被捕获
             */
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            //这里不用打印e,而是用自己写的FileUploadIOException类并
            // 抛出文件读写异常
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/"+filename;
        userService.updateAvatarByUid(uid,avatar,username);
        //返回用户头像的路径给前端页面,将来用于头像展示使用
        return new JsonResult<>(OK,avatar);

    }

    /**
     * 设置上传文件的最大值
     * SpringMVC 默认大小的单位是字节，所以10MB是10 * 1024 * 1024
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;


    /**
     * 限制上传文件的类型
     */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    /** 给一个集合做初始化用静态代码块 因为只需初始化调用一次即可*/
    static {
        /** 图片类型都用image表示*/
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
}
