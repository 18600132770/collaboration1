package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/21 15:12
 */
public interface UserMapper {

    /**
     * 查找所有可以是负责人的用户
     * @return
     */
    public List<User> findAllPrincipals(@Param("departmentId") Integer departmentId);

    /**
     * 找所有可以是总工的用户
     * @return
     */
    public List<User> findAllChiefEngineers(@Param("departmentId") Integer departmentId);

    List<User> findByUserNameAndDeptId(@Param("username") String username, @Param("departmentId") Integer departmentId);

}
