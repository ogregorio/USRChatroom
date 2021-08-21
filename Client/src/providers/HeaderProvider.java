package providers;

public class HeaderProvider {
    private Integer count;
    private String user;
    private String room;
    private String command;
    private String separator = "§";

    public HeaderProvider(String user, String room, String command) {
      this.user = user;
      this.room = room;
      this.command = command;
    }

    public HeaderProvider(String string) {
      try {
        String[] parts = string.split(separator);
        this.user = parts[0];
        this.room = parts[1];
        this.command = parts[2];
        this.count = 0;
      } catch (Exception e) {
        this.count++;
        System.out.println("An error occurred while parsing your message, try again, please!");
        if(count == 5){
          System.exit(1);
        }
      }
    }

    public String getUser() {
      return this.user;
    }

    public String getRoom() {
      return this.room;
    }

    public String getCommand() {
      return this.command;
    }

    @Override
    public String toString() {
      return new StringBuilder()
                .append(this.user)
                .append(this.separator)
                .append(this.room)
                .append(this.separator)
                .append(this.command)
                .toString();
    }
    
}
