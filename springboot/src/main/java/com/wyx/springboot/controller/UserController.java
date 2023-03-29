package com.wyx.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyx.springboot.common.Constants;
import com.wyx.springboot.common.Result;
import com.wyx.springboot.controller.dto.UserDTO;
import com.wyx.springboot.entity.User;
import com.wyx.springboot.service.IUserService;
import com.wyx.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王sir
 * @since 2023-03-24
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${files.upload.path}")
    private String filesUploadPath;

    @Resource
    private IUserService userService;

    //登录界面接口
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username= userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto  = userService.login(userDTO);
        return Result.success(dto);
    }

    //注册接口
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username= userDTO.getUsername();
        String password = userDTO.getPassword();
        String nickname = userDTO.getNickname();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return  Result.success(userService.register(userDTO));
    }

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody User user){ //@RequestBody方法是把前台的json对象转成后台的java对象
       String  username = user.getUsername();
       if(StrUtil.isBlank(username)){
           return  Result.error(Constants.CODE_400,"参数错误");
       }
       if (user.getId() != null){
           user.setPassword(null);
       }else {
           user.setNickname(user.getUsername());
           if (user.getPassword() ==null){
               user.setPassword("123456");
           }
       }

        return Result.success(userService.saveOrUpdate(user));
        }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id)) ;
            }

    //查询全部
    @GetMapping
    public Result findAll() {
        return  Result.success(userService.list()) ;
            }

    //查询id
    @GetMapping("/{id}")
    public   Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
            }

    //个人信息
    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return  Result.success(userService.getOne(queryWrapper));
            }

    //批量删除接口
    @DeleteMapping ("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
            return
                    Result.success(userService.removeBatchByIds(ids));
            }

     //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam (defaultValue = "") String username,
                               @RequestParam (defaultValue = "") String nickname,
                               @RequestParam (defaultValue = "") String address,
                               @RequestParam (defaultValue = "") String email,
                               @RequestParam (defaultValue = "") String phone) {
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username",username);
        }
        if (!"".equals(nickname)) {
            queryWrapper.like("nickname",nickname);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address",address);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email",email);
        }
        if (!"".equals(phone)) {
            queryWrapper.like("phone",phone);
        }
        queryWrapper.orderByDesc("id");

        User currentUser =  TokenUtils.getCurrentUser();
        System.out.println(currentUser.getNickname());
            return Result.success(userService.page(new Page<>(pageNum, pageSize),queryWrapper)) ;

}

    /**
     * 导出Excel
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查出所有的数据
        List<User> list = userService.list();
        //通过工具类创建Writer 写出到磁盘路径
    //    ExcelWriter writer = ExcelUtil.getWriter(fileUploadPath + "/用户信息.xlsx");
        //内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id","Id");
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("wechat","微信");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("avatar_url","头像");

        //一次性自而出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }


    /**
     *  Excel导入数据
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("Id","id");
        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("昵称","nickname");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("电话","phone");
        reader.addHeaderAlias("地址","address");
        reader.addHeaderAlias("微信","wechat");
        reader.addHeaderAlias("头像","avatar_url");
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
        return Result.success(true);
    }

}

