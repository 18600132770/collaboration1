package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专业资料
 * @author huag
 * @date 2019/10/28 13:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalProfile {

    private Integer id;
    private String content;//内容
    private String fileName;//文件名
    private String filePath;//文件路径
    private String uploadDate;//上传日期
    private String checkDate;//技表日期

    private Integer departmentId;
    private String departmentName;

    private Integer submitActionProjectId;//提交行为的分项目id
    private Integer receiveActionProjectId;//接收行为的分项目id

    private Integer initiatorProjectId;//发起本任务的分项目id

    //1、上序资料：receiveActionProjectId = projectId 上序资料内容
    // （1）initiatorProjectId = projectId 我发起的我需要别人给我发过来的资料
    // （2）initiatorProjectId != projectId 别人传递给我的上序资料

    //2、下序资料：submitActionProjectId = projecctId 下序资料内容
    // （1）initiatorProjectId = projectId 我发起的我发送给别人的下序资料
    // （2）initiatorProjectId != projectId 别人发起的我要我提供给他的下序资料

    private String dealFlag;//被处理标识, null：未处理   0：接受    1：驳回
    private String dealOpinions;//处理意见

    private String createTime;
    private String updateTime;
    private String deltag;


}
