package com.back.search.controller;

import com.back.search.common.BaseResponse;
import com.back.search.common.ResultUtils;
import com.back.search.manager.SearchFacade;
import com.back.search.model.dto.search.SearchRequset;
import com.back.search.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequset searchRequset, HttpServletRequest request) throws ExecutionException, InterruptedException, IOException {
        return ResultUtils.success(searchFacade.searchAll(searchRequset,request));
    }
}
