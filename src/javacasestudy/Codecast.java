package javacasestudy;

public class Codecast extends Entity {
  private final String title;
  private final String publicationDate;

  public Codecast(String title, String publicationDate) {
    this.title = title;
    this.publicationDate = publicationDate;
  }

  public String getTitle() {
    return title;
  }

  public String getPublicationDate() {
    return publicationDate;
  }
}
