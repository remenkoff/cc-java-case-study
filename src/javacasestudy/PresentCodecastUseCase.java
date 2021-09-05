package javacasestudy;

import java.util.List;
import java.util.stream.Collectors;

public class PresentCodecastUseCase {
  public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
    return Context.gateway.findAllCodecasts().stream()
      .map(codecast -> new PresentableCodecast(
        codecast.getTitle(),
        codecast.getPublicationDate(),
        isLicensedToViewCodecast(loggedInUser, codecast))
      )
      .collect(Collectors.toList());
  }

  public boolean isLicensedToViewCodecast(User user, Codecast codecast) {
    List<License> licenses = Context.gateway.findLicensesForUserAndCodecast(user, codecast);
    return !licenses.isEmpty();
  }
}
