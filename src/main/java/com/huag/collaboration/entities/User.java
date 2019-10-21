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
    private String name;
    private String password;
    private String nationality;//民族
    private String birthday;
    private String domicilePlace;//户籍所在地
    private String telephoneNumber;
    private String gender;
    private String politicalOutlook;//政治面貌
    private String maritalStatus;//婚姻状况
    private String settledCity;//定居城市
    private String administrativePost;//行政职务
    private String sectoral;//所在部门
    private String sectoralProperty;//部门性质
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
    private String createTime;
    private String updateTime;
    private String deltag;
    private String remark;
    private String principalFlag;//能否当专业负责人属性
    private String chiefEngineerFlag;//能否当总工


}
