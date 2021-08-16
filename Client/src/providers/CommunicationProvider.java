package providers;

import java.io.IOException;
import java.util.Scanner;

public class CommunicationProvider {
  
  Scanner scanner = new Scanner(System.in);
  ClientProvider provider = new ClientProvider();
  String user;
  String room;

  public void main() throws IOException {
    provider.start("127.0.0.1",9999);
    this.user = this.getInput("What's yout nickname? ");
    responseInterpreter(this.provider.sendMessage(this.user, null, "/first_connection"));
    String message = "";
    while(!message.equals("/exit")) {
      if(this.room != null) {
        message = this.getInput("Message: ");
        responseInterpreter(this.provider.sendMessage(this.user, this.room, message));
      }else {
        this.createRoom();
      }
    }
  }

  public String getInput(String alert) {
    System.out.print(alert);
    String message = this.scanner.nextLine();
    return message;
  }

  public void createRoom() {
    try {
      String response = this.provider.sendMessage(this.user, null, "/list_all_rooms");
      this.responseInterpreter(response);
      String[] roomParams = this.getInput("Select a room, or type \'/create_room room-name\' to create a new room.\n").split(" ");
      switch(roomParams[0]) {
        case "/create_room":
          this.room = roomParams[1];
          responseInterpreter(this.provider.sendMessage(this.user, this.room, "/create_room " + this.room));
          break;
        default: 
          this.room = roomParams[0];
          responseInterpreter(this.provider.sendMessage(this.user, this.room, "/enter_room " + this.room));
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void responseInterpreter(String response) {
    HeaderProvider headerProvider = new HeaderProvider(response);
    this.room = headerProvider.getRoom();
    if(this.user == headerProvider.getUser()){
      System.out.println(headerProvider.getUser() + " -> " + headerProvider.getMessage());
    }else {
      System.out.println("Server -> " + headerProvider.getMessage());
    }
  }
}
