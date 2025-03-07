package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Cart;
import com.danglinh.project_bookstore.service.CartService;
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
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    @ApiMessage("Fetch All Carts")
    public ResponseEntity<ResponsePaginationDTO> getAllCarts(
            @Filter Specification<Cart> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(cartService.findAllCarts(spec, pageable));
    }

    @GetMapping("/carts/{id}")
    @ApiMessage("Fetch A Cart with Id")
    public ResponseEntity<Cart> getCartById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (cartService.findCartById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartService.findCartById(id));
    }

    @PostMapping("/carts")
    @ApiMessage("Create A Cart")
    public ResponseEntity<Cart> createCart(@Valid @RequestBody Cart cart) {
        if (cartService.addCart(cart) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/carts")
    @ApiMessage("Update A Cart")
    public ResponseEntity<Cart> updateCart(@Valid @RequestBody Cart cart) {
        if (cartService.updateCart(cart) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @DeleteMapping("/carts/{id}")
    @ApiMessage("Delete A Cart with Id")
    public ResponseEntity<String> deleteCart(@PathVariable int id) {
        if (cartService.deleteCart(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Cart deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
