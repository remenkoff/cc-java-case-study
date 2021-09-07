package javacasestudy;

import javacasestudy.entities.User;

public class Gatekeeper {
  private User loggedInUser;

  public User getLoggedInUser() {
    return loggedInUser;
  }

  public void setLoggedInUser(User user) {
    loggedInUser = user;
  }
}
