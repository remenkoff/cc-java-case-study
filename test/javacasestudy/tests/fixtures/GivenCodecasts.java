package javacasestudy.tests.fixtures;

import javacasestudy.entities.Codecast;
import javacasestudy.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GivenCodecasts {
  private static final String FIT_NESSE_DATE_FORMAT = "M/d/yyyy";

  private String title;
  private Date pubDate;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPubDate(String pubDateString) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(FIT_NESSE_DATE_FORMAT);
    this.pubDate = formatter.parse(pubDateString);
  }

  public void execute() {
    Codecast codecast = new Codecast(title, pubDate);
    Context.codecastGateway.save(codecast);
  }
}
