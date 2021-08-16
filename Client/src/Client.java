import java.io.IOException;

import providers.CommunicationProvider;

public class Client {

  public static void main(String[] args) throws IOException {
    CommunicationProvider provider = new CommunicationProvider();
    provider.main();
  }
  
}
