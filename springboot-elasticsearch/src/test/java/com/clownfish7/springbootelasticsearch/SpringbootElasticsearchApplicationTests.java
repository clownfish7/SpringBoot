package com.clownfish7.springbootelasticsearch;

import com.clownfish7.springbootelasticsearch.pojo.Article;
import com.clownfish7.springbootelasticsearch.pojo.Book;
import com.clownfish7.springbootelasticsearch.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private BookRepository bookRepository;

    /**
     * BookRepository 插入
     */
    @Test
    public void inesrt() {
        Book book = new Book();
        book.setId(1);
        book.setAuthor("红楼梦");
        book.setBookName("曹雪芹");
//        bookRepository.index(book);

        List<Book> bookList = bookRepository.findBookByBookNameList("雪");
        System.out.println(bookList.get(0));
    }

    /**
     * 插入
     * @throws IOException
     */
    @Test
    public void contextLoads() throws IOException {

        //1.给Es中索引保存一个文档
        Article article = new Article();
        article.setId(1);
        article.setAuthor("这是作者");
        article.setTitle("这是标题");
        article.setContent("这是内容");

        //构建一个索引内容
        Index index = new Index.Builder(article).index("clownfish7").type("news").build();

        //执行
        jestClient.execute(index);
    }

    /**
     * 搜索
     */
    @Test
    public void search() throws IOException {
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"title\" : \"这是标题\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search search = new Search.Builder(json).addIndex("clownfish7").addType("news").build();

        SearchResult searchResult = jestClient.execute(search);

        System.out.println(searchResult.getJsonString());

    }

}
