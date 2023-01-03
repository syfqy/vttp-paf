package vttp.paf.workshop24.controllers;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vttp.paf.workshop24.models.Order;
import vttp.paf.workshop24.models.OrderDetail;
import vttp.paf.workshop24.services.OrderService;
import vttp.paf.workshop24.services.exceptions.OrderException;

@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  OrderService orderSvc;

  @PostMapping
  public String postOrder(
    @RequestBody MultiValueMap<String, String> form,
    Model model
  ) {
    // get order attributes
    String orderDate = form.getFirst("orderDate");
    String customerName = form.getFirst("customerName");
    String shipAddress = form.getFirst("shipAddress");
    String notes = form.getFirst("notes");
    Double tax = Double.parseDouble(form.getFirst("tax"));

    Order order = new Order();
    order.setOrderDate(orderDate);
    order.setCustomerName(customerName);
    order.setShipAddress(shipAddress);
    order.setNotes(notes);
    order.setTax(tax);

    // get order details
    String product = form.getFirst("product");
    Double unitPrice = Double.parseDouble(form.getFirst("unitPrice"));
    Double discount = Double.parseDouble(form.getFirst("discount"));
    Integer quantity = Integer.parseInt(form.getFirst("quantity"));

    OrderDetail orderDetails = new OrderDetail();
    orderDetails.setProduct(product);
    orderDetails.setUnitPrice(unitPrice);
    orderDetails.setDiscount(discount);
    orderDetails.setQuantity(quantity);

    // insert order details into order
    List<OrderDetail> orderDetailList = new LinkedList<>();
    orderDetailList.add(orderDetails);
    order.setOrderDetails(orderDetailList);

    // create order
    try {
      orderSvc.createOrder(order);
    } catch (OrderException e) {
      // TODO Auto-generated catch block
      return "error";
    }

    return "created";
  }
}
