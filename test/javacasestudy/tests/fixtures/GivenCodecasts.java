package javacasestudy.tests.fixtures;

import javacasestudy.entities.Codecast;
import javacasestudy.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GivenCodecasts {
  private static final String FIT_NESSE_DATE_FORMAT = "M/d/yyyy";

  private String title;
  private Date publicationDate;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPublicationDate(String publicationDateString) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(FIT_NESSE_DATE_FORMAT);
    this.publicationDate = formatter.parse(publicationDateString);
  }

  public void execute() {
    Codecast codecast = new Codecast(title, publicationDate);
    Context.codecastGateway.save(codecast);
  }
}
