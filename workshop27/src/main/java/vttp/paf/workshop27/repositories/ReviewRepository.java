package vttp.paf.workshop27.repositories;

import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop27.models.Edit;
import vttp.paf.workshop27.models.Review;
import vttp.paf.workshop27.repositories.Exceptions.IdNotFoundException;

@Repository
public class ReviewRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private static final String REVIEW_COLLECTION = "review";

  public boolean createReview(Review r) {
    /*
        db.comment.insert({
        "user": <user>,
        "rating": <rating>,
        "gid": <gid>,
        "posted": <date>,
        "name": <name of board game>
        });
    */

    Document docToInsert = r.toDoc();
    Document docInserted = mongoTemplate.insert(docToInsert, REVIEW_COLLECTION);
    return docInserted != null;
  }

  public boolean updateReview(String id, Edit edit) {
    /*
        db.review.updateOne({
            "_id": ObjectId("<id>")
        },   
        {
            $push: 
            {
                "edited": 
                    {
                        "comment": <edit.comment>,
                        "rating": <edit.rating>,
                        "posted" <edit.posted>
                    }
            }    
        }
    );
     */
    ObjectId rid = new ObjectId(id);
    Query q = Query.query(Criteria.where("_id").is(rid));
    Update updateOps = new Update().push("edited").each(edit.toDoc());

    UpdateResult updateResult = mongoTemplate.updateFirst(
      q,
      updateOps,
      Document.class,
      REVIEW_COLLECTION
    );
    return updateResult.getModifiedCount() > 0;
  }

  public Review getReview(String id) throws IdNotFoundException {
    /*
    db.review.find({
    "_id": ObjectId(<id>)
    })
    */

    ObjectId rid = new ObjectId(id);
    Query q = Query.query(Criteria.where("_id").is(rid));
    Document result = mongoTemplate.findOne(
      q,
      Document.class,
      REVIEW_COLLECTION
    );
    if (result == null) throw new IdNotFoundException(
      "Cannot find Review with id " + rid
    );

    return Review.create(result);
  }
}
