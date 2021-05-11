package com.clownfish7.springbootzookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryOneTime;

/**
 * @author You
 * @create 2021-05-11 22:30
 */
public class CuratorConnection {
    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                // ip地址端口号
                .connectString("ip:port,ip:port")
                // 会话超时时间
                .sessionTimeoutMs(5000)
                // 重试策略
//                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .retryPolicy(new RetryOneTime(3000))
                // 命名空间
                .namespace("create")
                // 构建对象
                .build();

        client.start();
        // 过时方法
        client.isStarted();
        CuratorFrameworkState state = client.getState();
        client.close();
    }
}
