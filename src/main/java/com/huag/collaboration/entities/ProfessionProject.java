package com.huag.collaboration.entities;

import lombok.Data;

/**
 * 专业项目总列表
 * @author huag
 * @date 2019/10/17 14:34
 */
@Data
public class ProfessionProject {

    private String id;
    private String profession;//专业
    private String professionValidation;//专业审定
    private String departmentalValidation;//处审定
    private Integer projectId;//项目id
    private String remark;//备注
    private String deltag;

}
