package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Favorite;
import com.danglinh.project_bookstore.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(path = "favorites")
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    public Favorite findByUserAndBook(@RequestParam User user,@RequestParam Book book);
}
