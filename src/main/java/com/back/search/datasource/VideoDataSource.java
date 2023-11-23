package com.back.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;

//todo 模拟第三方数据源接入
public class VideoDataSource  implements DataSource<T>{

    @Override
    public Page<T> doSearch(String searchText, long pageNum, long pageSize) {
        return null;
    }
}
