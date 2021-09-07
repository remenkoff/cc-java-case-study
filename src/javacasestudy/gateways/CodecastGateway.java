package javacasestudy.gateways;

import javacasestudy.entities.Codecast;

import java.util.List;

public interface CodecastGateway {
  void save(Codecast codecast);
  void delete(Codecast codecast);
  List<Codecast> findAllCodecastsChronoSorted();
  Codecast findCodecast(String title);
}
