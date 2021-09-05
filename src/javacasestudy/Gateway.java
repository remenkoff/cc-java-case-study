package javacasestudy;

import java.util.List;

public interface Gateway {
  List<Codecast> findAllCodecasts();
  Codecast findCodecast(String title);
  void save(Codecast codecast);
  void delete(Codecast codecast);

  User findUser(String username);
  void save(User user);

  void save(License license);
}
