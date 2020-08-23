package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.fileTree.ProjectSummaryFileTree;

import java.util.List;

/**
 * @author huag
 * @date 2020/8/23 17:18
 */
public interface ProjectSummaryFileTreeMapper {

    public int insert(ProjectSummaryFileTree projectSummaryFileTree);

    public List<ProjectSummaryFileTree> findByProjectSummaryId(Integer projectSummaryId);

}
