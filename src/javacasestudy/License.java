package javacasestudy;

public class License {
  private final User user;
  private final Codecast codecast;

  public License(User user, Codecast codecast) {
    this.user = user;
    this.codecast = codecast;
  }

  public User getUser() {
    return user;
  }

  public Codecast getCodecast() {
    return codecast;
  }
}
