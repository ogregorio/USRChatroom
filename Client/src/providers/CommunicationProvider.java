package providers;

import java.io.IOException;
import java.util.Scanner;

public class CommunicationProvider {
  
  Scanner scanner = new Scanner(System.in);
  ClientProvider provider;
  String user;
  String room;

  public void main() throws IOException {
    this.user = this.getInput("What's yout nickname? ");
    this.provider = new ClientProvider("127.0.0.1", "9999", this.user);
    this.provider.start();
    this.provider.sendMessage(this.user, null, "/first_connection");
    String message = "";
    while(!message.equals("/exit")) {
      message = this.getInput("");
      this.provider.sendMessage(this.user, this.room, message);
    }
  }

  public String getInput(String alert) {
    System.out.print(alert);
    String message = this.scanner.nextLine();
    return message;
  }

  public void responseInterpreter(String response) {
    HeaderProvider headerProvider = new HeaderProvider(response);
    this.room = headerProvider.getRoom();
    if(this.user.equals(headerProvider.getUser())){
      System.out.println(headerProvider.getUser() + " -> " + headerProvider.getMessage());
    }else {
      System.out.println("Server -> " + headerProvider.getMessage());
    }
  }
}
