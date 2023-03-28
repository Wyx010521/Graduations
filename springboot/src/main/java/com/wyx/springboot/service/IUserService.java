package com.wyx.springboot.service;

import com.wyx.springboot.controller.dto.UserDTO;
import com.wyx.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王sir
 * @since 2023-03-24
 */
public interface IUserService extends IService<User> {

   UserDTO  login(UserDTO userDTO);

   User register(UserDTO userDTO);
}
