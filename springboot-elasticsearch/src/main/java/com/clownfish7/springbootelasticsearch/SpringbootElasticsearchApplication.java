package com.clownfish7.springbootelasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot默认支持两种技术来和ES交互；
 * 1、Jest（默认不生效）
 *      需要导入jest的工具包（io.searchbox.client.Jestclient）
 * 2、SpringData ElasticSearch [Es版本有可能不合适]
 *          版本适配说明：https://github.com/spring-projects/spring-data-elasticsearch
 *              如果版本不适配：2.4.6
 *              1）、升级springBoot版本
 *              2）、安装对应版本的ES
 *      1）、Client 节点信息clusterNodes；clusterName
 *      2）、ELasticsearchTemplate 操作es
 *      3）、编写一个ElasticsearchRepository的子接口来操作ES；
 *
 *      elasticsearch从5版本以后默认不开启远程连接，需要修改配置文件
 *          运行测试程序，发现会报如下错误
 *          NoNodeAvailableException[None of the configured nodes are available: 
 *
 *      解决办法：
 *          1.进入容器
 *              docker exec ‐it tensquare_elasticsearch  /bin/bash
 *          2.看到elasticsearch所在的目录为/usr/share/elasticsearch  ,进入config看到了 配置文件
 *              elasticsearch.yml
 *          3.容器内没有vi命令 拷贝配置文件到宿主机
 *              docker cp elasticsearch:/usr/share/elasticsearch/config/elasticsearch.yml  /usr/share/elasticsearch.yml
 *          4.停止和删除原来创建的容器
 *              docker stop elasticsearch  
 *              docker rm elasticsearch
 *          5.重新执行创建容器命令
 *              docker run ‐di ‐‐name=elasticsearch ‐p 9200:9200 ‐p 9300:9300 ‐v  /usr/share/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml elasticsearch
 *          6.修改/usr/share/elasticsearch.yml   将 transport.host: 0.0.0.0 前的#去掉后保 存文件退出。 其作用是允许任何ip地址访问elasticsearch
 *              开发测试阶段可以这么做，生 产环境下指定具体的IP
 *          7.重启启动
 *              docker restart elasticsearch
 *          8.重启后发现重启启动失败了，这时什么原因呢？这与我们刚才修改的配置有关，因为 elasticsearch 在启动的时候会进行一些检查，比如最多打开的文件的个数以及虚拟内存 区域数量等等，如果你放开了此配置，意味着需要打开更多的文件以及虚拟内存，所以 我们还需要系统调优。
 *          9.系统调优
 *              我们一共需要修改两处 修改/etc/security/limits.conf  ，追加内容
 *                  * soft nofile 65536 
 *                  * hard nofile 65536
 *              nofile是单个进程允许打开的最大文件个数  soft nofile 是软限制  hard nofile是硬限制
 *              修改/etc/sysctl.conf，追加内容
 *                  vm.max_map_count=655360     限制一个进程可以拥有的VMA(虚拟内存区域)的数量
 *              执行下面命令    修改内核参数马上生效
 *                  sysctl ‐p
 *          重新启动虚拟机，再次启动容器，发现已经可以启动并远程访问
 */
@SpringBootApplication
public class SpringbootElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootElasticsearchApplication.class, args);
    }

}
