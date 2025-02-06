package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
