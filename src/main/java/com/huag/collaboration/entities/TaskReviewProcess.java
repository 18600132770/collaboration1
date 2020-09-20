package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务审核流程
 * @author huag
 * @date 2020/9/13 4:25 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskReviewProcess {

    private Integer id;
    private Integer taskAssignmentId;

    /**
     * 角色
     * 设计：designer
     * 复核：reviewer
     * 审核：inspector
     * 审定：validationer
     */
    private String role;

    /**
     * 用户名
     */
    private String username;

    /**
     * json串
     * [
     *     {
     *         "fileName":"文件名",
     *         "fileUrl":"oss文件路径"
     *     },
     *     {
     *         "fileName":"文件名",
     *         "fileUrl":"oss文件路径"
     *     }
     * ]
     */
    private String file; //文件json数组

    private String reviewOpinion;//审核意见

    private Boolean reviewPassedFlag; //审核是否通过，null：还未审核，true:审核通过，false：审核不通过。

    private Boolean reviewButtonShowFlag; //审核按钮显示标志。为true显示审核按钮

    private Integer rejectLevel; //驳回级别

    private String createTime;
    private String updateTime;

    private Integer orderNum;//排序。方便被驳回后中间新加新的审核流程

    private String deltag;

}
