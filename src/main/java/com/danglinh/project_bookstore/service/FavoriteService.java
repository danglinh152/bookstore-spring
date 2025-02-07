package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Favorite;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.FavoriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    private FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Favorite findFavoriteById(int id) {
        Optional<Favorite> favorite = favoriteRepository.findById(id);
        return favorite.orElse(null);
    }

    public ResponsePaginationDTO findAllFavorites(Specification<Favorite> spec, Pageable pageable) {
        Page<Favorite> pageFavorite = favoriteRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageFavorite.getTotalElements());
        meta.setTotalPages(pageFavorite.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageFavorite.getContent());

        return responsePaginationDTO;
    }

    public Favorite addFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public Favorite updateFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public Boolean deleteFavorite(int id) {
        Optional<Favorite> favorite = favoriteRepository.findById(id);
        if (favorite.isPresent()) {
            favoriteRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
