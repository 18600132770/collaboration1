package com.huag.collaboration.entities.fileTree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 项目文件树
 * @author huag
 * @date 2020/8/23 16:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSummaryFileTree {

    private Integer id;
    private Integer projectSummaryId;   //项目id
    private String fileTreeName;    //文件树名称
    private Integer fileTreeId; //文件树id
    private List<Profile> profileList; //该分支下的文件集合
    private String deltag;

}
