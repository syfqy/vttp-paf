package vttp.paf.workshop23.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.paf.workshop23.models.Order;
import vttp.paf.workshop23.repositories.OrderRepository;
import vttp.paf.workshop23.repositories.exceptions.OrderNotFoundException;

@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepo;

  public Order getOrderSummary(Integer orderId) throws OrderNotFoundException {
    return orderRepo.findOrderSummaryById(orderId);
  }
}
