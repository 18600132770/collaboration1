package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author huag
 * @date 2019/9/27 13:41
 */
//@Mapper
public interface ProjectMapper {

    List<Project> findAll();
    List<Project> findByProjectName(String projectName);
    Project findById(Integer id);
    int deleteById(Integer id);
    int insert(Project project);
    int update(Project project);
    List<Project> findAllIn(String[] array);

    List<Project> findProjectByDepartmentId(Integer departmentId);


    /**
     * 按照总项目id查询
     * @param projectSummaryId
     * @return
     */
    List<Project> findProjectsByProjectSummaryId(Integer projectSummaryId);

    /**
     * 根据projectName的模糊查询和deptId进行查询
     * @param projectName
     * @param departmentId
     * @return
     */

    List<Project> findByProjectNameAndDeptId(@Param("projectName") String projectName, @Param("departmentId") Integer departmentId);


}
