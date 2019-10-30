package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件树资料
 * @author huag
 * @date 2019/10/30 16:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private Integer id;
    private String name;
    private String url;
    private Integer projectId;
    private String createUser;
    private String createTime;
    private String updateTime;
    private String deltag;

}
