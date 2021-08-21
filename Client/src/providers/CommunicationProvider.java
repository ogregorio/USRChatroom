package providers;

import java.io.IOException;
import java.util.Scanner;

public class CommunicationProvider {
  
  private Scanner scanner = new Scanner(System.in);
  private ClientProvider provider;

  public void main() throws IOException {
    GlobalStateProvider.setUser(this.getInput("What's yout nickname? "));
    this.provider = new ClientProvider("127.0.0.1", "9999", GlobalStateProvider.getUser());
    this.provider.start();
    this.provider.sendMessage(GlobalStateProvider.getUser(), null, "/first_connection");
    String message = "";
    while(!message.equals("/exit")) {
      message = this.getInput("");
      this.provider.sendMessage(
        GlobalStateProvider.getUser(),
        GlobalStateProvider.getRoom(),
        message
      );
    }
  }

  public String getInput(String alert) {
    System.out.print(alert);
    String message = this.scanner.nextLine();
    return message;
  }
}
