package vttp.paf.workshop28.repositories;

import com.mongodb.client.AggregateIterable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Avg;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop28.models.Comment;
import vttp.paf.workshop28.models.Game;

@Repository
public class GameRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private static final String GAME_COLLECTION = "game";

  public Optional<Game> findGameById(Integer gid) {
    /*
    db.game.aggregate([
    { 
        $match: { gid: 24 }
    },
    {
        $lookup: {
        from: 'review',
        foreignField: 'gid',
        localField: 'gid',
        as: 'reviews',
        }
    },
    {
        $project: {
            gid: 1,
            name: 1,
            year: 1,
            rank: "$ranking",
            "average": {$avg: "$reviews.rating"},
            users_rated: 1,
            url:1,
            thumbnail: "$image",
            "reviews._id": 1
    }
    }
    ]);
   */
    MatchOperation matchByGid = Aggregation.match(
      Criteria.where("gid").is(gid)
    );

    LookupOperation lookupReviews = Aggregation.lookup(
      "review",
      "gid",
      "gid",
      "reviews"
    );

    ProjectionOperation projectGame = Aggregation
      .project("gid", "name", "year", "users_rated", "url", "reviews")
      .and(Avg.avgOf("reviews.rating"))
      .as("average")
      .and("ranking")
      .as("rank")
      .and("image")
      .as("thumbnail");

    Aggregation pipeline = Aggregation.newAggregation(
      matchByGid,
      lookupReviews,
      projectGame
    );

    AggregationResults<Document> results = mongoTemplate.aggregate(
      pipeline,
      GAME_COLLECTION,
      Document.class
    );

    if (!results.iterator().hasNext()) return Optional.empty();

    Document doc = results.iterator().next();
    Game game = Game.create(doc);
    System.out.println("Game >>> " + game.toString());
    List<String> reviewIds = doc
      .getList("reviews", Document.class)
      .stream()
      .map(review -> "review/" + review.getObjectId("_id").toString())
      .toList();

    game.setReviews(reviewIds);

    return Optional.of(game);
  }

  public List<Comment> findGameByTopRating(boolean isHighest) {
    /*
  db.game.aggregate([
      {
          $sort: {"gid": 1}
      },
      {
          $limit: 10
      },
      {
          $lookup: {
              from: 'comment',
              foreignField: 'gid',
              localField: 'gid',
              as: 'comments',
              pipeline: [
                  { $sort: { rating: 1 } },
                  { $limit: 1 }
              ]
          }
      },
      {
          $unwind: "$comments"
      },
      {
          $project: {
              gid: 1,
              name: 1,
              rating: "$comments.rating",
              user: "$comments.user",
              comment: "$comments.c_text",
              review_id: "$comments.c_id"
          }
      }
  ]);
  */

    // set sort order of rating (highest or lowest rating)
    Integer ratingSortOrder = isHighest ? -1 : 1;

    // sort games by gid
    Document sortGamesByGid = new Document("$sort", new Document("gid", 1));

    // limit to 10 games
    // WARNING: limit needed to prevent connection timeout error
    Document limitGames = new Document("$limit", 10);

    // sort comment by highest/lowest rating then get first comment
    List<Document> lookupPipeline = Arrays.asList(
      new Document("$sort", new Document("rating", ratingSortOrder)),
      new Document("$limit", 1)
    );

    // embed comments as array into game document
    Document lookupComments = new Document(
      "$lookup",
      new Document("from", "comment")
        .append("localField", "gid")
        .append("foreignField", "gid")
        .append("as", "comments")
        .append("pipeline", lookupPipeline)
    );

    // unwind comments to unpack embedded comment document from array
    Document unwindComments = new Document("$unwind", "$comments");

    // select and rename fields
    Document selectFields = new Document(
      "$project",
      new Document(
        new Document("gid", 1)
          .append("name", 1)
          .append("rating", "$comments.rating")
          .append("user", "$comments.user")
          .append("comment", "$comments.c_text")
          .append("comment_id", "$comments.cid")
      )
    );

    // create final aggregation pipeline
    List<Document> pipeline = Arrays.asList(
      sortGamesByGid,
      limitGames,
      lookupComments,
      unwindComments,
      selectFields
    );

    System.out.println("pipeline >>> " + pipeline.toString());

    // exeute pipeline
    AggregateIterable<Document> results = mongoTemplate
      .getCollection(GAME_COLLECTION)
      .aggregate(pipeline);

    // cast each document to Comment
    List<Comment> games = new LinkedList<>();
    for (Document doc : results) {
      games.add(Comment.create(doc));
    }
    return games;
  }
}
