package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Delivery;
import com.danglinh.project_bookstore.service.DeliveryService;
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
public class DeliveryController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/deliveries")
    @ApiMessage("Fetch All Deliveries")
    public ResponseEntity<ResponsePaginationDTO> getAllDeliveries(
            @Filter Specification<Delivery> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(deliveryService.findAllDeliveries(spec, pageable));
    }

    @GetMapping("/deliveries/{id}")
    @ApiMessage("Fetch A Delivery with Id")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (deliveryService.findDeliveryById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deliveryService.findDeliveryById(id));
    }

    @PostMapping("/deliveries")
    @ApiMessage("Create A Delivery")
    public ResponseEntity<Delivery> createDelivery(@Valid @RequestBody Delivery delivery) {
        if (deliveryService.addDelivery(delivery) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
    }

    @PutMapping("/deliveries")
    @ApiMessage("Update A Delivery")
    public ResponseEntity<Delivery> updateDelivery(@Valid @RequestBody Delivery delivery) {
        if (deliveryService.updateDelivery(delivery) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(delivery);
    }

    @DeleteMapping("/deliveries/{id}")
    @ApiMessage("Delete A Delivery with Id")
    public ResponseEntity<String> deleteDelivery(@PathVariable int id) {
        if (deliveryService.deleteDelivery(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Delivery deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
