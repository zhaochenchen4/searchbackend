package com.back.search.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.back.search.model.entity.Post;
import com.back.search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//取消注释Component后，每次启动springboot项目会执行一次run方法
@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {
    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
        //获取数据
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();
        //将json数据转对象
        Map<String,Object> map = JSONUtil.toBean(result, Map.class);
        System.out.println(map);
        JSONObject data =(JSONObject) map.get("data");
        JSONArray records =(JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record:
                records) {
            JSONObject tempRecord =(JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags =(JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
        //数据入库 断言
        boolean res = postService.saveBatch(postList);
        if(res){
            log.info("获取初始内容列表成功，条数={}",postList.size());
        }else {
            log.error("获取初始内容列表失败");
        }
    }
}
