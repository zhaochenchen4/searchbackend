package com.back.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据接口 新接入数据源实现
 */
public interface DataSource<T> {
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
