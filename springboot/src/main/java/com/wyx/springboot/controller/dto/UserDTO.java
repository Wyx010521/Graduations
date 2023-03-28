package com.wyx.springboot.controller.dto;

import lombok.Data;
/**
 *接收前端登录请求参数
 *
 */

@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
