package providers;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RoomProvider {
    public static Map<String,ArrayList<String>> rooms = new TreeMap<String,ArrayList<String>>();
    private UserProvider userProvider = new UserProvider();
    
    public RoomProvider() {
        rooms.put("default", new ArrayList<String>());
    }

    public boolean createRoom(String roomName, String username) {
        ArrayList<String> usernames = new ArrayList<String>();
        usernames.add(username);
        if(!rooms.containsKey(roomName)) {
          rooms.put(roomName, usernames);
          return true;
        }else {
          return false;
        }
    }

    public boolean enterRoom(String roomName, String username) {
      if(rooms.containsKey(roomName)) {
        ArrayList<String> usernames = rooms.get(roomName);
        if(!usernames.contains(username)) {
          usernames.add(username);
          rooms.put(roomName, usernames);
        }
        return true;
      }else {
        return false;
      }
    }

    public boolean exitRoom(String roomName, String username) {
      if(rooms.containsKey(roomName)) {
        Boolean added = rooms.get(roomName).add(username);
        return added;
      }else {
        return false;
      }
    }

    public boolean detroyRoom(String roomName, String username) {
      if(rooms.containsKey(roomName) && rooms.get(roomName).get(0).equals(username)) {
        rooms.remove(roomName);
        return true;
      }else {
        return false;
      }
    }

    public String listAllRooms() {
      String roomsList = "";
      for (Map.Entry<String, ArrayList<String>> entry : rooms.entrySet()) {
        roomsList += entry.getKey() + " ";
      }
      return roomsList; 
    }

    public String listUsersAtRoom(String roomName){
      String usersList = "";
      try {
        ArrayList<String> users = rooms.get(roomName);
        for (String entry : users) {
          usersList += entry + " - ";
        }
        return usersList;
      }
      catch (NullPointerException e) {
        return "There are no users in this room.";
      }
    }

    public boolean sendMessage(String sender, String roomName, String message) {
      ArrayList<String> users = rooms.get(roomName);
      for (String username : users) {
        userProvider.getUser(username).notify(new HeaderProvider(sender, roomName, message));
      }
      return true;
    }
}
