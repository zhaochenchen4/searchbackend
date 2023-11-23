package com.back.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.back.search.model.dto.user.UserQueryRequest;
import com.back.search.model.vo.UserVO;
import com.back.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
  *
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO>  {

    @Resource
    private UserService userService;

    /**
     * 适配器模式  转换数据源类型
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return userVOPage;
    }
}
