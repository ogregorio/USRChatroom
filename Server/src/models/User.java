package models;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

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
      out = new PrintWriter(this.clientSocket.getOutputStream(), true);
      out.println(message.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getUserName() {
    return this.username;
  }
}
