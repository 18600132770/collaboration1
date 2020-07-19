package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huag
 * @date 2020/7/19 17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEnum {

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

    private List<Project> children;
}
