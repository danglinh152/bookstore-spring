package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Favorite;
import com.danglinh.project_bookstore.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.web.bind.annotation.RequestParam;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>, JpaSpecificationExecutor<Favorite> {
    public Favorite findByUserAndBook(@RequestParam User user, @RequestParam Book book);
}
