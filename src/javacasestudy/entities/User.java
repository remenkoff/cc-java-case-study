package javacasestudy.entities;

public class User extends Entity {
  private final String username;

  public User(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
