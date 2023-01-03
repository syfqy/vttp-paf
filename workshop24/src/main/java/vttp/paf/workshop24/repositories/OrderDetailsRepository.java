package vttp.paf.workshop24.repositories;

import static vttp.paf.workshop24.repositories.Queries.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop24.models.OrderDetail;

@Repository
public class OrderDetailsRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public boolean addOrderDetails(
    List<OrderDetail> orderDetails,
    String orderId
  ) {
    List<Object[]> data = orderDetails
      .stream()
      .map(od -> {
        Object[] d = new Object[5];
        d[0] = orderId;
        d[1] = od.getProduct();
        d[2] = od.getUnitPrice();
        d[3] = od.getDiscount();
        d[4] = od.getQuantity();
        return d;
      })
      .toList();

    // Batch update
    return jdbcTemplate.batchUpdate(SQL_INSERT_INTO_ORDER_DETAILS, data)[0] > 0;
  }
}
