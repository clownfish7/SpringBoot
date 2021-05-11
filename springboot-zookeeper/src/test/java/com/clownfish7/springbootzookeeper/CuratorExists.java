package com.clownfish7.springbootzookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2021-05-11 22:39
 */
public class CuratorExists {

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
                .namespace("get")
                // 构建对象
                .build();
        client.start();
    }

    @AfterEach
    public void after() {
        client.close();
    }

    @Test
    public void exists1() throws Exception {
        // 不存在返回null
        Stat stat = client.checkExists()
                .forPath("/node1");
    }

    @Test
    public void exists2() throws Exception {
        Stat stat = client.checkExists()
                .inBackground((client, event) -> {
                    System.out.println(new String(event.getData()));
                    System.out.println(event.getPath());
                    event.getStat();
                }, "context")
                .forPath("/node1");

    }

    @Test
    public void get3() throws Exception {
        // get znode
        client.getData()
                .inBackground((client, event) -> {
                    System.out.println(new String(event.getData()));
                    System.out.println(event.getPath());
                }, "context")
                .forPath("/node3/node31");
    }

}
