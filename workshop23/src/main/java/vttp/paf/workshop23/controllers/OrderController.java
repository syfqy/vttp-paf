package vttp.paf.workshop23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.paf.workshop23.models.Order;
import vttp.paf.workshop23.repositories.exceptions.OrderNotFoundException;
import vttp.paf.workshop23.services.OrderService;

@Controller
public class OrderController {

  @Autowired
  OrderService orderSvc;

  @GetMapping("/")
  public String showSearchOrderPage() {
    return "search";
  }

  @GetMapping("order/total/")
  public String getOrderSummary(@RequestParam String orderId, Model model) {
    Integer id = Integer.parseInt(orderId);

    try {
      Order order = orderSvc.getOrderSummary(id);
      model.addAttribute("order", order);
      return "orderSummary";
    } catch (OrderNotFoundException e) {
      return "error";
    }
  }
}
