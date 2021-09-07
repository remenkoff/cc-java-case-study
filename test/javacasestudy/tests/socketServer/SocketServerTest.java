package javacasestudy.tests.socketServer;

import javacasestudy.socketServer.SocketServer;
import javacasestudy.socketServer.SocketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static javacasestudy.tests.TestUtils.randomIntFromZeroTo;
import static org.junit.jupiter.api.Assertions.*;

final public class SocketServerTest {
  private static final int MAX_PORT_VALUE = 65353;
  private int port;
  private SocketService service;
  private SocketServer server;

  @BeforeEach
  void setUp() throws IOException {
    port = randomIntFromZeroTo(MAX_PORT_VALUE);
    service = new FakeSocketService();
    server = new SocketServer(port, service);
  }

  @AfterEach
  void tearDown() {
    if (server.isRunning())
      server.stop();
  }

  @Test
  public void instantiate() {
    assertEquals(port, server.getPort());
    assertEquals(service, server.getService());
  }

  @Test
  public void canStartAndStopServer() {
    server.start(() -> assertTrue(server.isRunning()));

    server.stop();
    assertFalse(server.isRunning());
  }

  @Test
  public void acceptsAnIncomingConnection() {
    server.start(() -> assertEquals(1, service.getNumberOfConnections()));
  }

  private static class FakeSocketService implements SocketService {
    private int numberOfConnections;

    @Override
    public void serve(Socket socket) throws IOException {
      numberOfConnections += 1;
      socket.close();
    }

    @Override
    public int getNumberOfConnections() {
      return numberOfConnections;
    }
  }
}
