package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提下序
 * @author huag
 * @date 2019/10/19 20:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnderProfessionData {

    private Integer id;
    private Integer serialNumber;//序号
    private String neededContent;//所需内容
    private String fileName;//文件名
    private String uploadDate;//上传日期
    private String checkDate;//技表日期
    private String upderProfession;//下序专业
    private String remark;//备注
    private String deltag;

}
