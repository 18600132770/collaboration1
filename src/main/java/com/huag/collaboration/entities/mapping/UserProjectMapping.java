package com.huag.collaboration.entities.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 人员项目信息映射
 * @author huag
 * @date 2019/10/16 22:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectMapping {

    private Integer id;
    private String username;
    private String tripState;//出差状态
    private Integer finishedWorkload;//完成的工作量
    private Integer currentWorkload;//目前工作量
    private String projectName;//项目名称
    private String designPhase;//设计阶段
    private String taskLevel;//任务级别
    private String startTime;//开始时间
    private String stopTime;//截止时间
    private String leftTime;//剩余时间 XX天hh小时
    private Integer currentProcess;//目前进度
    private String role;//负责人、总工、参与
    private String state;//项目状态

    private Boolean hidden;//是总的就隐藏

    private Boolean reverseHidden;//分的就显示

    private Boolean hasChildren;

    private List<UserProjectMapping> children;

}
