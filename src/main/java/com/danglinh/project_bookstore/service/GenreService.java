package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Genre;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findGenreById(int id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElse(null);
    }

    public ResponsePaginationDTO findAllGenres(Specification<Genre> spec, Pageable pageable) {
        Page<Genre> pageGenre = genreRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageGenre.getTotalElements());
        meta.setTotalPages(pageGenre.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageGenre.getContent());

        return responsePaginationDTO;
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Boolean deleteGenre(int id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            genreRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
