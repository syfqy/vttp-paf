package vttp.paf.workshop21.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vttp.paf.workshop21.models.Customer;
import vttp.paf.workshop21.models.Order;
import vttp.paf.workshop21.services.CustomerService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRESTController {

  @Autowired
  CustomerService customerSvc;

  @GetMapping("/customers")
  public ResponseEntity<String> getAllCustomers(
    @RequestParam(defaultValue = "10") Integer limit,
    Integer offset
  ) {
    List<Customer> customers = customerSvc.getAllCustomers(limit, offset);
    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    for (Customer c : customers) {
      arrBuilder.add(c.toJson());
    }
    JsonArray result = arrBuilder.build();

    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(result.toString());
  }

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<String> getCustomerById(
    @PathVariable Integer customerId
  ) {
    Customer result = customerSvc.getCustomerById(customerId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType((MediaType.APPLICATION_JSON))
      .body(result.toJson().toString());
  }

  @GetMapping("/customer/{customerId}/orders")
  public ResponseEntity<String> getOrdersByCustomerId(
    @PathVariable Integer customerId
  ) {
    List<Order> orders = customerSvc.getOrdersByCustomer(customerId);

    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    for (Order o : orders) {
      arrBuilder.add(o.toJson());
    }
    JsonArray result = arrBuilder.build();

    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(result.toString());
  }
}
