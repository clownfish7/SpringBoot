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
public class CuratorGet {

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
    public void get1() throws Exception {
        // get znode
        byte[] bytes = client.getData()
                .forPath("/node1");
        System.out.println(new String(bytes));

    }

    @Test
    public void get2() throws Exception {
        // get znode
        Stat stat = new Stat();
        byte[] bytes = client.getData()
                .storingStatIn(stat)
                .forPath("/node1");
        System.out.println(new String(bytes));
        System.out.println(stat.getVersion());

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
