package javacasestudy.tests;

import javacasestudy.Context;
import javacasestudy.Gatekeeper;
import javacasestudy.entities.Codecast;
import javacasestudy.entities.License;
import javacasestudy.entities.User;
import javacasestudy.tests.doubles.InMemoryCodecastGateway;
import javacasestudy.tests.doubles.InMemoryLicenseGateway;
import javacasestudy.tests.doubles.InMemoryUserGateway;

import java.util.Date;

import static javacasestudy.entities.License.Type.VIEWING;

public class TestSetup {
  public static void setupContext() {
    Context.userGateway = new InMemoryUserGateway();
    Context.codecastGateway = new InMemoryCodecastGateway();
    Context.licenseGateway = new InMemoryLicenseGateway();
    Context.gatekeeper = new Gatekeeper();
  }

  public static void setupData() {
    User crowley = new User("Mr. Crowley");
    User mason = new User("Perry Mason");

    Codecast episode1 = new Codecast("Episode 1 — A New Beginning", new Date());
    Codecast episode2 = new Codecast("Episode 2 — Great Debate",
                                     new Date(episode1.getPublicationDate().getTime() + 1)
    );

    License crowleyLicense1 = new License(VIEWING, crowley, episode1);
    License crowleyLicense2 = new License(VIEWING, crowley, episode2);

    Context.userGateway.save(crowley);
    Context.userGateway.save(mason);
    Context.codecastGateway.save(episode1);
    Context.codecastGateway.save(episode2);
    Context.licenseGateway.save(crowleyLicense1);
    Context.licenseGateway.save(crowleyLicense2);
  }
}
