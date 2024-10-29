package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.DAO.FeedbackRepository;
import com.danglinh.project_bookstore.entity.Book;
import com.danglinh.project_bookstore.entity.Feedback;
import com.danglinh.project_bookstore.entity.Message;
import com.danglinh.project_bookstore.entity.User;
import com.danglinh.project_bookstore.security.FeedbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private FeedbackRepository feedbackRepository;


    @Override
    public ResponseEntity<?> AddFeedback(FeedbackRequest feedbackRequest) {
        try {
            String token = feedbackRequest.getToken();
            int bookId = feedbackRequest.getBookId();

            // Log the incoming request data
            System.out.println("Received feedback request: " + feedbackRequest);

            // Validate JWT token
            if (jwtService.validateJwtToken(token, userService.loadUserByUsername(jwtService.extractUserNameFromJwtToken(token)))) {
                User user = userService.findByUsername(jwtService.extractUserNameFromJwtToken(token));
                Book book = bookService.findById(bookId);

                // Check if user and book are found
                if (user == null || book == null) {
                    return ResponseEntity.badRequest().body(new Message("User or Book not found"));
                }

                Feedback newFeedback = new Feedback();
                newFeedback.setFeedback(feedbackRequest.getFeedback());
                newFeedback.setRate(feedbackRequest.getRate());
                newFeedback.setUser(user);
                newFeedback.setBook(book);

                // Save feedback to the database (assuming there's a save method)
                feedbackRepository.save(newFeedback);

                return ResponseEntity.ok(newFeedback);
            } else {
                return ResponseEntity.badRequest().body(new Message("Invalid token"));
            }

        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Message("Failed to give feedback: " + e.getMessage()));
        }
    }

}
