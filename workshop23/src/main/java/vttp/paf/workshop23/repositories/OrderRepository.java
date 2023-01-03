package vttp.paf.workshop23.repositories;

import static vttp.paf.workshop23.repositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop23.models.Order;
import vttp.paf.workshop23.repositories.exceptions.OrderNotFoundException;

@Repository
public class OrderRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public Order findOrderSummaryById(Integer orderId)
    throws OrderNotFoundException {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_ORDER_SUMMARY_BY_ID,
      orderId
    );
    if (!rs.next()) {
      System.out.println("Cannot find order summary");
      throw new OrderNotFoundException(
        "Cannot find order summary for order " + orderId
      );
    }

    Order order = Order.create(rs);
    return order;
  }
}
