package com.unsky.myblog.dao;

import com.unsky.myblog.pojo.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * @author UNSKY
 * @date 2022/3/14 20:19
 */
@Mapper
public interface AdminUserMapper {
    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    /**
    * @Description:  用户登录
    * @Param: userName 用户名
    * @Param: password 密码
    * @return: 返回用户
    * @author: UNSKY
    * @date: 2022年4月5日
    */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    /**
    * @Description: 根据用户ID查询用户
    * @Param: adminUserId 用户ID
    * @return: 返回用户
    * @author: UNSKY
    * @date: 2022年4月5日
    */
    AdminUser selectByPrimaryKey(Integer adminUserId);

    /**
    * @Description:  更新用户信息
    * @return: 成功返回执行update的数量 失败返回0
    * @author: UNSKY
    * @date: 2022年4月13日
    */
    int updateByPrimaryKeySelective(AdminUser record);

}
