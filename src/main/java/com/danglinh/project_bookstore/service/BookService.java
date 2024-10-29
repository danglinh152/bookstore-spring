package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.entity.Book;
import com.danglinh.project_bookstore.entity.Favorite;
import com.danglinh.project_bookstore.security.FavoriteRequest;
import com.danglinh.project_bookstore.security.FeedbackRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookService {
    public ResponseEntity<?> AddFeedback(@RequestBody FeedbackRequest feedbackRequest);

    public ResponseEntity<?> isFavorite(@RequestBody int userId);

    public ResponseEntity<?> AddFavorite(@RequestBody FavoriteRequest favoriteRequest);

    public ResponseEntity<?> RemoveFavorite(@RequestBody FavoriteRequest favoriteRequest);

}
