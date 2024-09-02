package com.danglinh.project_bookstore;

import com.danglinh.project_bookstore.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectBookstoreApplicationTests {

    @Test
    void contextLoads() {
        Book book = new Book();
        book.setTitle("cc");
        System.out.println(book.getTitle());
    }

}
