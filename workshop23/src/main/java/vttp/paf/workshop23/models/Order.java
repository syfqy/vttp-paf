package vttp.paf.workshop23.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Order {

  private Integer orderId;
  private String orderDate;
  private String customerId;
  private Double total;
  private Double cost;

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public static Order create(SqlRowSet rs) {
    Order o = new Order();
    o.setOrderId(rs.getInt("order_id"));
    o.setOrderDate(rs.getString("order_date"));
    o.setCustomerId(rs.getString("customer_id"));
    o.setTotal(rs.getDouble("total"));
    o.setCost(rs.getDouble("cost_price"));

    return o;
  }
}
