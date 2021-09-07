package javacasestudy.tests.doubles;

import javacasestudy.entities.User;
import javacasestudy.gateways.UserGateway;

public class InMemoryUserGateway extends GatewayUtilities<User> implements UserGateway {
  public User findUserByName(String username) {
    for (User user : getEntities()) {
      if (user.getUsername().equals(username))
        return user;
    }
    return null;
  }
}
