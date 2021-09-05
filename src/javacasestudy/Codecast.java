package javacasestudy;

public class Codecast extends Entity {
  private String title;
  private String publicationDate;

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
