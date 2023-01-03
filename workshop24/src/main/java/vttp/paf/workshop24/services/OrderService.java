package vttp.paf.workshop24.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vttp.paf.workshop24.models.Order;
import vttp.paf.workshop24.repositories.OrderDetailsRepository;
import vttp.paf.workshop24.repositories.OrderRepository;
import vttp.paf.workshop24.services.exceptions.OrderException;

@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepo;

  @Autowired
  OrderDetailsRepository orderDetailRepo;

  @Transactional(rollbackFor = OrderException.class)
  public void createOrder(Order order) throws OrderException {
    if (!orderRepo.createOrder(order)) throw new OrderException(
      "cannot create order"
    );

    // get order id
    String orderId = orderRepo.getLastOrderId();

    // add orderDetail
    if (
      !orderDetailRepo.addOrderDetails(order.getOrderDetails(), orderId)
    ) throw new OrderException("cannot add order details");
  }
}
