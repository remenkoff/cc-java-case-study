package javacasestudy.gateways;

import javacasestudy.entities.Codecast;
import javacasestudy.entities.License;
import javacasestudy.entities.User;

import java.util.List;

public interface LicenseGateway {
  void save(License license);
  List<License> findLicensesForUserAndCodecast(User loggedInUser, Codecast codecast);
}
