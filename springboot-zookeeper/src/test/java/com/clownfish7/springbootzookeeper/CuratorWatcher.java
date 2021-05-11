package com.clownfish7.springbootzookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2021-05-11 22:39
 */
public class CuratorWatcher {

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
                .namespace("watcher")
                // 构建对象
                .build();
        client.start();
    }

    @AfterEach
    public void after() {
        client.close();
    }

    @Test
    public void watcher1() throws Exception {
        // 监测节点
        final NodeCache nodeCache = new NodeCache(client, "/watchNode1");
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println(nodeCache.getCurrentData().getPath());
                System.out.println(nodeCache.getCurrentData().getStat().getVersion());
            }
        });
        // 多次监听，使用完关闭监听
        nodeCache.close();

    }

    @Test
    public void watcher2() throws Exception {
        // 监视子节点 param3 = 事件中是否可以获取子节点数据
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/childNode1", true);
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                // 事件类型
                System.out.println(pathChildrenCacheEvent.getType());
                // 数据
                System.out.println(pathChildrenCacheEvent.getData().getData());
                // 路径
                System.out.println(pathChildrenCacheEvent.getData().getPath());
            }
        });
        // 多次监听，使用完关闭监听
        pathChildrenCache.close();
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
