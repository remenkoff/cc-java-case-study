package javacasestudy.socketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketServer {
  private final int port;
  private final SocketService socketService;
  private final ServerSocket serverSocket;
  private final ExecutorService executorService;
  private boolean isRunning;

  public SocketServer(int port, SocketService socketService) throws IOException {
    this.port = port;
    this.socketService = socketService;
    serverSocket = new ServerSocket(port);
    executorService = Executors.newFixedThreadPool(4);
  }

  public int getPort() {
    return port;
  }

  public SocketService getSocketService() {
    return socketService;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public void start() {
    executorService.execute(() -> {
      try {
        while (isRunning) {
          final Socket serviceSocket = serverSocket.accept();
          executorService.execute(() -> socketService.serve(serviceSocket));
        }
      } catch (Exception e) {
        if (isRunning) {
          e.printStackTrace();
        }
        System.out.println("SocketServer: stopped by catch");
      }
    });

    isRunning = true;
    System.out.printf("SocketServer: started on port %s%n", port);
  }

  public void stop() throws Exception {
    isRunning = false;
    serverSocket.close();
    executorService.shutdown();
    if (!executorService.awaitTermination(1024, TimeUnit.MILLISECONDS))
      executorService.shutdownNow();
  }
}
