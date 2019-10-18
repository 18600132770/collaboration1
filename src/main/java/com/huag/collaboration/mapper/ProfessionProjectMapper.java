package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.ProfessionProject;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/17 15:42
 */
public interface ProfessionProjectMapper {

    public List<ProfessionProject> findByProjectId(Integer projectId);

}
