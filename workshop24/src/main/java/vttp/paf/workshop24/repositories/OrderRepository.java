package vttp.paf.workshop24.repositories;

import static vttp.paf.workshop24.repositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop24.models.Order;

@Repository
public class OrderRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public boolean createOrder(Order order) {
    return (
      jdbcTemplate.update(
        SQL_INSERT_INTO_ORDER,
        order.getOrderDate(),
        order.getCustomerName(),
        order.getShipAddress(),
        order.getNotes(),
        order.getTax()
      ) >
      0
    );
  }

  public String getLastOrderId() {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_LATEST_ORDER_ID
    );
    rs.next();

    return rs.getString("last_order_id");
  }
}
