package vttp.paf.workshop27.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.paf.workshop27.models.Edit;
import vttp.paf.workshop27.models.Review;
import vttp.paf.workshop27.repositories.Exceptions.IdNotFoundException;
import vttp.paf.workshop27.repositories.GameRepository;
import vttp.paf.workshop27.repositories.ReviewRepository;

@Service
public class ReviewService {

  @Autowired
  ReviewRepository reviewRepo;

  @Autowired
  GameRepository gameRepo;

  public boolean createReview(Review r) {
    String name;
    try {
      name = gameRepo.getGameNameById(r.getGid());
      r.setName(name);
      return reviewRepo.createReview(r);
    } catch (IdNotFoundException e) {
      return false;
    }
  }

  public boolean updateReview(String rid, Edit edit) {
    // get review to update
    return reviewRepo.updateReview(rid, edit);
  }

  public Review getReview(String rid) {
    try {
      Review result = reviewRepo.getReview(rid);
      return result;
    } catch (IdNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }
}
