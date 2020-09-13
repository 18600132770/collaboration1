package com.huag.collaboration.entities;

import lombok.Data;

/**
 * 常用配置参数
 * @author huag
 * @date 2020/9/13 5:38 下午
 */
@Data
public class Configueations {


    /**
     * 角色
     * 设计：designer
     * 复核：reviewer
     * 审核：inspector
     * 审定：validationer
     */
    public String[] taskReviewProcessRoles = new String[]{"designer", "reviewer", "inspector", "validationer"};

}
