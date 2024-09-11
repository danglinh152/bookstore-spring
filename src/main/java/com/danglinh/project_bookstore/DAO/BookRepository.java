package com.danglinh.project_bookstore.DAO;

import com.danglinh.project_bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Integer> {

}
