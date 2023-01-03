package vttp.paf.workshop28.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.bson.Document;

public class Comment {

  private Integer gid;
  private String name;
  private Integer rating;
  private String user;
  private String comment;
  private String commentId;

  public Integer getGid() {
    return gid;
  }

  public void setGid(Integer gid) {
    this.gid = gid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String comemntId) {
    this.commentId = comemntId;
  }

  public static Comment create(Document doc) {
    Comment comment = new Comment();
    comment.setGid(doc.getInteger("gid"));
    comment.setName(doc.getString("name"));
    comment.setRating(doc.getInteger("rating"));
    comment.setUser(doc.getString("user"));
    comment.setComment(doc.getString("comment"));
    comment.setCommentId(doc.getString("comment_id"));
    return comment;
  }

  public JsonObject toJson() {
    JsonObject json = Json
      .createObjectBuilder()
      .add("name", name)
      .add("rating", rating)
      .add("user", user)
      .add("comment", comment)
      .build();
    return json;
  }
}
