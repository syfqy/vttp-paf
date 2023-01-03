package vttp.paf.workshop21.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.paf.workshop21.models.Customer;
import vttp.paf.workshop21.models.Order;
import vttp.paf.workshop21.repositories.CustomerRepository;

@Service
public class CustomerService {

  @Autowired
  CustomerRepository customerRepo;

  public List<Customer> getAllCustomers(Integer limit, Integer offset) {
    return customerRepo.findAllCustomers(limit, offset);
  }

  public Customer getCustomerById(Integer customerId) {
    return customerRepo.findOneCustomer(customerId);
  }

  public List<Order> getOrdersByCustomer(Integer customerId) {
    return customerRepo.findAllOrdersByCustomer(customerId);
  }
}
