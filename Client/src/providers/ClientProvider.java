package providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProvider {

  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in ;
  private String ip, port, user;

  public ClientProvider(String ip, String port, String user){
    this.ip = ip;
    this.port = port;
    this.user = user;
  }

  public void start() throws IOException {
    clientSocket = new Socket(this.ip, Integer.parseInt(this.port));
    out = new PrintWriter(clientSocket.getOutputStream(), true); in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    new ListenerProvider(in,user).start();
  }

  public void sendMessage(String user, String room, String msg) throws IOException {
    HeaderProvider headerProvider = new HeaderProvider(user, room, msg);
    out.println(headerProvider.toString());
  }

  public void stopConnection() throws IOException {
    in .close();
    out.close();
    clientSocket.close();
  }

}