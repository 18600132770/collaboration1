package com.huag.collaboration.entities.fileTree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件树详细资料（文件url,name）
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
    private Integer projectId;//废弃
    private Integer fileTreeId;//文件树id
    private String createUser;
    private String createTime;
    private String updateTime;
    private String deltag;

}
