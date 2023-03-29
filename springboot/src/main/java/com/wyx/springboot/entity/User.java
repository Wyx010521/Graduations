package com.wyx.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 王sir
 * @since 2023-03-24
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @TableField("username")
      @ApiModelProperty("用户名")
      private String username;

      @JsonIgnore
      @TableField("password")
      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("昵称")
      @TableField("nickname")
      private String nickname;

      @ApiModelProperty("电子邮箱")
      @TableField("email")
      private String email;

      @ApiModelProperty("手机号")
      @TableField("phone")
      private String phone;

      @ApiModelProperty("微信号")
      @TableField("wechat")
      private String wechat;

      @ApiModelProperty("地址")
      @TableField("address")
      private String address;

      @ApiModelProperty("创建时间")
      @TableField("create_time")
      private LocalDateTime createTime;

      @TableField("avatarUrl")
      @ApiModelProperty("头像")
      private String avatarUrl;


}
