package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Image;
import com.danglinh.project_bookstore.service.ImageService;
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
public class ImageController {
    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    @ApiMessage("Fetch All Images")
    public ResponseEntity<ResponsePaginationDTO> getAllImages(
            @Filter Specification<Image> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(imageService.findAllImages(spec, pageable));
    }

    @GetMapping("/images/{id}")
    @ApiMessage("Fetch A Image with Id")
    public ResponseEntity<Image> getImageById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (imageService.findImageById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageService.findImageById(id));
    }

    @PostMapping("/images")
    @ApiMessage("Create A Image")
    public ResponseEntity<Image> createImage(@Valid @RequestBody Image image) {
        if (imageService.addImage(image) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    @PutMapping("/images")
    @ApiMessage("Update A Image")
    public ResponseEntity<Image> updateImage(@Valid @RequestBody Image image) {
        if (imageService.updateImage(image) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @DeleteMapping("/images/{id}")
    @ApiMessage("Delete A Image with Id")
    public ResponseEntity<String> deleteImage(@PathVariable int id) {
        if (imageService.deleteImage(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Image deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
