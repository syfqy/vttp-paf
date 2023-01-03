package vttp.paf.workshop24.models;

import java.util.List;

public class Order {

  private String orderId;
  private String orderDate;
  private String customerName;
  private String shipAddress;
  private String notes;
  private Double tax;
  private List<OrderDetail> orderDetails;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getShipAddress() {
    return shipAddress;
  }

  public void setShipAddress(String shipAddress) {
    this.shipAddress = shipAddress;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Double getTax() {
    return tax;
  }

  public void setTax(Double tax) {
    this.tax = tax;
  }

  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<OrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }
}
