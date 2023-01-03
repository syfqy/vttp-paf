package vttp.paf.workshop22.repositories;

public class Queries {

  public static final String SQL_SELECT_ALL_RSVPS =
    """
        SELECT *
        FROM rsvp;
            """;

  public static final String SQL_SELECT_RSVP_BY_NAME =
    """
        SELECT *
        FROM rsvp
        WHERE name LIKE CONCAT('%' ? '%');
            """;

  public static final String SQL_INSERT_RSVP =
    """
        INSERT INTO rsvp(name, email, phone, confirmation_date, comments)
        VALUES (?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?);
            """;

  public static final String SQL_UPDATE_RSVP =
    """
        UPDATE rsvp
        SET name = ?, email = ?, phone = ?, confirmation_date = STR_TO_DATE(?, '%d/%m/%Y'), comments = ?
        WHERE email = ? and id > 0;
            """;
  public static final String SQL_COUNT_RSVP =
    """
        SELECT COUNT(id) AS n_rsvps
        FROM rsvp
        WHERE confirmation_date IS NOT NULL;
            """;
}
