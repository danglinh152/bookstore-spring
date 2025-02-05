package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.entity.Genre;
import com.danglinh.project_bookstore.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private GenreRepository GenreRepository;

    public GenreService(GenreRepository GenreRepository) {
        this.GenreRepository = GenreRepository;
    }

    public Genre findGenreById(int id) {
        Optional<Genre> Genre = GenreRepository.findById(id);
        if (Genre.isPresent()) {
            return Genre.get();
        }
        return null;
    }

    public List<Genre> findAllGenres() {
        List<Genre> genres = GenreRepository.findAll();
        if (genres.isEmpty()) {
            return null;
        }
        return genres;
    }

    public Genre addGenre(Genre Genre) {
        return GenreRepository.save(Genre);
    }

    public Genre updateGenre(Genre Genre) {
        return GenreRepository.save(Genre);
    }

    public Boolean deleteGenre(int id) {
        Optional<Genre> Genre = GenreRepository.findById(id);
        if (Genre.isPresent()) {
            GenreRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
