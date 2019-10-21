package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目信息
 * @author huag
 * @date 2019/9/26 16:55
 */
@Data
@NoArgsConstructor//生成一个无参数的构造方法
@AllArgsConstructor//生成一个包含所有变量,同时如果变量使用了NotNull annotation,会进行是否为空的构造方法
public class Project {

    private Integer id;
    private String projectNum;//项目编号
    private String projectName;//项目名称
    private String designPhase;//设计阶段
    private String principal;//负责人
    private String chiefEngineer;//总工
    private String startTime;//开始时间
    private String stopTime;//截止时间
    private String leftTime;//剩余时间 XX天hh小时
    private Integer currentProcess;//目前进度
    private String emergencyLevel;//紧急程度
    private String createTime;
    private String updateTime;
    private Integer departmentId;//所id
    private String deltag;//删除标志

//    private List<ProjectSubitem> projectSubitems;

}
