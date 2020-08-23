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
    List<User> findAllPrincipals(@Param("departmentId") Integer departmentId);

    /**
     * 找所有可以是总工的用户
     * @return
     */
    List<User> findAllChiefEngineers(@Param("departmentId") Integer departmentId);

    List<User> findByUserNameAndDeptId(@Param("username") String username, @Param("departmentId") Integer departmentId);

    List<User> findByUserName(String username);

    /**
     * 查找可以担任总项目负责人的人选
     * @return
     */
    List<User> findProjectSummaryPrincipals();

    /**
     * 查找可以担任总项目总工的人选
     * @return
     */
    List<User> findProjectSummaryChiefEngineers();

    /**
     * 查询全部
     * @return
     */
    List<User> findAll();

    /**
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * 根据人名修改出差状态
     * @param username
     * @param tripState
     * @return
     */
    int updateUserTripStateByUsername(@Param("username")String username, @Param("tripState")String tripState);

    /**
     * 根据员工唯一cardId识别，例如mayun02
     * @param cardId
     * @return
     */
    User findByCardId(String cardId);

}
