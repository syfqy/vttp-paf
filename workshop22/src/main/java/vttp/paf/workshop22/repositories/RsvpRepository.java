package vttp.paf.workshop22.repositories;

import static vttp.paf.workshop22.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import vttp.paf.workshop22.models.Rsvp;

@Repository
public class RsvpRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Rsvp> findAllRsvps() {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_RSVPS);
    final List<Rsvp> rsvps = new LinkedList<>();
    while (rs.next()) {
      rsvps.add(Rsvp.create(rs));
    }

    return rsvps;
  }

  public Rsvp findOneRsvp(String name) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(
      SQL_SELECT_RSVP_BY_NAME,
      name
    );

    rs.next();
    Rsvp rsvp = Rsvp.create(rs);
    return rsvp;
  }

  public int createRsvp(Rsvp rsvp) {
    return jdbcTemplate.update(
      SQL_INSERT_RSVP,
      rsvp.getName(),
      rsvp.getEmail(),
      rsvp.getPhone(),
      rsvp.getConfirmationDate(),
      rsvp.getComments()
    );
  }

  public int updateRsvp(Rsvp rsvp, String email) {
    return jdbcTemplate.update(
      SQL_UPDATE_RSVP,
      rsvp.getName(),
      rsvp.getEmail(),
      rsvp.getPhone(),
      rsvp.getConfirmationDate(),
      rsvp.getComments(),
      email
    );
  }

  public Integer countRsvps() {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_COUNT_RSVP);
    rs.next();
    return rs.getInt("n_rsvps");
  }
}
