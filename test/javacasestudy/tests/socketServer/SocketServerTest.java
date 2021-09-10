package javacasestudy.tests.socketServer;

import javacasestudy.socketServer.SocketServer;
import javacasestudy.socketServer.SocketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static javacasestudy.tests.TestUtils.randomStringLowercased;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SynchronizeOnNonFinalField")
final public class SocketServerTest {
  private static final String HOST = "localhost";
  private static final int PORT = 16192;
  private SocketServer server;

  @AfterEach
  void tearDown() throws Exception {
    server.stop();
  }

  @Nested
  protected class WithClosingSocketService {
    private static class ClosingSocketService extends TestSocketService {
      protected int numberOfConnections;

      public int getNumberOfConnections() {
        return numberOfConnections;
      }

      protected void handle(Socket socket) {
        numberOfConnections += 1;
      }
    }

    private ClosingSocketService closingService;

    @BeforeEach
    void setUp() throws Exception {
      closingService = new ClosingSocketService();
      server = new SocketServer(PORT, closingService);
    }

    @Test
    public void instantiate() {
      assertEquals(PORT, server.getPort());
      assertEquals(closingService, server.getSocketService());
    }

    @Test
    public void canStartAndStopServer() throws Exception {
      server.start();

      assertTrue(server.isRunning());

      server.stop();

      assertFalse(server.isRunning());
    }

    @Test
    public void acceptsAnIncomingConnection() throws Exception {
      server.start();

      new Socket(HOST, PORT);
      synchronized (closingService) {
        closingService.wait();
      }

      assertEquals(1, closingService.getNumberOfConnections());
    }

    @Test
    public void acceptsMultipleConnections() throws Exception {
      server.start();

      new Socket(HOST, PORT);
      synchronized (closingService) {
        closingService.wait();
      }
      new Socket(HOST, PORT);
      synchronized (closingService) {
        closingService.wait();
      }

      assertEquals(2, closingService.getNumberOfConnections());
    }
  }

  @Nested
  protected class WithReadingSocketService {
    private static class ReadingSocketService extends TestSocketService {
      private String message;

      public String getMessage() {
        return message;
      }

      @Override
      protected void handle(Socket socket) throws IOException {
        message = readLineFrom(socket);
      }
    }

    private ReadingSocketService readingService;

    @BeforeEach
    void setUp() throws Exception {
      readingService = new ReadingSocketService();
      server = new SocketServer(PORT, readingService);
    }

    @Test
    public void canSendData() throws Exception {
      String expectedString = randomStringLowercased();
      server.start();

      writeStringTo(new Socket(HOST, PORT), expectedString + "\n");
      synchronized (readingService) {
        readingService.wait();
      }

      assertEquals(expectedString, readingService.getMessage());
    }
  }

  @Nested
  protected class WithWritingSocketService {
    private static class WritingSocketService extends TestSocketService {
      private final String message = randomStringLowercased();

      public String getMessage() {
        return message;
      }

      @Override
      protected void handle(Socket socket) throws IOException {
        writeStringTo(socket, message + "\n");
      }
    }

    private WritingSocketService writingService;

    @BeforeEach
    void setUp() throws Exception {
      writingService = new WritingSocketService();
      server = new SocketServer(PORT, writingService);
    }

    @Test
    public void canReceiveData() throws Exception {
      server.start();

      Socket socket = new Socket(HOST, PORT);
      synchronized (writingService) {
        writingService.wait();
      }
      String expected = readLineFrom(socket);

      assertEquals(writingService.getMessage(), expected);
    }
  }

  @Nested
  protected class WithEchoSocketService {
    private static class EchoSocketService extends TestSocketService {
      @Override
      public void handle(Socket socket) throws IOException {
        writeStringTo(socket, readLineFrom(socket));
      }
    }

    @BeforeEach
    void setUp() throws Exception {
      server = new SocketServer(PORT, new EchoSocketService());
    }

    @Test
    public void canEcho() throws Exception {
      String expectedString = randomStringLowercased();
      server.start();

      final Socket socket = new Socket(HOST, PORT);
      writeStringTo(socket, expectedString + "\n");
      String actualString = readLineFrom(socket);

      assertEquals(expectedString, actualString);
    }
  }

  private static abstract class TestSocketService implements SocketService {
    public void serve(Socket socket) {
      try {
        handle(socket);
        synchronized (this) {
          notify();
        }
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    protected abstract void handle(Socket socket) throws IOException;
  }

  private static String readLineFrom(Socket socket) throws IOException {
    return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
  }

  private static void writeStringTo(Socket socket, String string) throws IOException {
    OutputStream os = socket.getOutputStream();
    os.write(string.getBytes());
    os.flush();
  }
}
