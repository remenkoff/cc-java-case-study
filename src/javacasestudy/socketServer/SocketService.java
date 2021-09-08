package javacasestudy.socketServer;

import java.net.Socket;

public interface SocketService {
  void serve(Socket socket);
}
