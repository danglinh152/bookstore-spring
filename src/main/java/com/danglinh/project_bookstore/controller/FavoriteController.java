package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Favorite;
import com.danglinh.project_bookstore.service.FavoriteService;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class FavoriteController {
    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/favorites")
    @ApiMessage("Fetch All Favorites")
    public ResponseEntity<ResponsePaginationDTO> getAllFavorites(
            @Filter Specification<Favorite> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(favoriteService.findAllFavorites(spec, pageable));
    }

    @GetMapping("/favorites/{id}")
    @ApiMessage("Fetch A Favorite with Id")
    public ResponseEntity<Favorite> getFavoriteById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (favoriteService.findFavoriteById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(favoriteService.findFavoriteById(id));
    }

    @PostMapping("/favorites")
    @ApiMessage("Create A Favorite")
    public ResponseEntity<Favorite> createFavorite(@Valid @RequestBody Favorite favorite) {
        if (favoriteService.addFavorite(favorite) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
    }

    @PutMapping("/favorites")
    @ApiMessage("Update A Favorite")
    public ResponseEntity<Favorite> updateFavorite(@Valid @RequestBody Favorite favorite) {
        if (favoriteService.updateFavorite(favorite) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(favorite);
    }

    @DeleteMapping("/favorites/{id}")
    @ApiMessage("Delete A Favorite with Id")
    public ResponseEntity<String> deleteFavorite(@PathVariable int id) {
        if (favoriteService.deleteFavorite(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Favorite deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
