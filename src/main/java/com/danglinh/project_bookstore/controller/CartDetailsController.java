package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.CartDetails;
import com.danglinh.project_bookstore.service.CartDetailsService;
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
public class CartDetailsController {
    private CartDetailsService cartDetailsService;

    public CartDetailsController(CartDetailsService cartDetailsService) {
        this.cartDetailsService = cartDetailsService;
    }

    @GetMapping("/cart-details")
    @ApiMessage("Fetch All CartDetails")
    public ResponseEntity<ResponsePaginationDTO> getAllCartDetailss(
            @Filter Specification<CartDetails> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(cartDetailsService.findAllCartDetails(spec, pageable));
    }

    @GetMapping("/cart-details/{id}")
    @ApiMessage("Fetch A CartDetails with Id")
    public ResponseEntity<CartDetails> getCartDetailsById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (cartDetailsService.findCartDetailsById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDetailsService.findCartDetailsById(id));
    }

    @PostMapping("/cart-details")
    @ApiMessage("Create A CartDetails")
    public ResponseEntity<CartDetails> createCartDetails(@RequestBody CartDetails cartDetails) {
        if (cartDetailsService.addCartDetails(cartDetails) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDetails);
    }

    @PutMapping("/cart-details")
    @ApiMessage("Update A CartDetails")
    public ResponseEntity<CartDetails> updateCartDetails(@RequestBody CartDetails cartDetails) {
        if (cartDetailsService.updateCartDetails(cartDetails) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(cartDetails);
    }

    @DeleteMapping("/cart-details/{id}")
    @ApiMessage("Delete A CartDetails with Id")
    public ResponseEntity<String> deleteCartDetails(@PathVariable int id) {
        if (cartDetailsService.deleteCartDetails(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("CartDetails deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
