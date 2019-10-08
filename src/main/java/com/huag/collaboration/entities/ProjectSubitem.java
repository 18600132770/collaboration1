package com.huag.collaboration.entities;

import lombok.Data;

/**
 * 项目分项
 * @author huag
 * @date 2019/9/29 9:41
 */
@Data
public class ProjectSubitem {

    private Integer id;
    private String professionalSubOption;//专业分项
    private String taskLevel;
    private String startTime;
    private String stopTime;
    private Integer currentProcess;//目前进度
    private String totalWorkload;//总工作量
    private Integer user_id;
    private String deltag;

    private Integer projectId;//项目id

//    private Project project;

}
