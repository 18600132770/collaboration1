package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.ProjectSubitem;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/8 13:35
 */
public interface ProjectSubitemMapper {

    public List<ProjectSubitem> findByProjectId(Integer projectId);
    public int deleteById(Integer id);
    public ProjectSubitem findById(Integer id);

}
