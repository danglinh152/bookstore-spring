package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.DAO.FeedbackRepository;
import com.danglinh.project_bookstore.entity.Feedback;
import com.danglinh.project_bookstore.security.Endpoints;
import com.danglinh.project_bookstore.security.FeedbackRequest;
import com.danglinh.project_bookstore.service.FeedbackServiceImpl;
import com.danglinh.project_bookstore.service.JwtServiceImpl;
import com.danglinh.project_bookstore.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@CrossOrigin(origins = Endpoints.front_end_port)
public class FeedbackController {
    private FeedbackServiceImpl feedbackService;

    @Autowired
    public FeedbackController(FeedbackServiceImpl feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/givefeedback")
    public ResponseEntity<?> AddFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        ResponseEntity<?> response = feedbackService.AddFeedback(feedbackRequest);
        return response;
    }
}
