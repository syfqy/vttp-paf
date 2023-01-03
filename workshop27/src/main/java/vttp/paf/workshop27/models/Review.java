package vttp.paf.workshop27.models;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;

public class Review {

  private String user;
  private Integer rating;
  private String comment;
  private Integer gid;
  private LocalDate posted;
  private String name;
  private List<Edit> edits;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Integer getGid() {
    return gid;
  }

  public void setGid(Integer gid) {
    this.gid = gid;
  }

  public LocalDate getPosted() {
    return posted;
  }

  public void setPosted(LocalDate posted) {
    this.posted = posted;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Edit> getEdits() {
    return edits;
  }

  public void setEdits(List<Edit> edits) {
    this.edits = edits;
  }

  public void addEdit(Edit edit) {
    if (this.edits == null) this.edits = new LinkedList<Edit>();
    this.edits.add(edit);
  }

  private Edit getLatestEdit() {
    return Collections.max(
      this.edits,
      Comparator.comparing(c -> c.getPosted())
    );
  }

  public void updateWithLatestEdit() {
    Edit latestEdit = getLatestEdit();
    this.setComment(latestEdit.getComment());
    this.setPosted(latestEdit.getPosted());
    this.setRating(latestEdit.getRating());
  }

  public Document toDoc() {
    Document doc = new Document();
    doc.put("user", user);
    doc.put("rating", rating);
    doc.put("comment", comment);
    doc.put("gid", gid);
    doc.put("posted", posted);
    doc.put("name", name);
    if (edits != null) doc.put(
      "edited",
      edits.stream().map(e -> e.toDoc()).toList()
    );

    return doc;
  }

  public static Review create(Document doc) {
    Review r = new Review();
    r.setUser(doc.getString("user"));
    r.setRating(doc.getInteger("rating"));
    r.setComment(doc.getString("comment"));
    r.setGid(doc.getInteger("gid"));
    r.setPosted(
      doc
        .getDate("posted")
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    );
    r.setName(doc.getString("name"));
    List<Document> edits = doc.getList("edited", Document.class);
    if (edits != null) {
      r.setEdits(edits.stream().map(d -> Edit.create(d)).toList());
    }
    return r;
  }

  public JsonObjectBuilder toJson(boolean withHistory) {
    JsonObjectBuilder job = Json
      .createObjectBuilder()
      .add("user", user)
      .add("rating", rating)
      .add("comment", comment)
      .add("gid", gid)
      .add("posted", posted.toString())
      .add("name", name);

    // SMELL: nested logic
    if (withHistory) {
      if (edits != null) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Edit edit : edits) {
          arrBuilder.add(edit.toJson());
        }
        job.add("edited", arrBuilder.build());
      } else {
        job.add("edited", edits != null);
      }
    }

    return job;
  }
}
