package vttp.paf.workshop28.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.paf.workshop28.models.Comment;
import vttp.paf.workshop28.models.Game;
import vttp.paf.workshop28.repositories.GameRepository;

@Service
public class GameService {

  @Autowired
  GameRepository gameRepo;

  public Optional<Game> findGameById(Integer gid) {
    return gameRepo.findGameById(gid);
  }

  public List<Comment> findGameByTopRating(boolean isHighest) {
    return gameRepo.findGameByTopRating(isHighest);
  }
}
