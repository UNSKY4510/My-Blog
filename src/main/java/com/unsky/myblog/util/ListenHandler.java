package com.unsky.myblog.util;

import com.unsky.myblog.config.BackUpDataBaseManager;
import com.unsky.myblog.dao.BlogMapper;
import com.unsky.myblog.pojo.Blog;
import com.unsky.myblog.service.BlogService;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author UNSKY
 * @date 2022/5/21 0:38
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableScheduling
@EnableAsync
public class ListenHandler {
    private static final Logger log = LoggerFactory.getLogger(ListenHandler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private  BlogService blogService;

    @Autowired
    private  RedisUtil redisUtil;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BackUpDataBaseManager backUpDataBaseManager;


    public ListenHandler(){
        System.out.println("============开始初始化============");
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("=============数据库开始初始化=============");
        //将数据库中的数据写入redis
        List<Blog> blogList = blogService.selectAll();
        blogList.forEach(blog -> {
            // 设置blog的浏览量的Sorted Set
            redisUtil.zAdd("Blog_Views",blog.getBlogId().toString(),blog.getBlogViews().doubleValue());

            // 设置blog的发布时间List
            redisUtil.lSet("Blog_Time",blog.getBlogId());

            //初始浏览量
            redisUtil.set("blog_id:"+blog.getBlogId().toString(),blog.getBlogViews());
        });
        System.out.println("===========已写入Redis==========");
    }

    //关闭时操作
    @PreDestroy
    public void destroy(){
        System.out.println("=======关闭========");
        //将Redis中的数据取出    写入数据库
        List<Blog> blogList = blogService.selectAll();
        blogList.forEach(blog -> {
            blog.setBlogViews(redisUtil.zScore("Blog_Views",blog.getBlogId().toString()).longValue());
            redisUtil.expire("blog_id:"+blog.getBlogId().toString(),1,TimeUnit.MILLISECONDS);
            blogMapper.updateByPrimaryKeySelective(blog);
        });
        System.out.println("======数据从Redis取出完毕======");
        System.out.println("======系统关闭中=======");
    }


    /**
     * 每30秒定时更新点击量
     */
    @Async("TaskExecutor")
    @Scheduled(cron = "0/30 * * * * ?")
    public void timeToSaveClick(){
        System.out.println("更新点击量线程:"+Thread.currentThread().getName());
        System.out.println("========点击量定时写入=========");
        //将Redis中的数据取出    写入数据库
        List<Blog> blogList = blogService.selectAll();
        blogList.forEach(blog -> {
            // 如果浏览量有更新则刷新数据库
            String blogViews = String.valueOf(redisUtil.zScore("Blog_Views", blog.getBlogId().toString()).longValue());
            String blogIdViews = String.valueOf(redisUtil.get("blog_id:" + blog.getBlogId()));
            if(!blogViews.equals(blogIdViews)){
                blog.setBlogViews(Long.valueOf(blogViews));
                redisUtil.set("blog_id:"+blog.getBlogId().toString(),blogViews);
                System.out.println(blog);
                blogMapper.updateByPrimaryKeySelective(blog);
            }
        });
        System.out.println("=====点击量定时写入完成========");
    }

    /**
     * 每天23.59备份数据库
     */
    @Async("TaskExecutor")
    @Scheduled(cron = "0 59 23 * * ?")
    public void timeToSaveDataBase(){
        System.out.println("更新数据库线程:"+Thread.currentThread().getName());
        System.out.println("========数据库定时写入=========");
        String format = dateFormat.format(System.currentTimeMillis());
        log.info("The time is now {}", format);
        backUpDataBaseManager.exportSql(format);
        System.out.println("=====数据库定时写入完成========");
    }
}
