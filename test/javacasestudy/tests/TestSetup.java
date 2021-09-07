package javacasestudy.tests;

import javacasestudy.Context;
import javacasestudy.Gatekeeper;
import javacasestudy.tests.doubles.InMemoryCodecastGateway;
import javacasestudy.tests.doubles.InMemoryLicenseGateway;
import javacasestudy.tests.doubles.InMemoryUserGateway;

public class TestSetup {
  public static void setupContext() {
    Context.userGateway = new InMemoryUserGateway();
    Context.codecastGateway = new InMemoryCodecastGateway();
    Context.licenseGateway = new InMemoryLicenseGateway();
    Context.gatekeeper = new Gatekeeper();
  }
}
