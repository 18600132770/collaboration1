package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huag
 * @date 2019/9/27 16:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String cardId;//员工编号
    private String username;
    private String password;
    private String nationality;//民族
    private String birthday;
    private String domicilePlace;//户籍所在地
    private String telephoneNumber;
    private String mailBox;//邮箱
    private String gender;
    private String politicalOutlook;//政治面貌
    private String maritalStatus;//婚姻状况
    private String settledCity;//定居城市
    private String administrativePost;//行政职务
    private Integer departmentId;//所在部门
    private String postSystem;//任职体系
    private String contractType;//合同类型
    private String entryDate;//入职时间
    private String originalWorkUnit;//原工作单位
    private String grade;//职称
    private String registrationStatus;//注册情况
    private String education;//学历
    private String graduateSchool;//毕业学校
    private String graduateDate;//毕业时间
    private String major;//专业
    private String undertakeJobs;//可以承担的职务：设计、复核、审核、审定

    private String designerFlag;//能否担任设计
    private String reviewerFlag;//能否担任复核
    private String inspectorFlag;//能否担任审核
    private String validationerFlag;//能否担任审定

    private String projectSummaryPrincipalFlag;//能否担任总项目负责人
    private String projectSummaryChiefEngineerFlag;//能否担任总项目总工

    private String principalFlag;//能否当专业负责人属性
    private String chiefEngineerFlag;//能否当专业总工

    private String remark;
    private String createTime;
    private String updateTime;
    private String tripState;//在京、新加坡
    private String deltag;


}
