package javacasestudy.useCases.presentCodecast;

import javacasestudy.Context;
import javacasestudy.entities.Codecast;
import javacasestudy.entities.License;
import javacasestudy.entities.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static javacasestudy.entities.License.Type.*;

public class PresentCodecastUseCase {
  private static final String PRESENTABLE_DATE_FORMAT = "M/dd/yyyy";

  public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
    SimpleDateFormat formatter = new SimpleDateFormat(PRESENTABLE_DATE_FORMAT);
    return Context.codecastGateway.findAllCodecastsChronoSorted().stream().map(codecast -> new PresentableCodecast(
      codecast.getTitle(),
      formatter.format(codecast.getPublicationDate()),
      isLicensedToViewCodecast(loggedInUser, codecast),
      isLicensedToDownloadCodecast(loggedInUser, codecast)
    )).collect(Collectors.toList());
  }

  public boolean isLicensedToViewCodecast(User loggedInUser, Codecast codecast) {
    return isLicensedTo(VIEWING, loggedInUser, codecast);
  }

  public boolean isLicensedToDownloadCodecast(User loggedInUser, Codecast codecast) {
    return isLicensedTo(DOWNLOADING, loggedInUser, codecast);
  }

  public boolean isLicensedTo(License.Type type, User loggedInUser, Codecast codecast) {
    List<License> licenses = Context.licenseGateway.findLicensesForUserAndCodecast(loggedInUser, codecast);
    return licenses.stream().anyMatch(license -> license.getType() == type);
  }
}
