package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.CartDetails;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.CartDetailsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailsService {
    private CartDetailsRepository cartDetailsRepository;

    public CartDetailsService(CartDetailsRepository cartDetailsRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
    }

    public CartDetails findCartDetailsById(int id) {
        Optional<CartDetails> cartDetails = cartDetailsRepository.findById(id);
        return cartDetails.orElse(null);
    }

    public ResponsePaginationDTO findAllCartDetails(Specification<CartDetails> spec, Pageable pageable) {
        Page<CartDetails> pageCartDetails = cartDetailsRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageCartDetails.getTotalElements());
        meta.setTotalPages(pageCartDetails.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageCartDetails.getContent());

        return responsePaginationDTO;
    }

    public CartDetails addCartDetails(CartDetails cartDetails) {
        return cartDetailsRepository.save(cartDetails);
    }

    public CartDetails updateCartDetails(CartDetails cartDetails) {
        return cartDetailsRepository.save(cartDetails);
    }

    public Boolean deleteCartDetails(int id) {
        Optional<CartDetails> cartDetails = cartDetailsRepository.findById(id);
        if (cartDetails.isPresent()) {
            cartDetailsRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
