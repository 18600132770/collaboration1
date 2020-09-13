package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.fileTree.FileTree;

import java.util.List;

/**
 * @author huag
 * @date 2020/8/23 10:10
 */
public interface FileTreeMapper {

    public List<FileTree> findAll();
    int delete(Integer id);

}
