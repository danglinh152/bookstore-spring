package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.request.AddToCartDTO;
import com.danglinh.project_bookstore.domain.DTO.request.CheckOutDTO;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Cart;
import com.danglinh.project_bookstore.domain.entity.CartDetails;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.BookService;
import com.danglinh.project_bookstore.service.CartDetailsService;
import com.danglinh.project_bookstore.service.CartService;
import com.danglinh.project_bookstore.service.UserService;
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
    private final UserService userService;
    private final CartDetailsService cartDetailsService;
    private final BookService bookService;
    private CartService cartService;

    public CartController(CartService cartService, UserService userService, CartDetailsService cartDetailsService, BookService bookService) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartDetailsService = cartDetailsService;
        this.bookService = bookService;
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
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        if (cartService.addCart(cart) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/carts")
    @ApiMessage("Update A Cart")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
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

    @PostMapping("/add-to-cart")
    @ApiMessage("Add To Cart")
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartDTO addToCartDTO) throws IdInvalidException {
        User user = userService.findUserById(addToCartDTO.getUserId());
        Cart cart = cartService.findByUser(user);

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Book book = bookService.findBookById(addToCartDTO.getBookId());

            if (book == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            CartDetails cartDetails = cartDetailsService.findByCartAndBook(cart, book);

            if (cartDetails == null) {
                // Create new cart details
                cartDetails = new CartDetails();
                cartDetails.setCart(cart);
                cartDetails.setBook(book);
                cartDetails.setQuantity(1); // Set initial quantity to 1
                cartDetailsService.addCartDetails(cartDetails);
            } else {
                // Update existing cart details
                cartDetails.setQuantity(cartDetails.getQuantity() + 1);
                cartDetailsService.updateCartDetails(cartDetails);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @PostMapping("/check-out")
    @ApiMessage("Check Out")
    public ResponseEntity<String> checkOut(@RequestBody CheckOutDTO checkOutDTO) throws IdInvalidException {
        User user = userService.findUserById(checkOutDTO.getUserId());
        Cart cart = cartService.findByUser(user);
        Book book = bookService.findBookById(checkOutDTO.getBookId());
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        CartDetails cartDetails = cartDetailsService.findByCartAndBook(cart, book);
        if (cartDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chưa có sản phẩm trong giỏ hàng");
        } else {
            // Update existing cart details
            if (cartDetails.getQuantity() - checkOutDTO.getQuantity() < 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Số lượng thanh toán nhiều hơn trong giỏ hàng");
            }
            cartDetails.setQuantity(cartDetails.getQuantity() - checkOutDTO.getQuantity());
            if (cartDetails.getQuantity() == 0) {
                cartDetailsService.deleteCartDetails(cartDetails.getCartDetailsId());
            } else {
                cartDetailsService.updateCartDetails(cartDetails);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Thanh Toán thành công!");
    }


}
