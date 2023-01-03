package vttp.paf.workshop23.repositories.exceptions;

public class OrderNotFoundException extends Exception {

  public OrderNotFoundException() {
    super();
  }

  public OrderNotFoundException(String message) {
    super(message);
  }
}
