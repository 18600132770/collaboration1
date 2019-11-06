package com.huag.collaboration.mapper;

import com.huag.collaboration.entities.ProfessionalProfile;

import java.util.List;

/**
 * @author huag
 * @date 2019/10/28 15:31
 */
public interface ProfessionalProfileMapper {

    /**
     * 查找我收到的上序资料（上序专业主动发送给我的资料）
     * @param projectId
     * @return
     */
    List<ProfessionalProfile> findMyReceivedPreviousProfile(Integer projectId);

    /**
     * 查找我发起的上序资料（我需要我的上序专业给我提供的资料）
     * @param projectId
     * @return
     */
    List<ProfessionalProfile> findMyInitiatedPreviousProfile(Integer projectId);

    /**
     * 查找我提交的发送给下序专业的资料
     * @param projectId
     * @return
     */
    List<ProfessionalProfile> findMySubmitProfileToNextProfession(Integer projectId);

    /**
     * 查找下序专业发起的需要我提供的资料
     * @param projectId
     * @return
     */
    List<ProfessionalProfile> findMyNeededProfileToNextProfession(Integer projectId);

    /**
     * 更新
     * @param professionalProfile
     * @return
     */
    int update(ProfessionalProfile professionalProfile);

    /**
     * 添加
     * @param professionalProfile
     * @return
     */
    int insert(ProfessionalProfile professionalProfile);


}
