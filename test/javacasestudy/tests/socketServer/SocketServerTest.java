package javacasestudy.tests.socketServer;

import javacasestudy.socketServer.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;

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
      server.stop();

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
      server.stop();

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
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        message = br.readLine();
      }
    }

    private ReadingSocketService readingService;

    @BeforeEach
    void setUp() throws Exception {
      readingService = new ReadingSocketService();
      server = new SocketServer(PORT, readingService);
    }

    @Test
    public void canSendAndReceiveData() throws Exception {
      String expected = "Knock-knock.";
      server.start();

      OutputStream os = (new Socket(HOST, PORT)).getOutputStream();
      os.write(expected.getBytes());
      os.close();
      synchronized (readingService) {
        readingService.wait();
      }
      server.stop();

      assertEquals(expected, readingService.getMessage());
    }
  }

  @Nested
  protected class WithWritingSocketService {
    private static class WritingSocketService extends TestSocketService {
      private final String message = "Who's There?";

      public String getMessage() {
        return message;
      }

      @Override
      protected void handle(Socket socket) throws IOException {
        OutputStream os = socket.getOutputStream();
        os.write(message.getBytes());
      }
    }

    private WritingSocketService writingService;

    @BeforeEach
    void setUp() throws Exception {
      writingService = new WritingSocketService();
      server = new SocketServer(PORT, writingService);
    }

    @Test
    public void canSendAndReceiveData() throws Exception {
      server.start();

      Socket socket = new Socket(HOST, PORT);
      synchronized (writingService) {
        writingService.wait();
      }
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String expected = br.readLine();
      server.stop();

      assertEquals(writingService.getMessage(), expected);
    }
  }

  private static abstract class TestSocketService implements SocketService {
    @Override
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
}
