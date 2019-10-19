package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huag
 * @date 2019/10/19 18:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    private Integer id;
    private String topic;//主题
    private String bussinessType;//业务类型
    private String founder;//创建人
    private String createTime;
    private String deltag;

}
