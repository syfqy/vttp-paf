package vttp.paf.workshop27.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop27.repositories.Exceptions.IdNotFoundException;

@Repository
public class GameRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private static final String GAME_COLLECTION = "game";

  public String getGameNameById(Integer gid) throws IdNotFoundException {
    /* 
    db.game.find(
        {
            "gid": <gid>,
        },
        {
            "_id": 0, 
            "name": 1
        }
    )
    */

    Criteria c = Criteria.where("gid").is(gid);
    Query q = Query.query(c);

    Document result = mongoTemplate.findOne(q, Document.class, GAME_COLLECTION);

    if (result == null) {
      throw new IdNotFoundException("Game with game id " + gid + " not found");
    }

    return result.getString("name");
  }
}
