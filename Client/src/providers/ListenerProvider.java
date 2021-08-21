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
      if (this.user.equals(headerProvider.getUser())) {
        System.out.println(headerProvider.getUser() + " -> " + headerProvider.getMessage());
      } else {
        System.out.println(this.user + " -> " + headerProvider.getMessage());
      }
    }
  }