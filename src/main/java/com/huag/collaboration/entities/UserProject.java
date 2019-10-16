package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * @author huag
 * @date 2019/10/16 22:49
 */
@Data
@AllArgsConstructor
public class UserProject {

    private Integer id;
    private String username;
    private String state;
    private String projectName;//项目名称
    private String designPhase;//设计阶段
    private String professionalSubOption;//专业分项
    private String taskLevel;
    private Date startTime;//开始时间
    private Date stopTime;//截止时间
    private String leftTime;//剩余时间 XX天hh小时
    private Integer currentProcess;//目前进度
    private Integer totalWork;//总工作量
    private String deltag;//删除标志

}
