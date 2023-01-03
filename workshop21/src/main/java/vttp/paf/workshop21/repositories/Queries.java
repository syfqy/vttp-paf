package vttp.paf.workshop21.repositories;

public class Queries {

  public static final String SQL_SELECT_ALL_CUSTOMERS =
    """
        SELECT * 
        FROM customers
        LIMIT ?
        OFFSET ?
            """;

  public static final String SQL_SELECT_CUSTOMER_BY_ID =
    """
        SELECT *
        FROM customers
        WHERE id = ?
            """;
  public static final String SQL_SELECT_ORDERS_BY_CUSTOMER_ID =
    """
        SELECT *
        FROM orders
        where customer_id = ?
            """;
}
