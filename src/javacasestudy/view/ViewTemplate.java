package javacasestudy.view;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ViewTemplate {
  private String content;

  public ViewTemplate(String content) {
    this.content = content;
  }

  public static ViewTemplate createFromResource(String templateResource) throws IOException {
    URL url = ClassLoader.getSystemResource(templateResource);
    byte[] bytes = Files.readAllBytes(Path.of(url.getPath()));
    return new ViewTemplate(new String(bytes));
  }

  public String getContent() {
    return content;
  }

  public void replace(String token, String string) {
    String wrappedToken = "${" + token + "}";
    content = content.replace(wrappedToken, string);
  }
}
