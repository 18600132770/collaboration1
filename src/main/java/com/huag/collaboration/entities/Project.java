package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * 项目信息
 * @author huag
 * @date 2019/9/26 16:55
 */
@Data
@AllArgsConstructor
public class Project {

    private Integer id;
    private String projectName;//项目名称
    private String designPhase;//设计阶段
    private Date startTime;//开始时间
    private Date stopTime;//截止时间
    private String leftTime;//剩余时间 XX天hh小时
    private Integer currentProcess;//目前进度
    private String emergencyLevel;//紧急程度
    private String deltag;//删除标志

//    private List<ProjectSubitem> projectSubitems;

}
