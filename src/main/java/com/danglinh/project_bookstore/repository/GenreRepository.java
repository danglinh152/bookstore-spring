package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
