package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Feedback;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.FeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public ResponsePaginationDTO findAllFeedbacks(Specification<Feedback> spec, Pageable pageable) {
        Page<Feedback> pageFeedback = feedbackRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageFeedback.getTotalElements());
        meta.setTotalPages(pageFeedback.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageFeedback.getContent());

        return responsePaginationDTO;
    }

    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Feedback feedback) {
        Optional<Feedback> currentFeedback = feedbackRepository.findById(feedback.getFeedbackId());
        if (currentFeedback.isPresent()) {
            Feedback existingFeedback = currentFeedback.get();
            existingFeedback.setFeedback(feedback.getFeedback());
            existingFeedback.setRate(feedback.getRate());
            return feedbackRepository.save(existingFeedback);
        }
        return null;
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
