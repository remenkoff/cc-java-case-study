package javacasestudy.tests.doubles;

import javacasestudy.entities.Codecast;
import javacasestudy.entities.License;
import javacasestudy.gateways.LicenseGateway;
import javacasestudy.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLicenseGateway extends GatewayUtilities<License> implements LicenseGateway {
  @Override
  public List<License> findLicensesForUserAndCodecast(User loggedInUser, Codecast codecast) {
    List<License> foundLicenses = new ArrayList<>();
    for (License license : getEntities()) {
      if (license.getUser().isTheSame(loggedInUser) && license.getCodecast().isTheSame(codecast))
        foundLicenses.add(license);
    }
    return foundLicenses;
  }
}
