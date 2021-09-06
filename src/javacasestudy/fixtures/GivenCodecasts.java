package javacasestudy.fixtures;

import javacasestudy.Codecast;
import javacasestudy.Context;

public class GivenCodecasts {
  private String title;
  private String published;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPublished(String published) {
    this.published = published;
  }

  public void execute() {
    Codecast codecast = new Codecast(title, published);
    Context.gateway.save(codecast);
  }
}
