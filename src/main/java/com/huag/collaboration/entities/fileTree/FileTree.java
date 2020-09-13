package com.huag.collaboration.entities.fileTree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件树模板
 * @author huag
 * @date 2020/8/23 10:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTree {
    private Integer id;
    private String name;
    private String deltag;
}
