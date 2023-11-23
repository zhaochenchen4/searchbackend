package com.back.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.back.search.model.entity.Picture;

import java.io.IOException;

/**
 * 图片服务
 */
public interface PictureService{
    Page<Picture> searchPicture(String searchText,long pageNum,long pageSize) throws IOException;
}
