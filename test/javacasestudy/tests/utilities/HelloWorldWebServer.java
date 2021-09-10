package javacasestudy.tests.utilities;

import javacasestudy.socketServer.SocketServer;
import javacasestudy.socketServer.SocketService;

import java.io.IOException;
import java.net.Socket;

public class HelloWorldWebServer implements SocketService {
  public static void main(String[] args) throws IOException {
    SocketServer server = new SocketServer(8080, new HelloWorldWebServer());
    server.start();
  }

  @Override
  public void serve(Socket socket) {
    try {
      String rawResponse = rawHeaders(rawBody().length()) + rawBody();
      socket.getOutputStream().write(rawResponse.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String rawHeaders(int contentLength) {
    String format = """
      HTTP/1.1 200 OK
      Content-Type: text/html; charset=UTF-8
      Content-Length: %s
      Accept-Ranges: bytes
      Connection: close
            
      """;
    return String.format(format, contentLength);
  }

  private String rawBody() {
    return """
      <html>
      <head>
        <title>HelloWorldWebServer works!</title>
      </head>
      <body style="text-align: center">
        <h1>Hello from Java Web Server</h1>
        <h2>¯\\_(ツ)_/¯</h2>
      </body>
      </html>
      """;
  }
}
