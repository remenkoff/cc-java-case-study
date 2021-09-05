package javacasestudy.fixtures;

import javacasestudy.*;

import java.util.ArrayList;
import java.util.List;

public class CodecastPresentation {
  private PresentCodecastUseCase useCase = new PresentCodecastUseCase();
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
    if (user != null) {
      gatekeeper.setLoggedInUser(user);
      return true;
    } else {
      return false;
    }
  }

  public boolean createLicenseForViewing(String username, String codecastTitle) {
    User user = Context.gateway.findUser(username);
    Codecast codecast = Context.gateway.findCodecast(codecastTitle);
    License license = new License(user, codecast);
    Context.gateway.save(license);
    return useCase.isLicensedToViewCodecast(user, codecast);
  }

  public boolean createLicenseForDownloading(String username, String codecastTitle) {
    return false;
  }

  public String presentationUser() {
    return gatekeeper.getLoggedInUser().getUsername();
  }

  public boolean clearCodecasts() {
    List<Codecast> codecasts = Context.gateway.findAllCodecasts();
    for (Codecast codecast: new ArrayList<>(codecasts)) {
      Context.gateway.delete(codecast);
    }
    return Context.gateway.findAllCodecasts().size() == 0;
  }

  public int numberOfCodecastPresented() {
    List<PresentableCodecast> presentations = useCase.presentCodecasts(gatekeeper.getLoggedInUser());
    return presentations.size();
  }
}
