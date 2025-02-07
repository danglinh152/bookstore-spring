package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Delivery;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.DeliveryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {
    private DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery findDeliveryById(int id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        return delivery.orElse(null);
    }

    public ResponsePaginationDTO findAllDeliveries(Specification<Delivery> spec, Pageable pageable) {
        Page<Delivery> pageDelivery = deliveryRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageDelivery.getTotalElements());
        meta.setTotalPages(pageDelivery.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageDelivery.getContent());

        return responsePaginationDTO;
    }

    public Delivery addDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Delivery updateDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Boolean deleteDelivery(int id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            deliveryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
