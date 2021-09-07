package javacasestudy.entities;

import java.util.Date;

public class Codecast extends Entity {
  private final String title;
  private final Date publicationDate;

  public Codecast(String title, Date publicationDate) {
    this.title = title;
    this.publicationDate = publicationDate;
  }

  public String getTitle() {
    return title;
  }

  public Date getPublicationDate() {
    return publicationDate;
  }
}
