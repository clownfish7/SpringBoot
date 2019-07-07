package com.clownfish7.springbootelasticsearch.repository;

import com.clownfish7.springbootelasticsearch.pojo.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author You
 * @create 2019-07-07 20:34
 */
public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
    //ctrl + f12 查看方法

    //参考    https://docs.spring.io/spring-data/elasticsearch/docs/3.1.9.RELEASE/reference/html/#elasticsearch.repositories
    public List<Book> findBookByBookNameList(String bookName);
}
