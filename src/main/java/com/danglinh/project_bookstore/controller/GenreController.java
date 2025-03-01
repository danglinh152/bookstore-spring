package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Genre;
import com.danglinh.project_bookstore.service.BookService;
import com.danglinh.project_bookstore.service.GenreService;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class GenreController {
    private final BookService bookService;
    private GenreService genreService;

    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/genres")
    @ApiMessage("Fetch All Genres")
    public ResponseEntity<ResponsePaginationDTO> getAllGenres(
            @Filter Specification<Genre> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(genreService.findAllGenres(spec, pageable));
    }

    @GetMapping("/genres/{id}")
    @ApiMessage("Fetch A Genre with Id")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (genreService.findGenreById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genreService.findGenreById(id));
    }

    @GetMapping("/genres/{id}/books")
    @ApiMessage("Fetch Books with Genre Id")
    public ResponseEntity<List<Book>> getBooksByGenreId(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }

        // Tìm sách theo ID
        Genre genre = genreService.findGenreById(id);
        if (genre == null) {
            return ResponseEntity.notFound().build();
        }

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < genre.getListOfBook().size(); i++) {
            Book book = bookService.findBookById(genre.getListOfBook().get(i).getBookId()); // Changed to get() method
            if (book != null) { // Check if genre is not null
                books.add(book); // Use add() instead of push()
            }
        }

        // Kiểm tra xem danh sách thể loại có tồn tại không
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(books);
    }

    @PostMapping("/genres")
    @ApiMessage("Create A Genre")
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) {
        if (genreService.addGenre(genre) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @PutMapping("/genres")
    @ApiMessage("Update A Genre")
    public ResponseEntity<Genre> updateGenre(@Valid @RequestBody Genre genre) {
        if (genreService.updateGenre(genre) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(genre);
    }

    @DeleteMapping("/genres/{id}")
    @ApiMessage("Delete A Genre with Id")
    public ResponseEntity<String> deleteGenre(@PathVariable int id) {
        if (genreService.deleteGenre(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Genre deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
