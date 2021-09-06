package javacasestudy.fixtures;

import javacasestudy.PresentCodecastUseCase;
import javacasestudy.PresentableCodecast;
import javacasestudy.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OfCodecasts {
  private List<Object> list(Object... objects) {
    return Arrays.asList(objects);
  }

  public List<Object> query() {
    User loggedInUser = CodecastPresentation.gatekeeper.getLoggedInUser();
    PresentCodecastUseCase useCase = new PresentCodecastUseCase();
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(loggedInUser);
    List<Object> queryResponse = new ArrayList<>();

    for (PresentableCodecast pcc : presentableCodecasts) {
      List<Object> row = makeRow(
        pcc.title(),
        pcc.publicationDate(),
        pcc.title(),
        pcc.title(),
        pcc.isViewable(),
        false
      );
      queryResponse.add(row);
    }
    return queryResponse;
  }

  private List<Object> makeRow(
    String title,
    String publicationDate,
    String picture,
    String description,
    boolean isViewable,
    boolean isDownloadable
  ) {
    return list(list("title", title),
                list("publicationDate", publicationDate),
                list("picture", picture),
                list("description", description),
                list("isViewable", isViewable ? "+" : "-"),
                list("isDownloadable", isDownloadable ? "+" : "-")
    );
  }
}
