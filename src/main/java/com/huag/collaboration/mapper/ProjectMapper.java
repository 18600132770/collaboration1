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
    public Project findById(Integer id);
    public int deleteById(Integer id);
    public int insert(Project project);
    public int update(Project project);

}
