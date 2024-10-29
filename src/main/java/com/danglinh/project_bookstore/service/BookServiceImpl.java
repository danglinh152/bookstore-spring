package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.DAO.BookRepository;
import com.danglinh.project_bookstore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findById(int id) {
        return bookRepository.findById(id);
    }
}
