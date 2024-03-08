/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labs.server;

import labs.MBuilder;
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
    Model m = MBuilder.build();
    
    ExecutorService service = Executors.newCachedThreadPool();
    ArrayList<SClient> clients = new ArrayList<>();
    
    void notifyAllClients() {
        for (SClient client : clients)
            client.notifyClient();
    }
    
    public void StartServer() {
        int port = 7474;
        InetAddress ip = null;
        ServerSocket ss;
        
        int numClients = 0;
        
        try {
            m.setPnt(new Pnt(0, 0));
            m.addObserver(() -> { notifyAllClients(); });
            
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Server started");
            
            while (true) {
                Socket cs = ss.accept();
                System.out.println("Client " + ++numClients + " connected. Port: " + cs.getPort());
                
                SClient client = new SClient(numClients, cs);
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
