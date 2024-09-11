package com.danglinh.project_bookstore.DAO;

import com.danglinh.project_bookstore.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "feedbacks")
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
