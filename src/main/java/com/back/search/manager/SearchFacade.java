package com.back.search.manager;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.back.search.common.ErrorCode;
import com.back.search.datasource.*;
import com.back.search.exception.ThrowUtils;
import com.back.search.model.dto.post.PostQueryRequest;
import com.back.search.model.dto.search.SearchRequset;
import com.back.search.model.dto.user.UserQueryRequest;
import com.back.search.model.entity.Picture;
import com.back.search.model.enums.SearchTypeEnum;
import com.back.search.model.vo.PostVO;
import com.back.search.model.vo.SearchVO;
import com.back.search.model.vo.UserVO;
import com.back.search.service.PictureService;
import com.back.search.service.PostService;
import com.back.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
//门面模式
@Component
@Slf4j
public class SearchFacade {
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private DataSourceRegistry dataSourceRegistry;
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;

    public SearchVO searchAll(@RequestBody SearchRequset searchRequset, HttpServletRequest request) throws ExecutionException, InterruptedException, IOException {
        String type = searchRequset.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        String searchText = searchRequset.getSearchText();
        long current = searchRequset.getCurrent();
        long pageSize = searchRequset.getPageSize();
        //判空
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        SearchVO searchVO = new SearchVO();
        if(type == null){
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(()->{
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                return userDataSource.doSearch(searchText,current,pageSize);
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(()->{
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                return postDataSource.doSearch(searchText,current,pageSize);
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(()-> pictureDataSource.doSearch(searchText, 1, 10));

            CompletableFuture.allOf(userTask,postTask,pictureTask).join();
            Page<UserVO> userVOPage = userTask.get();
            Page<PostVO> postVOPage = postTask.get();
            Page<Picture> picturePage = pictureTask.get();
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
        }
        else{
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
        }
        return searchVO;
    }
}
