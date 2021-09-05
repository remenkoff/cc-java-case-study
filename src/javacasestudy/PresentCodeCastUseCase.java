package javacasestudy;

import java.util.ArrayList;
import java.util.List;

public class PresentCodeCastUseCase {
  public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
    return new ArrayList<PresentableCodecast>();
  }

  public boolean isLicensedToViewCodecast(User user, Codecast codecast) {
    return false;
  }
}
