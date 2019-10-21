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
    public List<UserProjectMapping> findUserProjectByProjectNameAndDeptId(@Param("searchKey") String searchKey, @Param("departmentId") Integer departmentId);

}
