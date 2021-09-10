package javacasestudy.tests.utilities;

import javacasestudy.Context;
import javacasestudy.socketServer.SocketServer;
import javacasestudy.tests.TestSetup;
import javacasestudy.useCases.presentCodecast.PresentCodecastUseCase;
import javacasestudy.useCases.presentCodecast.PresentableCodecast;
import javacasestudy.view.ViewTemplate;

import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    TestSetup.setupContext();
    TestSetup.setupData();
    run();
  }

  private static void run() {
    try {
      (new SocketServer(8080, socket -> {
        try {
          String response = makeResponse(getBody());
          socket.getOutputStream().write(response.getBytes());
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      })).start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String makeResponse(String content) {
    String format = """
      HTTP/1.1 200 OK
      Content-Type: text/html; charset=UTF-8
      Content-Length: %s
      Connection: close
            
      %s
      """;
    return String.format(format, content.length(), content);
  }

  private static String getBody() {
    PresentCodecastUseCase useCase = new PresentCodecastUseCase();
    List<PresentableCodecast> codecasts = useCase.presentCodecasts(Context.userGateway.findUserByName("Ozzy"));

    try {
      ViewTemplate indexTemplate = ViewTemplate.createFromResource("html/index.html");
      StringBuilder codecastLines = new StringBuilder();

      for (PresentableCodecast codecast : codecasts) {
        ViewTemplate codecastTemplate = ViewTemplate.createFromResource("html/codecast.html");
        codecastTemplate.replace("title", codecast.title());
        codecastTemplate.replace("pubDate", codecast.pubDate());
        codecastTemplate.replace("thumbnail", "https://dcaq3g2sjcqz2.cloudfront.net/clean-code-65.gif");
        codecastTemplate.replace("author", "Mr. Crowley");
        codecastTemplate.replace("duration", "1 hour 4 minutes");
        codecastTemplate.replace("licenseOptions", "License options go here..");
        codecastTemplate.replace("contentActions", "Content options go here..");
        codecastLines.append(codecastTemplate.getContent());
      }

      String codecastsToken = "codecasts";
      indexTemplate.replace(codecastsToken, codecastLines.toString());

      return indexTemplate.getContent();
    } catch (IOException e) {
      e.printStackTrace();
      return "stub";
    }
  }
}
