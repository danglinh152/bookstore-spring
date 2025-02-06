package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
