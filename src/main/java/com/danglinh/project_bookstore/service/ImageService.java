package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Image;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.ImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image findImageById(int id) {
        Optional<Image> image = imageRepository.findById(id);
        return image.orElse(null);
    }

    public ResponsePaginationDTO findAllImages(Specification<Image> spec, Pageable pageable) {
        Page<Image> pageImage = imageRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageImage.getTotalElements());
        meta.setTotalPages(pageImage.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageImage.getContent());

        return responsePaginationDTO;
    }

    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    public Image updateImage(Image image) {
        return imageRepository.save(image);
    }

    public Boolean deleteImage(int id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
