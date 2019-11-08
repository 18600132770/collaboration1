package com.huag.collaboration.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页工具
 * @author huag
 * @date 2019/11/7 9:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBaseResponse<T> {

    private List<T> list;
    public int currentPage;
    public int totalPage;
    public int pageSize;
    public long totalCount;

}
