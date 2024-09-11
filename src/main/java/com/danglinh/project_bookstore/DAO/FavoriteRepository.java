package com.danglinh.project_bookstore.DAO;

import com.danglinh.project_bookstore.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "favorite-books")
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

}
