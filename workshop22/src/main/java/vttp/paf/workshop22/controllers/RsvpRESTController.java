package vttp.paf.workshop22.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vttp.paf.workshop22.models.Rsvp;
import vttp.paf.workshop22.services.RsvpService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RsvpRESTController {

  @Autowired
  RsvpService rsvpSvc;

  @GetMapping("/rsvps")
  public ResponseEntity<String> getAllRsvps() {
    List<Rsvp> rsvps = rsvpSvc.getAllRsvps();
    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    for (Rsvp r : rsvps) {
      arrBuilder.add(r.toJson());
    }

    JsonArray result = arrBuilder.build();

    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(result.toString());
  }

  @GetMapping("/rsvp")
  public ResponseEntity<String> getRsvpByName(@RequestParam String name) {
    Rsvp rsvp = rsvpSvc.getRsvpByName(name);
    JsonObject result = rsvp.toJson();

    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(result.toString());
  }

  @PostMapping("/rsvp")
  public ResponseEntity<String> addRsvp(
    @RequestBody MultiValueMap<String, String> form
  ) {
    String name = form.getFirst("name");
    String email = form.getFirst("email");
    String phone = form.getFirst("phone");
    String confirmationDate = form.getFirst("confirmationDate");
    String comments = form.getFirst("comments");

    System.out.printf(
      "name: %s, email: %s, phone: %s, confirmation date: %s, comments: %s, \n",
      name,
      email,
      phone,
      confirmationDate,
      comments
    );

    Rsvp rsvp = new Rsvp();
    rsvp.setName(name);
    rsvp.setEmail(email);
    rsvp.setPhone(phone);
    rsvp.setConfirmationDate(confirmationDate);
    rsvp.setComments(comments);

    try {
      if (!rsvpSvc.createRsvp(rsvp)) System.out.println(
        ">>>> Error! Cannot create Rsvp"
      );
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .contentType(MediaType.APPLICATION_JSON)
      .body("rsvp created successfully");
  }

  @PostMapping("/rsvp/update/{user}")
  public ResponseEntity<String> updateRsvp(
    @PathVariable String user,
    @RequestBody MultiValueMap<String, String> form
  ) {
    System.out.println("update");

    String name = form.getFirst("name");
    String newEmail = form.getFirst("email");
    String phone = form.getFirst("phone");
    String confirmationDate = form.getFirst("confirmationDate");
    String comments = form.getFirst("comments");

    System.out.printf(
      "name: %s, email: %s, phone: %s, confirmation date: %s, comments: %s, \n",
      name,
      newEmail,
      phone,
      confirmationDate,
      comments
    );

    Rsvp rsvp = new Rsvp();
    rsvp.setName(name);
    rsvp.setEmail(newEmail);
    rsvp.setPhone(phone);
    rsvp.setConfirmationDate(confirmationDate);
    rsvp.setComments(comments);

    // try {
    if (!rsvpSvc.updateRsvp(rsvp, user)) System.out.println(
      ">>>> Error! Cannot update Rsvp"
    );
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // }

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .contentType(MediaType.APPLICATION_JSON)
      .body("rsvp updated successfully");
  }

  @GetMapping("rsvps/count")
  public ResponseEntity<String> getCountOfRsvps() {
    Integer result = rsvpSvc.getCountOfRsvps();
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .contentType(MediaType.APPLICATION_JSON)
      .body(result.toString());
  }
}
