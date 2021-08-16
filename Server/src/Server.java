import providers.ServerProvider;

public class Server {
  public static void main(String[] args) {
    ServerProvider provider = new ServerProvider();
    try {
      provider.start(9999);
    }catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
    
  }
} 