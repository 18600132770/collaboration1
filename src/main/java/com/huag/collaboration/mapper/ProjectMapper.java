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

    public List<Project> findAll();
    public List<Project> findByProjectName(String projectName);
    public Project findById(Integer id);
    public int deleteById(Integer id);
    public int insert(Project project);
    public int update(Project project);
    public List<Project> findAllIn(String[] array);

    public List<Project> findProjectByDepartmentId(Integer departmentId);

    /**
     * 根据projectName的模糊查询和deptId进行查询
     * @param projectName
     * @param departmentId
     * @return
     */

    public List<Project> findByProjectNameAndDeptId(@Param("projectName") String projectName, @Param("departmentId") Integer departmentId);


}
