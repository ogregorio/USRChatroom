package providers;

import java.io.BufferedReader;

public class ListenerProvider extends Thread {

    private BufferedReader in;
    private String user;

    public ListenerProvider(BufferedReader in, String user) {
      this.in = in;
      this.user = user;
    }

    public void run() {
      while (true) {
        try {
          responseInterpreter(this.in.readLine());
        }catch(Exception e) {}
      }
    }

    public void responseInterpreter(String response) {
      HeaderProvider headerProvider = new HeaderProvider(response);
      this.syncWithServer(headerProvider);
      if (!this.user.equalsIgnoreCase(headerProvider.getUser())) {
        System.out.println(headerProvider.getUser() + " -> " + headerProvider.getCommand());
      } else {
        System.out.println(this.user + " -> " + headerProvider.getCommand());
      }
    }

    public void syncWithServer(HeaderProvider header){
      if(!header.getRoom().equalsIgnoreCase("null")){
        GlobalStateProvider.setRoom(header.getRoom());
      }
    }
  }