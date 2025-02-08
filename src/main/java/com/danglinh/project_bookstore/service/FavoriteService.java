package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Favorite;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.BookRepository;
import com.danglinh.project_bookstore.repository.FavoriteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    private FavoriteRepository favoriteRepository;
    private BookRepository bookRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, BookRepository bookRepository) {
        this.favoriteRepository = favoriteRepository;
        this.bookRepository = bookRepository;
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
        // Lấy Book từ Image
        Book book = favorite.getBook();

        // Kiểm tra nếu Book là thực thể đã được quản lý
        if (book != null) {
            // Tải Book từ cơ sở dữ liệu để đảm bảo nó là thực thể đang quản lý
            book = bookRepository.findById(book.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        }
        // Gán lại Book cho Image
        favorite.setBook(book);

        // Lưu Image vào cơ sở dữ liệu
        return favoriteRepository.save(favorite);
    }

    public Favorite updateFavorite(Favorite favorite) {
        // Lấy Book từ Image
        Book book = favorite.getBook();

        // Kiểm tra nếu Book là thực thể đã được quản lý
        if (book != null) {
            // Tải Book từ cơ sở dữ liệu để đảm bảo nó là thực thể đang quản lý
            book = bookRepository.findById(book.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        }
        // Gán lại Book cho Image
        favorite.setBook(book);

        // Lưu Image vào cơ sở dữ liệu
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
