package vttp.paf.workshop22.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Rsvp {

  private Integer id;
  private String name;
  private String email;
  private String phone;
  private String confirmationDate;
  private String comments;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getConfirmationDate() {
    return confirmationDate;
  }

  public void setConfirmationDate(String confirmationDate) {
    this.confirmationDate = confirmationDate;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public static Rsvp create(SqlRowSet rs) {
    Rsvp r = new Rsvp();
    r.setId(rs.getInt("id"));
    r.setName(rs.getString("name"));
    r.setEmail(rs.getString("email"));
    r.setPhone(rs.getString("phone"));
    r.setConfirmationDate(rs.getString("confirmation_date"));
    r.setComments(rs.getString("comments"));
    return r;
  }

  public JsonObject toJson() {
    return Json
      .createObjectBuilder()
      .add("id", getId())
      .add("name", getName())
      .add("email", getEmail())
      .add("phone", getPhone())
      .add("confirmation_date", getConfirmationDate())
      .add("comments", getComments())
      .build();
  }
}
