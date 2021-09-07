package javacasestudy.socketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
  private final int port;
  private final SocketService service;
  private final ExecutorService executor;
  private final ServerSocket socket;
  private boolean isRunning;

  public SocketServer(int port, SocketService service) throws IOException {
    this.port = port;
    this.service = service;
    executor = Executors.newFixedThreadPool(4);
    socket = new ServerSocket(port);
  }

  public int getPort() {
    return port;
  }

  public SocketService getService() {
    return service;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public void start(AsyncCompletion completion) {
    executor.execute(() -> {
      try {
        service.serve(socket.accept());
      } catch (IOException e) {
        if (isRunning)
          e.printStackTrace();
      }
      isRunning = true;
      completion.onComplete();
    });
  }

  public void stop() {
    executor.shutdown();
    isRunning = false;
  }

  public interface AsyncCompletion {
    void onComplete();
  }
}
