package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.RestResponse;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<Book> findBookById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    public ResponseEntity<Book> addBook(Book book) {
        bookRepository.save(book);
        return ResponseEntity.status(201).body(book);
    }

    public ResponseEntity<Book> updateBook(Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    public ResponseEntity<String> deleteBook(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.status(200).body("Book deleted");
        }
        return ResponseEntity.notFound().build();
    }

}
