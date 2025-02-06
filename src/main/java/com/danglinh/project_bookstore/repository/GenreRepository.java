package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
