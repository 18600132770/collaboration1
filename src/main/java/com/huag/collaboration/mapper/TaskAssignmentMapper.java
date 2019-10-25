package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.TaskAssignment;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/25 11:21
 */
public interface TaskAssignmentMapper {

    /**
     * 根据分项目查询该分项目下的任务分配情况
     * @param projectId
     * @return
     */
    List<TaskAssignment> findByProjectId(Integer projectId);

    /**
     * 插入数据
     * @param taskAssignment
     * @return
     */
    int insert(TaskAssignment taskAssignment);

}
