package providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProvider {
  private ServerSocket serverSocket;

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    while (true)
      new ClientHandler(serverSocket.accept()).start();
  }

  public void stop() throws IOException {
    serverSocket.close();
  }

  private static class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private UserProvider userProvider;

    public ClientHandler(Socket socket) {
      this.userProvider = new UserProvider();
      this.clientSocket = socket;
    }

    public void run() {
      try {
        out = new PrintWriter(clientSocket.getOutputStream(), true); 
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          InterpreterProvider interpreter = new InterpreterProvider();
          String interpreted = interpreter.interpreter(inputLine);
          this.checkAccess(interpreted, clientSocket);
          out.println(interpreted);
        }

        in .close();
        out.close();
        clientSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    public void checkAccess(String interpreted, Socket socket) throws IOException {
      String[] values = interpreted.split(":");
      if(values[0].equals("FIRST_TIME_OF")){
        userProvider.addUser(values[1], socket);
      }
    }
  }

}