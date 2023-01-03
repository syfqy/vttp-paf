package vttp.paf.workshop21.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Customer {

  private String id;
  private String firstName;
  private String lastName;
  private String contactNo;
  private String company;
  private String address;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getContactNo() {
    return contactNo;
  }

  public void setContactNo(String contactNo) {
    this.contactNo = contactNo;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public static Customer create(SqlRowSet rs) {
    Customer c = new Customer();
    c.setId(rs.getString("id"));
    c.setFirstName(rs.getString("first_name"));
    c.setLastName(rs.getString("last_name"));
    c.setContactNo(rs.getString("business_phone"));
    c.setCompany(rs.getString("company"));
    c.setAddress(rs.getString("address"));
    return c;
  }

  public JsonObject toJson() {
    return Json
      .createObjectBuilder()
      .add("customerId", getId())
      .add("firstName", getFirstName())
      .add("lastName", getLastName())
      .add("contactNo", getContactNo())
      .add("company", getCompany())
      .add("address", getAddress())
      .build();
  }
}
