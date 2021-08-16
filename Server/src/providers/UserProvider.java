package providers;

import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import models.User;

public class UserProvider {
  private static Map<String,User> users = new TreeMap<String,User>(); 

  public User getUser(String username) {
    return users.get(username);
  }

  public void addUser(String username, Socket socket) {
    users.put(username, new User(username, socket));
  }
  
}
