package com.danglinh.project_bookstore.DAO;

import com.danglinh.project_bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;


@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    Page<Book> findByListOfGenre_GenreId(@RequestParam("id") int genreId, Pageable pageable);

    Page<Book> findByTitleContainingAndListOfGenre_GenreId(@RequestParam("title") String title, @RequestParam("id") int genreId, Pageable pageable);

    public Book findById(int id);
}
