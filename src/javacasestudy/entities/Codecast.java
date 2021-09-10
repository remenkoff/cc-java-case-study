package javacasestudy.entities;

import java.util.Date;

public class Codecast extends Entity {
  private final String title;
  private final Date pubDate;

  public Codecast(String title, Date pubDate) {
    this.title = title;
    this.pubDate = pubDate;
  }

  public String getTitle() {
    return title;
  }

  public Date getPubDate() {
    return pubDate;
  }
}
