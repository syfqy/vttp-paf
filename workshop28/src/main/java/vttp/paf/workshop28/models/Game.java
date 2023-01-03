package vttp.paf.workshop28.models;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.Document;

public class Game {

  private Integer gid;
  private String name;
  private Integer year;
  private Integer rank;
  private Double average;
  private Integer usersRated;
  private String url;
  private String thumbnail;
  private List<String> reviews;

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

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public Double getAverage() {
    return average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }

  public Integer getUsersRated() {
    return usersRated;
  }

  public void setUsersRated(Integer usersRated) {
    this.usersRated = usersRated;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public List<String> getReviews() {
    return reviews;
  }

  public void setReviews(List<String> reviews) {
    this.reviews = reviews;
  }

  public static Game create(Document doc) {
    Game game = new Game();
    game.setGid(doc.getInteger("gid"));
    game.setName(doc.getString("name"));
    game.setYear(doc.getInteger("year"));
    game.setRank(doc.getInteger("rank"));
    game.setAverage(doc.getDouble("average"));
    game.setUsersRated(doc.getInteger("users_rated"));
    game.setUrl(doc.getString("url"));
    game.setThumbnail(doc.getString("thumbnail"));

    return game;
  }

  public JsonObject toJson() {
    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    for (String review : reviews) {
      arrBuilder.add(review);
    }
    JsonArray reviewsArr = arrBuilder.build();

    JsonObject json = Json
      .createObjectBuilder()
      .add("game_id", gid)
      .add(".name", name)
      .add("year", year)
      .add("rank", rank)
      .add("average", average)
      .add("users_rated", usersRated)
      .add("url", url)
      .add("thumbnail", thumbnail)
      .add("reviews", reviewsArr)
      .add("timestamp", LocalDateTime.now().toString())
      .build();

    return json;
  }

  @Override
  public String toString() {
    return (
      "Game [gid=" +
      gid +
      ", name=" +
      name +
      ", year=" +
      year +
      ", rank=" +
      rank +
      ", average=" +
      average +
      ", usersRated=" +
      usersRated +
      ", url=" +
      url +
      ", thumbnail=" +
      thumbnail +
      ", reviews=" +
      reviews +
      "]"
    );
  }
}
