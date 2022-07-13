package com.unsky.myblog.service;

import com.unsky.myblog.pojo.AdminUser;

/**
 * @author UNSKY
 * @date 2022/3/14 20:22
 */
public interface AdminUserService {

    /**
    * @Description:  登录
    * @Param: userName 用户姓名
    * @Param: password 用户密码
    * @return: 返回用户对象
    * @author: UNSKY
    * @date: 2022年4月5日
    */
    AdminUser login(String userName, String password);

    /**
    * @Description:  获取用户的信息
    * @Param: loginUserId 登录用户的ID
    * @return: 返回用户的信息
    * @author: UNSKY
    * @date: 2022年4月5日
    */
    AdminUser getUserDetailById(Integer loginUserId);

    /**
    * @Description:  修改当前登录用户的密码
    * @Param: loginUserId 登录用户ID
    * @Param: originalPassword 旧密码
    * @Param: newPassword 新密码
    * @return: true=更新成功  false=更新失败
    * @author: UNSKY
    * @date: 2022年4月5日
    */
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /**
    * @Description:  修改当前登录用户昵称
    * @Param: loginUserId 登录用户ID
    * @Param: loginUserName 原始登录用户名称
    * @Param: nickName 用户昵称
    * @return: true=更新成功  false=更新失败
    * @author: UNSKY
    * @date: 2022年4月5日
    */
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}
