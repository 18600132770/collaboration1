package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.TaskReviewProcess;

import java.util.List;

/**
 * 任务审批流程
 * @author huag
 * @date 2020/9/13 4:37 下午
 */
public interface TaskReviewProcessMapper {

    /**
     * 根据任务id查看任务审批流程
     * @param id
     * @return
     */
    List<TaskReviewProcess> findByTaskAssignmentId(Integer id);

    /**
     *
     * @param taskReviewProcess
     * @return
     */
    int insert(TaskReviewProcess taskReviewProcess);

    /**
     *
     * @param taskReviewProcess
     * @return
     */
    int update(TaskReviewProcess taskReviewProcess);

}
