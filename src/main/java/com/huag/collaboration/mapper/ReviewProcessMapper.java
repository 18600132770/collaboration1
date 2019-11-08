package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.ReviewProcess;

import java.util.List;

/**
 * @author huag
 * @date 2019/11/8 9:27
 */
public interface ReviewProcessMapper {

    /**
     * 根据任务id查看审核意见
     * @param taskAssignmentId
     * @return
     */
    List<ReviewProcess> findByTaskAssignmentId(Integer taskAssignmentId);

    /**
     * 插入数据
     * @param reviewProcess
     * @return
     */
    int insert(ReviewProcess reviewProcess);

    /**
     * findById
     * @param id
     * @return
     */
    List<ReviewProcess> findById(Integer id);

    /**
     * update
     * @param reviewProcess
     * @return
     */
    int update(ReviewProcess reviewProcess);


}
