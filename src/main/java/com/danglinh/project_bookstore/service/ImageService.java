package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Image;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.BookRepository;
import com.danglinh.project_bookstore.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private ImageRepository imageRepository;
    private BookRepository bookRepository;

    public ImageService(ImageRepository imageRepository, BookRepository bookRepository) {
        this.imageRepository = imageRepository;
        this.bookRepository = bookRepository;
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
        // Lấy Book từ Image
        Book book = image.getBook();

        // Kiểm tra nếu Book là thực thể đã được quản lý
        if (book != null) {
            // Tải Book từ cơ sở dữ liệu để đảm bảo nó là thực thể đang quản lý
            book = bookRepository.findById(book.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        } else {
            throw new IllegalArgumentException("Book must be provided");
        }

        // Gán lại Book cho Image
        image.setBook(book);

        // Lưu Image vào cơ sở dữ liệu
        return imageRepository.save(image);
    }


    public Image updateImage(Image image) {
        // Lấy Book từ Image
        Book book = image.getBook();

        // Kiểm tra nếu Book là thực thể đã được quản lý
        if (book != null) {
            // Tải Book từ cơ sở dữ liệu để đảm bảo nó là thực thể đang quản lý
            book = bookRepository.findById(book.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        }
        // Gán lại Book cho Image
        image.setBook(book);
        // Lưu Image vào cơ sở dữ liệu
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
