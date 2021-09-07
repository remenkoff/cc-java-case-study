package javacasestudy.gateways;

import javacasestudy.entities.User;

public interface UserGateway {
  User findUserByName(String username);
  void save(User user);
}
