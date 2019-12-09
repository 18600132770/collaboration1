package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.mapping.UserProjectMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/16 22:53
 */
public interface UserProjectMapper {

    /**
     * 根据projectName的模糊查询和deptId进行查询
     * @param searchKey
     * @param departmentId
     * @return
     */
    List<UserProjectMapping> findUserProjectByProjectNameAndDeptId(@Param("searchKey") String searchKey, @Param("departmentId") Integer departmentId);


    /**
     * 查找本部门不在本名单的人员并且没有项目的人员信息情况
     * @param department
     * @param usernameList
     * @return
     */
    List<UserProjectMapping> findUserProjectsByDeptIdAndRoleWhereUsersNotIn(@Param("departmentId") Integer department, @Param("usernameArray") String[] usernameList, @Param("selectUserRole") String selectUserRole);

    /**
     * 查找本部门能担任role(设计、复核、审核、审定)的工程师
     * @param departmentId
     * @param role
     * @return
     */
    List<UserProjectMapping> findUserProjectsByDeptIdAndRole(@Param("departmentId") Integer departmentId, @Param("selectUserRole") String selectUserRole);
}
