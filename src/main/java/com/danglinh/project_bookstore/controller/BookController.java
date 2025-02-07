package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.BookService;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
    @ApiMessage("Fetch All Books")
    public ResponseEntity<ResponsePaginationDTO> getAllBooks(
            @Filter Specification<Book> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.findAllBooks(spec, pageable));
    }

    @GetMapping("/books/{id}")
    @ApiMessage("Fetch A Book with Id")
    public ResponseEntity<Book> getBookById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (bookService.findBookById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @PostMapping("/books")
    @ApiMessage("Create A Book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        if (bookService.addBook(book) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/books")
    @ApiMessage("Update A Book")
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) {
        if (bookService.updateBook(book) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/books/{id}")
    @ApiMessage("Delete A Book with Id")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
