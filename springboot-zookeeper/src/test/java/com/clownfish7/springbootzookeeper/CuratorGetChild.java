package com.clownfish7.springbootzookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author You
 * @create 2021-05-11 22:39
 */
public class CuratorGetChild {

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
                .namespace("getChild")
                // 构建对象
                .build();
        client.start();
    }

    @AfterEach
    public void after() {
        client.close();
    }

    @Test
    public void get1() throws Exception {
        List<String> list = client.getChildren()
                .forPath("/node1");
        list.forEach(System.out::println);

    }

    @Test
    public void get2() throws Exception {
        client.getChildren()
                .inBackground((client, event) -> {
                    System.out.println(new String(event.getData()));
                    System.out.println(event.getPath());
                    event.getChildren().forEach(System.out::println);
                }, "context")
                .forPath("/node2");

    }

}
