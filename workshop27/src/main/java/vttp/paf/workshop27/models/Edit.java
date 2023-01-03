package vttp.paf.workshop27.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.time.LocalDate;
import java.time.ZoneId;
import org.bson.Document;

public class Edit {

  private String comment;
  private Integer rating;
  private LocalDate posted;

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public LocalDate getPosted() {
    return posted;
  }

  public void setPosted(LocalDate posted) {
    this.posted = posted;
  }

  public Document toDoc() {
    Document doc = new Document();
    doc.put("comment", comment);
    doc.put("rating", rating);
    doc.put("posted", posted);
    return doc;
  }

  public static Edit create(Document doc) {
    Edit edit = new Edit();
    edit.setComment(doc.getString("comment"));
    edit.setPosted(
      doc
        .getDate("posted")
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    );
    edit.setRating(doc.getInteger("rating"));
    return edit;
  }

  public JsonObject toJson() {
    JsonObject json = Json
      .createObjectBuilder()
      .add("comment", comment)
      .add("rating", rating)
      .add("posted", posted.toString())
      .build();
    return json;
  }
}
