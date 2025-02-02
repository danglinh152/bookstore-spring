package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.RestResponse;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.service.BookService;
import com.danglinh.project_bookstore.service.error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999){
            throw new IdInvalidException("Id more than 9999");
        }
        return bookService.findBookById(id);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/books")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id);
    }

}
