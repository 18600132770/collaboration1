package com.huag.collaboration.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 所名
 * @author huag
 * @date 2019/10/17 14:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
	private Integer id;
	private String departmentName;
	private String departmentProperty;//部门性质
	private Integer serialNumber;//序号，用于排序
	private String createTime;
	private String updateTime;
	private String style;
	private String deltag;
}
