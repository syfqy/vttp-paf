package vttp.paf.workshop23.repositories;

public class Queries {

  public static final String SQL_SELECT_ORDER_SUMMARY_BY_ID =
    """
        WITH line_summary as (
            SELECT o.id as order_id, o.order_date, o.customer_id, (d.quantity*d.unit_price*(1-d.discount)) AS line_total, (d.quantity*p.standard_cost) AS line_cost
            FROM orders o
            JOIN order_details d
                ON o.id = d.order_id
            JOIN products p 
                ON d.product_id = p.id
            ), 
            order_summary AS (
                SELECT order_id, order_date, customer_id, sum(line_total) AS total, sum(line_cost) AS cost_price
                FROM line_summary
                GROUP BY 1, 2, 3
            )
            SELECT *
            FROM order_summary
            WHERE order_id = ?;
            """;
}
