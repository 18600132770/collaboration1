package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目总表
 * @author huag
 * @date 2019/10/22 9:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSummary {

    private Integer id;
    private String projectNum;//项目编号
    private String projectName;//项目名称
    private String designPhase;//设计阶段
    private Integer principalId;//项目总负责人id
    private String principal;//项目总负责人
    private Integer chiefEngineerId;//总工id
    private String chiefEngineer;//总工
    private String startTime;//开始时间
    private String stopTime;//截止时间
    private String leftTime;//剩余时间 XX天hh小时
    private String createTime;
    private String updateTime;
    private String deltag;

}
