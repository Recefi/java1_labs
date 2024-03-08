
package labs.server;

import labs.Model;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import labs.Pnt;

public class Server {
    Model m = Model.build();
    
    ExecutorService service = Executors.newCachedThreadPool();
    ArrayList<SClient> clients = new ArrayList<>();
    
    void notifyAllClients() {
        for (SClient client : clients)
            client.notifyClient();
    }
    
    public void StartServer() {
        int port = 7474;
        InetAddress ip = null;
        ServerSocket sSock;
        
        int numClients = 0;
        
        try {
            m.setPnt(new Pnt(0, 0));
            m.addObserver(this::notifyAllClients);  // m.addObserver(() -> { notifyAllClients(); });
            
            ip = InetAddress.getLocalHost();
            sSock = new ServerSocket(port, 0, ip);
            System.out.println("Server started");
            
            while (true) {
                Socket cSock = sSock.accept();
                System.out.println("Client " + ++numClients + " connected. Port: " + cSock.getPort());
                
                SClient client = new SClient(numClients, cSock);
                clients.add(client);
                service.submit(client);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        new Server().StartServer();
    }
    
}
