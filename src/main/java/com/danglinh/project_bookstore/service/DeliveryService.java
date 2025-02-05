package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.entity.Delivery;
import com.danglinh.project_bookstore.repository.DeliveryRepository;
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
        if (delivery.isPresent()) {
            return delivery.get();
        }
        return null;
    }

    public List<Delivery> findAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        if (deliveries.isEmpty()) {
            return null;
        }
        return deliveries;
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
