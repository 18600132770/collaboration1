package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上序专业资料
 * @author huag
 * @date 2019/10/19 19:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpperProfessionData {

    private Integer id;
    private String upperProfesson;//上序专业
    private Integer serialNumber;//序号
    private String neededContent;//所需内容
    private String fileName;//提的文件名
    private String uploadDate;//上传日期
    private String checkDate;//技表日期
    private String returnOpinion;//退回意见
    private String remark;//备注
    private String deltag;

}
