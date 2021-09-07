package javacasestudy.socketServer;

import java.io.IOException;
import java.net.Socket;

public interface SocketService {
  void serve(Socket socket) throws IOException;
  int getNumberOfConnections();
}
