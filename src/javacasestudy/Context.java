package javacasestudy;

import javacasestudy.gateways.CodecastGateway;
import javacasestudy.gateways.LicenseGateway;
import javacasestudy.gateways.UserGateway;

public class Context {
  public static UserGateway userGateway;
  public static CodecastGateway codecastGateway;
  public static LicenseGateway licenseGateway;
  public static Gatekeeper gatekeeper;
}
