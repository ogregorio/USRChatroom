package providers;

public class InterpreterProvider {
  
  HeaderProvider headerProvider;
  RoomProvider roomProvider;

  String user;
  String room;

  public String interpreter(String message) {
      try {
        this.headerProvider = new HeaderProvider(message);
      }catch (Exception e) {
        return "Bad request.";
      }
      this.roomProvider = new RoomProvider();
      this.user = this.headerProvider.getUser();
      this.room = this.headerProvider.getRoom();
      String result = this.analyzer(this.headerProvider.getMessage());
      this.headerProvider = new HeaderProvider(this.user, this.room, result);
      return this.headerProvider.toString();
  }

  public String analyzer(String message) {
    String[] messageSplited = message.split(" ");
    try {
      switch (messageSplited[0]) {
        case "/first_connection": return "FIRST_TIME_OF:" + this.user;
        case "/create_room": return createRoom(messageSplited[1]);
        case "/enter_room": return enterRoom(messageSplited[1]);
        case "/destroy_room": return destroyRoom(messageSplited[1]);
        case "/whereiam": return this.room;
        case "/list_all_rooms": return listAllRooms();
        case "/list_users": return listUsers(this.room);
        case "/exit_room": return exitRoom(messageSplited[1]);
        case "/send_to": return send(messageSplited[1]);
        default: return "OOOPPS! COMMAND NOT RECOGINIZED!";
      }
    }catch(Exception e){
      this.log(this.user + " try to launch a invalid command [" + message + "]");
      e.printStackTrace();
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
      this.log(this.user + " destroyed room " + roomName);
      return (roomProvider.detroyRoom(roomName, this.user)) ? "Room " + roomName + " was Destroyed.":"Failed to destroy " + roomName + " room.";
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
