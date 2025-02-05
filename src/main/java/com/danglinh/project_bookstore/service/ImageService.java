package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.entity.Image;
import com.danglinh.project_bookstore.domain.entity.Image;
import com.danglinh.project_bookstore.repository.ImageRepository;
import com.danglinh.project_bookstore.repository.ImageRepository;
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
        if (image.isPresent()) {
            return image.get();
        }
        return null;
    }

    public List<Image> findAllImages() {
        List<Image> images = imageRepository.findAll();
        if (images.isEmpty()) {
            return null;
        }
        return images;
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
