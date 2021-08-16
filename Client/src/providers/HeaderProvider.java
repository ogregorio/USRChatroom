package providers;

public class HeaderProvider {
    String user;
    String room;
    String message;
    String separator = "§";

    public HeaderProvider(String user, String room, String message) {
      this.user = user;
      this.room = room;
      this.message = message;
    }

    public HeaderProvider(String string) {
      try {
        String[] parts = string.split(separator);
        this.user = parts[0];
        this.room = parts[1];
        this.message = parts[2];
      } catch (Exception e) {
        System.out.println("An error occurred while parsing your message, try again, please!");
      }
    }

    public String getUser() {
      return this.user;
    }

    public String getRoom() {
      return this.room;
    }

    public String getMessage() {
      return this.message;
    }

    @Override
    public String toString() {
      return new StringBuilder()
                .append(this.user)
                .append(this.separator)
                .append(this.room)
                .append(this.separator)
                .append(this.message)
                .toString();
    }
}
