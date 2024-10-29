package com.danglinh.project_bookstore.controller;

import com.danglinh.project_bookstore.entity.Favorite;
import com.danglinh.project_bookstore.security.Endpoints;
import com.danglinh.project_bookstore.security.FavoriteRequest;
import com.danglinh.project_bookstore.security.FeedbackRequest;
import com.danglinh.project_bookstore.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = Endpoints.front_end_port)
public class BookController {
    private final BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/feedback/givefeedback")
    public ResponseEntity<?> AddFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        ResponseEntity<?> response = bookService.AddFeedback(feedbackRequest);
        return response;
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> isFavorite(@RequestParam int userid) {
        ResponseEntity<?> response = bookService.isFavorite(userid);
        return response;
    }

    @PostMapping("/favorite")
    public ResponseEntity<?> AddFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        ResponseEntity<?> response = bookService.AddFavorite(favoriteRequest);
        return response;
    }

    @DeleteMapping("/favorite")
    public ResponseEntity<?> RemoveFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        ResponseEntity<?> response = bookService.RemoveFavorite(favoriteRequest);
        return response;
    }

}