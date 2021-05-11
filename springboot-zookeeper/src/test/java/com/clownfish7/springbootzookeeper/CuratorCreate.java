package com.clownfish7.springbootzookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author You
 * @create 2021-05-11 22:39
 */
public class CuratorCreate {

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
                .namespace("create")
                // 构建对象
                .build();
        client.start();
    }

    @AfterEach
    public void after() {
        client.close();
    }

    @Test
    public void create1() throws Exception {
        // create znode
        client.create()
                // 临时节点
                .withMode(CreateMode.EPHEMERAL)
                // 节点权限 world:anyone:cdrwa
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // 节点路径 & 节点数据
                .forPath("/node1", "node1".getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void create2() throws Exception {
        ArrayList<ACL> acl = new ArrayList<>();
        acl.add(new ACL(ZooDefs.Perms.ALL, new Id("ip", "192.168.116.151")));
        // create znode
        client.create()
                // 临时节点
                .withMode(CreateMode.EPHEMERAL)
                // 节点权限 world:anyone:cdrwa
                .withACL(acl)
                // 节点路径 & 节点数据
                .forPath("/node2", "node2".getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void create3() throws Exception {
        // 递归创建节点
        ArrayList<ACL> acl = new ArrayList<>();
        acl.add(new ACL(ZooDefs.Perms.ALL, new Id("ip", "192.168.116.151")));
        // create znode
        client.create()
                // 持支递归节点创建，if parentNode is not exist
                .creatingParentsIfNeeded()
                // 临时节点
                .withMode(CreateMode.EPHEMERAL)
                // 节点权限 world:anyone:cdrwa
                .withACL(acl)
                // 节点路径 & 节点数据
                .forPath("/node3/node31", "node31".getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void create4() throws Exception {
        // 异步创建节点
        ArrayList<ACL> acl = new ArrayList<>();
        acl.add(new ACL(ZooDefs.Perms.ALL, new Id("ip", "192.168.116.151")));
        // create znode
        client.create()
                // 持支递归节点创建，if parentNode is not exist
                .creatingParentsIfNeeded()
                // 临时节点
                .withMode(CreateMode.EPHEMERAL)
                // 节点权限 world:anyone:cdrwa
                .withACL(acl)
                // 异步回调接口 BackgroundCallback
                .inBackground((client, event) -> {
                            long czxid = event.getStat().getCzxid();
                        },
                        "context")
                // 节点路径 & 节点数据
                .forPath("/node3/node31", "node31".getBytes(StandardCharsets.UTF_8));

    }
}
