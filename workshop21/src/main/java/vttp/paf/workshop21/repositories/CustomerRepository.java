package vttp.paf.workshop21.repositories;

import static vttp.paf.workshop21.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop21.models.Customer;
import vttp.paf.workshop21.models.Order;

@Repository
public class CustomerRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Customer> findAllCustomers(Integer limit, Integer offset) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_ALL_CUSTOMERS,
      limit,
      offset
    );

    final List<Customer> customers = new LinkedList<>();
    while (rs.next()) {
      customers.add(Customer.create(rs));
    }

    return customers;
  }

  public Customer findOneCustomer(Integer customerId) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_CUSTOMER_BY_ID,
      customerId
    );

    rs.next();
    Customer c = Customer.create(rs);
    return c;
  }

  public List<Order> findAllOrdersByCustomer(Integer customerId) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_ORDERS_BY_CUSTOMER_ID,
      customerId
    );

    final List<Order> orders = new LinkedList<>();
    while (rs.next()) {
      orders.add(Order.create(rs));
    }

    return orders;
  }
}
