package vttp.paf.workshop28.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vttp.paf.workshop28.models.Comment;
import vttp.paf.workshop28.models.Game;
import vttp.paf.workshop28.services.GameService;

@RestController
public class GameRESTController {

  @Autowired
  GameService gameSvc;

  @GetMapping(
    path = "/game/{gameId}/reviews",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<String> getGameById(@PathVariable Integer gameId) {
    Optional<Game> opt = gameSvc.findGameById(gameId);

    if (opt.isEmpty()) return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body("Game with gameId " + gameId + " not found");

    Game game = opt.get();
    JsonObject resp = game.toJson();

    return ResponseEntity.ok(resp.toString());
  }

  @GetMapping(
    path = "/games/{sortOrder}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<String> getGamesByTopRating(
    @PathVariable String sortOrder
  ) {
    boolean isHighest = sortOrder.toLowerCase().equals("highest");
    System.out.println(
      "getting games by: " + sortOrder.toLowerCase() + isHighest
    );
    List<Comment> games = gameSvc.findGameByTopRating(isHighest);

    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

    for (Comment game : games) {
      arrBuilder.add(game.toJson());
    }
    JsonArray gamesArr = arrBuilder.build();

    JsonObject resp = Json
      .createObjectBuilder()
      .add("rating", sortOrder.toLowerCase())
      .add("games", gamesArr)
      .add("timestamp", LocalDateTime.now().toString())
      .build();

    return ResponseEntity.ok(resp.toString());
  }
}
