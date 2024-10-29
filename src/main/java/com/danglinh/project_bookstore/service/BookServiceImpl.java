package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.DAO.BookRepository;
import com.danglinh.project_bookstore.DAO.FavoriteRepository;
import com.danglinh.project_bookstore.DAO.FeedbackRepository;
import com.danglinh.project_bookstore.DAO.UserRepository;
import com.danglinh.project_bookstore.DTO.BookDTO;
import com.danglinh.project_bookstore.entity.*;
import com.danglinh.project_bookstore.security.FavoriteRequest;
import com.danglinh.project_bookstore.security.FeedbackRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

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
                Book book = bookRepository.findById(bookId);

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

    @Override
    public ResponseEntity<?> isFavorite(int userId) {
        try {
            // Log the incoming request data

            User user = userRepository.findById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
            }

            List<Favorite> favorites = user.getListOfFavorite();
            if (favorites == null) {
                favorites = new ArrayList<>(); // Handle null case
            }

            // Create a list to hold DTO objects
            List<BookDTO> favoriteBookDTOs = new ArrayList<>();

            // Populate the list with books from favorites
            for (Favorite favorite : favorites) {
                Book book = favorite.getBook();
                if (book != null) {
                    favoriteBookDTOs.add(new BookDTO(book.getBookId(), book.getTitle()));
                }
            }


            Map<String, Object> response = new HashMap<>();
            response.put("favoriteBooks", favoriteBookDTOs); // Use DTOs in response

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred"));
        }
    }


    @Override
    public ResponseEntity<?> AddFavorite(FavoriteRequest favoriteRequest) {
        try {
            String token = favoriteRequest.getToken();
            int bookId = favoriteRequest.getBookId();

            // Log the incoming request data
            System.out.println("Received feedback request: " + favoriteRequest);

            // Validate JWT token
            if (jwtService.validateJwtToken(token, userService.loadUserByUsername(jwtService.extractUserNameFromJwtToken(token)))) {
                User user = userService.findByUsername(jwtService.extractUserNameFromJwtToken(token));
                Book book = bookRepository.findById(bookId);

                // Check if user and book are found
                if (user == null || book == null) {
                    return ResponseEntity.badRequest().body(new Message("User or Book not found"));
                }

                Favorite newFavorite = new Favorite();

                newFavorite.setUser(user);
                newFavorite.setBook(book);

                // Save feedback to the database (assuming there's a save method)
                favoriteRepository.save(newFavorite);

                return ResponseEntity.ok(newFavorite);
            } else {
                return ResponseEntity.badRequest().body(new Message("Invalid token"));
            }

        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Message("Failed to give feedback: " + e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> RemoveFavorite(FavoriteRequest favoriteRequest) {
        try {
            String token = favoriteRequest.getToken();
            int bookId = favoriteRequest.getBookId();

            // Log the incoming request data
            System.out.println("Received feedback request: " + favoriteRequest);

            // Validate JWT token
            if (jwtService.validateJwtToken(token, userService.loadUserByUsername(jwtService.extractUserNameFromJwtToken(token)))) {
                User user = userService.findByUsername(jwtService.extractUserNameFromJwtToken(token));
                Book book = bookRepository.findById(bookId);

                // Check if user and book are found
                if (user == null || book == null) {
                    return ResponseEntity.badRequest().body(new Message("User or Book not found"));
                }


                // Save feedback to the database (assuming there's a save method)
                try {
                    Favorite favorite = favoriteRepository.findByUserAndBook(user, book);
                    List<Favorite> listFavorite = user.getListOfFavorite(); // Use List instead of ArrayList
                    listFavorite.remove(favorite);
                    user.setListOfFavorite(listFavorite);

                    if (favorite != null) {
                        favoriteRepository.delete(favorite);
                        entityManager.flush();
//                        entityManager.createQuery("DELETE FROM Favorite f WHERE f.user = :user AND f.book = :book")
//                                .setParameter("user", user)
//                                .setParameter("book", book)
//                                .executeUpdate();
                        return ResponseEntity.ok(new Message("Successfully deleted favorite"));
                    } else {
                        return ResponseEntity.badRequest().body(new Message("Favorite not found"));
                    }

                } catch (Exception e) {
                    return ResponseEntity.badRequest().body(new Message("Failed to delete favorite"));
                }
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
