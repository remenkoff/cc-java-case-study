package javacasestudy;

public class License {
  public enum Type {VIEWING, DOWNLOADING}

  private final Type type;
  private final User user;
  private final Codecast codecast;

  public License(Type type, User user, Codecast codecast) {
    this.type = type;
    this.user = user;
    this.codecast = codecast;
  }

  public Type getType() {
    return type;
  }

  public User getUser() {
    return user;
  }

  public Codecast getCodecast() {
    return codecast;
  }
}
