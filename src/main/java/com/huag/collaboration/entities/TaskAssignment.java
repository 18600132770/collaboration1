package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 任务分配详情
 * @author huag
 * @date 2019/10/24 14:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignment implements Serializable {

    private Integer id;
    private String taskName;
    private Integer projectId;//专业分项id

    private Integer designerId;//设计者id
    private Integer reviewerId;//复核者id
    private Integer inspectorId;//审核者id
    private Integer validationerId;//审定者id

    private String taskLevel;//任务级别
    private String startTime;
    private String stopTime;

    private String createtTime;
    private String updateTime;
    private String deltag;

}
