package com.back.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.back.search.common.BaseResponse;
import com.back.search.common.ErrorCode;
import com.back.search.common.ResultUtils;
import com.back.search.exception.BusinessException;
import com.back.search.exception.ThrowUtils;
import com.back.search.model.dto.picture.PictureQueryRequest;
import com.back.search.model.entity.Picture;
import com.back.search.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                        HttpServletRequest request) {
        String searchText = pictureQueryRequest.getSearchText();
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> picturePage = null;
        try {
            picturePage = pictureService.searchPicture(searchText, current, size);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"picturePage异常");
        }

        return ResultUtils.success(picturePage);
    }
}
