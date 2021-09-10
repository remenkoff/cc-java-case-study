package view;

import javacasestudy.view.ViewTemplate;
import org.junit.jupiter.api.Test;

import static javacasestudy.tests.TestUtils.randomStringLowercased;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTemplateTest {
  @Test
  public void noReplacement() {
    String content = randomStringLowercased();
    ViewTemplate template = new ViewTemplate(content);

    assertEquals(template.getContent(), content);
  }

  @Test
  public void simpleReplacement() {
    String fix = randomStringLowercased();
    String token = randomStringLowercased();
    String wrapped = "${" + token + "}";
    String content = fix + wrapped + fix;
    String string = randomStringLowercased();
    String expected = fix + string + fix;
    ViewTemplate template = new ViewTemplate(content);

    template.replace(token, string);

    assertEquals(expected, template.getContent());
  }
}
