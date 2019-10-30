package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.Profile;

import java.util.List;

/**
 * 文件树
 * @author huag
 * @date 2019/10/30 16:09
 */
public interface ProfileMapper {

    /**
     * 插入数据
     * @param profile
     * @return
     */
    int insert(Profile profile);

    /**
     * @param projectId
     * @return
     */
    List<Profile> findByProjectId(Integer projectId);

    /**
     * @param id
     * @return
     */
    int update(Integer id);

}
