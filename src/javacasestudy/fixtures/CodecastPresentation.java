package javacasestudy.fixtures;

import javacasestudy.*;

import java.util.ArrayList;
import java.util.List;

public class CodecastPresentation {
  private final PresentCodecastUseCase useCase = new PresentCodecastUseCase();
  public static Gatekeeper gatekeeper = new Gatekeeper();

  public CodecastPresentation() {
    Context.gateway = new MockGateway();
  }

  public boolean addUser(String username) {
    User user = new User(username);
    Context.gateway.save(user);
    return Context.gateway.findUser(username) != null;
  }

  public boolean loginUser(String username) {
    User user = Context.gateway.findUser(username);
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
    User user = Context.gateway.findUser(username);
    Codecast codecast = Context.gateway.findCodecast(codecastTitle);
    License license = new License(type, user, codecast);
    Context.gateway.save(license);
    return useCase.isLicensedTo(type, user, codecast);
  }

  public String presentationUser() {
    return gatekeeper.getLoggedInUser().getUsername();
  }

  public boolean clearCodecasts() {
    List<Codecast> codecasts = Context.gateway.findAllCodecastsChronoSorted();
    for (Codecast codecast : new ArrayList<>(codecasts)) {
      Context.gateway.delete(codecast);
    }
    return Context.gateway.findAllCodecastsChronoSorted().size() == 0;
  }

  public int numberOfCodecastsPresented() {
    return useCase.presentCodecasts(gatekeeper.getLoggedInUser()).size();
  }
}
