package vttp.paf.workshop22.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.paf.workshop22.models.Rsvp;
import vttp.paf.workshop22.repositories.RsvpRepository;

@Service
public class RsvpService {

  @Autowired
  RsvpRepository rsvpRepo;

  public List<Rsvp> getAllRsvps() {
    return rsvpRepo.findAllRsvps();
  }

  public Rsvp getRsvpByName(String name) {
    return rsvpRepo.findOneRsvp(name);
  }

  public boolean createRsvp(Rsvp rsvp) {
    return rsvpRepo.createRsvp(rsvp) > 0;
  }

  public boolean updateRsvp(Rsvp rsvp, String email) {
    return rsvpRepo.updateRsvp(rsvp, email) > 0;
  }

  public Integer getCountOfRsvps() {
    return rsvpRepo.countRsvps();
  }
}
