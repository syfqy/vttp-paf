package vttp.paf.workshop24.repositories;

public class Queries {

  public static final String SQL_INSERT_INTO_ORDER =
    """
            INSERT INTO orders(order_date, customer_name, ship_address, notes, tax) 
            VALUES (?, ?, ?, ?, ?);
            """;

  public static final String SQL_INSERT_INTO_ORDER_DETAILS =
    """
            INSERT INTO order_details(id, product, unit_price, discount, quantity)
            VALUES (?, ?, ?, ?, ?);
            """;

  public static final String SQL_SELECT_LATEST_ORDER_ID =
    """
                SELECT MAX(order_id) AS last_order_id
                FROM orders;
                    """;
}
