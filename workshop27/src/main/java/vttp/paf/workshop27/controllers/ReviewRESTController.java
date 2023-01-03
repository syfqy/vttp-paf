package vttp.paf.workshop27.controllers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vttp.paf.workshop27.models.Edit;
import vttp.paf.workshop27.models.Review;
import vttp.paf.workshop27.services.ReviewService;

@RestController
@RequestMapping(path = "/review")
public class ReviewRESTController {

  @Autowired
  ReviewService reviewSvc;

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<String> postReview(
    @RequestBody MultiValueMap<String, String> form
  ) {
    // create Review from form data
    String user = form.getFirst("name");
    Integer rating = Integer.parseInt(form.getFirst("rating"));
    String comment = form.getFirst("comment");
    Integer gid = Integer.parseInt(form.getFirst("gid"));

    Review r = new Review();
    r.setUser(user);
    r.setRating(rating);
    r.setComment(comment);
    r.setGid(gid);
    r.setPosted(LocalDate.now());

    if (!reviewSvc.createReview(r)) {
      return ResponseEntity.status(404).body("Review not created");
    }
    return ResponseEntity.status(201).body("Review created successfully");
  }

  @PutMapping(path = "/{rid}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateReview(
    @RequestBody String json,
    @PathVariable String rid
  ) {
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject editJson = jsonReader.readObject();

    // create Edit object from request body
    Edit edit = new Edit();
    edit.setComment(editJson.getString("comment"));
    edit.setRating(editJson.getInt("rating"));
    edit.setPosted(LocalDate.now());

    // update review
    if (!reviewSvc.updateReview(rid, edit)) return ResponseEntity
      .status(404)
      .body("Review cannot be updated");

    return ResponseEntity.status(201).body("Review updated successfully");
  }

  @GetMapping(path = "/{rid}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getReviewLatest(@PathVariable String rid) {
    Review review = reviewSvc.getReview(rid);
    if (review == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Cannot find review for rid " + rid);
    }

    // update review with latest edit
    review.updateWithLatestEdit();

    // build json response
    JsonObject resp = review
      .toJson(false)
      .add("timestamp", LocalDateTime.now().toString())
      .build();

    return ResponseEntity.ok(resp.toString());
  }

  @GetMapping(
    path = "/{rid}/history",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<String> getReviewHistory(@PathVariable String rid) {
    Review review = reviewSvc.getReview(rid);
    if (review == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Cannot find review for rid " + rid);
    }

    // build json response
    JsonObject resp = review
      .toJson(true)
      .add("timestamp", LocalDateTime.now().toString())
      .build();

    return ResponseEntity.ok(resp.toString());
  }
}
