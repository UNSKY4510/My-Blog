package com.unsky.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author UNSKY
 * @date 2022/6/7 18:40
 */
@EnableAsync
@Configuration
public class TaskPoolConfig {

    @Bean("TaskExecutor")
    public Executor mailNumPoolSingleTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置为1，任务顺序执行
        executor.setCorePoolSize(2);//核心线程数：线程池创建时候初始化的线程数
        executor.setMaxPoolSize(5);//最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setQueueCapacity(5);//缓冲队列200：缓冲执行任务的队列
        executor.setKeepAliveSeconds(10);//允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setThreadNamePrefix("TaskExecutor-");//线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());// 线程池对拒绝任务的处理策略
        return executor;
    }
}
