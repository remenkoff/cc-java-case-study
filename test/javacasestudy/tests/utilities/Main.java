package javacasestudy.tests.utilities;

import javacasestudy.socketServer.SocketServer;
import javacasestudy.tests.TestSetup;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    TestSetup.setupContext();
    run();
  }

  private static void run() {
    try {
      (new SocketServer(8080, socket -> {
        try {
          String response = makeResponse(getContent());
          socket.getOutputStream().write(response.getBytes());
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

  private static String getContent() {
    return "stub";
  }
}
