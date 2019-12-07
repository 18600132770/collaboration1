package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.ProjectSummary;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/22 9:59
 */
public interface ProjectSummaryMapper {

    /**
     * 查询所有总项目
     * @return
     */
    public List<ProjectSummary> findAll();

    /**
     * @param id
     * @return
     */
    public ProjectSummary findById(Integer id);

    /**
     * 插入
     * @param projectSummary
     * @return
     */
    public int insert(ProjectSummary projectSummary);

    /**
     * 更新
     * @param projectSummary
     * @return
     */
    public int update(ProjectSummary projectSummary);

}
