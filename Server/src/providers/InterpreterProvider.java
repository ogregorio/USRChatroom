package providers;

import java.net.Socket;

public class InterpreterProvider {
  
  private UserProvider userProvider = new UserProvider();
  private RoomProvider roomProvider = new RoomProvider();
  private HeaderProvider headerProvider;

  String user;
  String room;

  public String analyzer(String message, Socket socket) {
      try {
        this.headerProvider = new HeaderProvider(message);
      }catch (Exception e) {
        return "Bad request.";
      }
      this.user = this.headerProvider.getUser();
      this.room = this.headerProvider.getRoom();
      String result = this.interpreter(this.headerProvider.getMessage(), socket);
      this.headerProvider = new HeaderProvider(this.user, this.room, result);
      return this.headerProvider.toString();
  }

  public String interpreter(String message, Socket socket) {
    String[] messageSplited = message.split(" ");
    try {
      switch (messageSplited[0]) {
        case "/first_connection": return createUser(this.user, socket);
        case "/create_room": return createRoom(messageSplited[1]);
        case "/enter_room": return enterRoom(messageSplited[1]);
        case "/destroy_room": return destroyRoom(messageSplited[1]);
        case "/whereiam": return this.room;
        case "/list_all_rooms": return listAllRooms();
        case "/list_users": return listUsers(this.room);
        case "/exit_room": return exitRoom(messageSplited[1]);
        case "/send": return send(messageSplited[1]);
        default: return "OOOPPS! COMMAND NOT RECOGINIZED!";
      }
    }catch(Exception e){
      this.log(this.user + " try to launch a invalid command [" + message + "]");
      return "Ops! Command not recognized! Try again!";
    }
  }

  public String createRoom(String roomName) {
    this.log(this.user + " try to create a room with name " + roomName);
    return (roomProvider.createRoom(roomName, this.user))? roomName + " room was created.":"Failed to create room, check if it already.";
  }

  public String enterRoom(String roomName) {
    this.log(this.user + " try to enter in room " + roomName);
    if (roomProvider.enterRoom(roomName, this.user)) {
      this.room = roomName;
      this.log(this.user + " enter in room " + roomName);
      return "Entered in room " + roomName + ".";
    }else{
      this.log(this.user + " failed to enter in room " + roomName);
      return "Failed to enter in room " + roomName + ".";
    }
  }

  public String exitRoom(String roomName) {
    if (roomProvider.exitRoom(roomName, this.user)) {
      this.room = "null";
      this.log(this.user + " exit from " + roomName);
      return "Exited from room " + roomName + ".";
    }else{
      this.log(this.user + " failed to exit from " + roomName);
      return "Failed to exit from room " + roomName + ".";
    }
  }

  public String destroyRoom(String roomName) {
    if(!this.room.equals(roomName)){
      if(roomProvider.detroyRoom(roomName, this.user)){
        this.log(this.user + " destroyed room " + roomName);
        return "Room " + roomName + " was Destroyed.";
      }else {
        this.log(this.user + " failed when try to destroy room " + roomName);
        return "Failed to destroy " + roomName + " room.";
      }
    }else{
      this.log(this.user + " try to destroy room " + roomName);
      return "You are inside the room " + roomName + ", get out of it and try to destroy it again.";
    } 
  }

  public String listUsers(String roomName) {
    this.log(this.user + " list all users from " + roomName);
    return "Users in " + roomName + ": " +roomProvider.listUsersAtRoom(roomName);
  }

  public String listAllRooms(){
    this.log(this.user + " list all rooms");
    return "Available rooms: " + roomProvider.listAllRooms();
  }

  public String send(String message){
    this.log(this.user + " send a message to " + this.room);
    roomProvider.sendMessage(this.user, this.room, message);
    return message;
  }

  public String createUser(String user, Socket socket){
    this.log(this.user + " was logged for the first time with socket adreess" + socket.getLocalAddress().toString());
    userProvider.addUser(user, socket);
    return "First login.";
  }

  public void log(String message) {
    System.out.println(
              new StringBuilder()
                  .append("Log (")
                  .append(java.util.Calendar.getInstance().getTime().toString())
                  .append(") ")
                  .append(message)
                  .append(".")
                  .toString()
    );
  }
}
