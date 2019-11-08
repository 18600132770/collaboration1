package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 送审流程
 * @author huag
 * @date 2019/11/8 9:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewProcess {

    private Integer id;
    private String fileNames;
    private String fileUrls;

    private String designerOpinion;//设计者意见
    private String designerCreateTime;

    private String reviewerOpioion;//复核者意见
    private String reviewerCreateTime;

    private String inspectorOpionion;//审核者意见
    private String inspectorCreateTime;

    private String validationerOpioion;//审定者意见
    private String validationerCreateTime;

    private Integer taskAssignmentId;

    private String deltag;

}
