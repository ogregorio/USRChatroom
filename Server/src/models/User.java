package models;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import providers.HeaderProvider;

public class User {
  private String username;
  private Socket clientSocket;
  private PrintWriter out;

  public User(String username, Socket clientSocket) {
    this.username = username;
    this.clientSocket = clientSocket;
  }

  public void notify(HeaderProvider message) {
    try {
      System.out.println(message);
      out = new PrintWriter(this.clientSocket.getOutputStream(), true);
      out.println(message.toString());
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getUserName() {
    return this.username;
  }
}
