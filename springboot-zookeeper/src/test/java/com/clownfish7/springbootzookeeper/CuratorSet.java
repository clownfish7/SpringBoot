package com.clownfish7.springbootzookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author You
 * @create 2021-05-11 22:39
 */
public class CuratorSet {

    CuratorFramework client;

    @BeforeEach
    public void before() {
        client = CuratorFrameworkFactory.builder()
                // ip地址端口号
                .connectString("ip:port,ip:port")
                // 会话超时时间
                .sessionTimeoutMs(5000)
                // 重试策略
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                // 命名空间
                .namespace("set")
                // 构建对象
                .build();
        client.start();
    }

    @AfterEach
    public void after() {
        client.close();
    }

    @Test
    public void set1() throws Exception {
        // set znode
        client.setData()
                .forPath("/node1", "node1".getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void set2() throws Exception {
        // set znode
        client.setData()
                // 指定版本号
                .withVersion(-1)
                .forPath("/node1", "node1".getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void set3() throws Exception {
        // 异步
        client.setData()
                // 指定版本号
                .withVersion(-1)
                // 异步
                .inBackground((client, event) -> {
                    event.getPath();
                }, "context")
                .forPath("/node1", "node1".getBytes(StandardCharsets.UTF_8));

    }

}
