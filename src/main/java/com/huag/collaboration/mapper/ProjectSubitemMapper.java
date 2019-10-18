package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.ProjectSubitem;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/8 13:35
 */
public interface ProjectSubitemMapper {

    public List<ProjectSubitem> findByProjectId(String projectId);
    public int deleteById(String id);
    public ProjectSubitem findById(String id);
    public int insert(ProjectSubitem projectSubitem);

}
