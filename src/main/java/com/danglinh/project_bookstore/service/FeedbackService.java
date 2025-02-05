package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.entity.Feedback;
import com.danglinh.project_bookstore.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback findFeedbackById(int id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if (feedback.isPresent()) {
            return feedback.get();
        }
        return null;
    }

    public List<Feedback> findAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (feedbacks.isEmpty()) {
            return null;
        }
        return feedbacks;
    }

    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Boolean deleteFeedback(int id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if (feedback.isPresent()) {
            feedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
