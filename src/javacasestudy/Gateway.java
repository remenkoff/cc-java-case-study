package javacasestudy;

import java.util.List;

public interface Gateway {
  List<Codecast> findAllCodecastsChronoSorted();
  Codecast findCodecast(String title);
  void save(Codecast codecast);
  void delete(Codecast codecast);

  User findUser(String username);
  void save(User user);

  void save(License license);
  List<License> findLicensesForUserAndCodecast(User user, Codecast codecast);
  List<License> findLicensesFor(User user);
}
