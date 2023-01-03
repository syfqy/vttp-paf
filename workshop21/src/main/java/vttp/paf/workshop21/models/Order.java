package vttp.paf.workshop21.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Order {

  private Integer id;
  private String orderDate;
  private String shipDate;
  private String shipAddress;
  private String shipCity;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  public String getShipDate() {
    return shipDate;
  }

  public void setShipDate(String shipDate) {
    this.shipDate = shipDate;
  }

  public String getShipAddress() {
    return shipAddress;
  }

  public void setShipAddress(String shipAddress) {
    this.shipAddress = shipAddress;
  }

  public String getShipCity() {
    return shipCity;
  }

  public void setShipCity(String shipCity) {
    this.shipCity = shipCity;
  }

  public static Order create(SqlRowSet rs) {
    Order order = new Order();
    order.setId(rs.getInt("id"));
    order.setOrderDate(rs.getString("order_date"));
    order.setShipDate(rs.getString("shipped_date"));
    order.setShipAddress(rs.getString("ship_address"));
    order.setShipCity(rs.getString("ship_city"));
    return order;
  }

  public JsonObject toJson() {
    return Json
      .createObjectBuilder()
      .add("orderId", id)
      .add("orderDate", orderDate)
      .add("shipDate", shipDate)
      .add("shipAddress", shipAddress)
      .add("shipCity", shipCity)
      .build();
  }
}
