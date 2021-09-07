package javacasestudy.tests.doubles;

import javacasestudy.entities.Codecast;
import javacasestudy.gateways.CodecastGateway;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryCodecastGateway extends GatewayUtilities<Codecast> implements CodecastGateway {
  @Override
  public List<Codecast> findAllCodecastsChronoSorted() {
    List<Codecast> sortedCodecasts = new ArrayList<>(getEntities());
    sortedCodecasts.sort(Comparator.comparing(Codecast::getPublicationDate));
    return sortedCodecasts;
  }

  @Override
  public Codecast findCodecast(String title) {
    for (Codecast codecast : getEntities()) {
      if (codecast.getTitle().equals(title))
        return codecast;
    }
    return null;
  }
}
