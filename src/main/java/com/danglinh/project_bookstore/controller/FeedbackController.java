package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Feedback;
import com.danglinh.project_bookstore.service.FeedbackService;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class FeedbackController {
    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedbacks")
    @ApiMessage("Fetch All Feedbacks")
    public ResponseEntity<ResponsePaginationDTO> getAllFeedbacks(
            @Filter Specification<Feedback> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(feedbackService.findAllFeedbacks(spec, pageable));
    }

    @GetMapping("/feedbacks/{id}")
    @ApiMessage("Fetch A Feedback with Id")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (feedbackService.findFeedbackById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(feedbackService.findFeedbackById(id));
    }

    @PostMapping("/feedbacks")
    @ApiMessage("Create A Feedback")
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback) {
        if (feedbackService.addFeedback(feedback) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }

    @PutMapping("/feedbacks")
    @ApiMessage("Update A Feedback")
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback) {
        if (feedbackService.updateFeedback(feedback) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(feedback);
    }

    @DeleteMapping("/feedbacks/{id}")
    @ApiMessage("Delete A Feedback with Id")
    public ResponseEntity<String> deleteFeedback(@PathVariable int id) {
        if (feedbackService.deleteFeedback(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Feedback deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
