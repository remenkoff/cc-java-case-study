package javacasestudy.tests.fixtures;

import javacasestudy.*;
import javacasestudy.entities.Codecast;
import javacasestudy.entities.License;
import javacasestudy.entities.User;
import javacasestudy.tests.TestSetup;
import javacasestudy.useCases.presentCodecast.PresentCodecastUseCase;

import java.util.ArrayList;
import java.util.List;

public class CodecastPresentation {
  private final PresentCodecastUseCase useCase = new PresentCodecastUseCase();
  public static Gatekeeper gatekeeper = new Gatekeeper();

  public CodecastPresentation() {
    new FixtureSetup();
  }

  public boolean addUser(String username) {
    User user = new User(username);
    Context.userGateway.save(user);
    return Context.userGateway.findUserByName(username) != null;
  }

  public boolean loginUser(String username) {
    User user = Context.userGateway.findUserByName(username);
    if (user == null) {
      return false;
    }
    gatekeeper.setLoggedInUser(user);
    return true;
  }

  public boolean createLicenseForViewing(String username, String codecastTitle) {
    return createLicenseFor(License.Type.VIEWING, username, codecastTitle);
  }

  public boolean createLicenseForDownloading(String username, String codecastTitle) {
    return createLicenseFor(License.Type.DOWNLOADING, username, codecastTitle);
  }

  private boolean createLicenseFor(License.Type type, String username, String codecastTitle) {
    User user = Context.userGateway.findUserByName(username);
    Codecast codecast = Context.codecastGateway.findCodecast(codecastTitle);
    License license = new License(type, user, codecast);
    Context.licenseGateway.save(license);
    return useCase.isLicensedTo(type, user, codecast);
  }

  public String presentationUser() {
    return gatekeeper.getLoggedInUser().getUsername();
  }

  public boolean clearCodecasts() {
    List<Codecast> codecasts = Context.codecastGateway.findAllCodecastsChronoSorted();
    for (Codecast codecast : new ArrayList<>(codecasts)) {
      Context.codecastGateway.delete(codecast);
    }
    return Context.codecastGateway.findAllCodecastsChronoSorted().size() == 0;
  }

  public int numberOfCodecastsPresented() {
    return useCase.presentCodecasts(gatekeeper.getLoggedInUser()).size();
  }
}
