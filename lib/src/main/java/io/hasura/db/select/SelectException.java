package io.hasura.db;

public class SelectException extends Exception {
  private static final long serialVersionUID = 1;
  private SelectError code;

  /**
   * Construct a new SelectException with a particular error code.
   *
   * @param theCode
   *          The error code to identify the type of exception.
   * @param theMessage
   *          A message describing the error in more detail.
   */
  public SelectException(SelectError theCode, String theMessage) {
    super(theMessage);
    code = theCode;
  }

  /**
   * Construct a new SelectException with a particular error code.
   *
   * @param theCode
   *          The error code to identify the type of exception.
   * @param cause
   *          The cause of the error.
   */
  public SelectException(SelectError theCode, Throwable cause) {
    super(cause);
    code = theCode;
  }

  /**
   * Access the code for this error.
   *
   * @return The code for this error.
   */
  public SelectError getCode() {
    return code;
  }
}
