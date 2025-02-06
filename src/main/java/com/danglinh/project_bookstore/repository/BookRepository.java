package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.web.bind.annotation.RequestParam;


public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    Page<Book> findByListOfGenre_GenreId(@RequestParam("id") int genreId, Pageable pageable);

    Page<Book> findByTitleContainingAndListOfGenre_GenreId(@RequestParam("title") String title, @RequestParam("id") int genreId, Pageable pageable);

}
