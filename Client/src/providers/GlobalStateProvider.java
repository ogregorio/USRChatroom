package providers;

public class GlobalStateProvider {
    private static String user;
    private static String room;

    public static void setUser(String username) {
      user = username;
    }

    public static void setRoom(String roomname) {
      room = roomname;
    }

    public static String getUser() {
      return user;
    }

    public static String getRoom() {
      return room;
    }
}
