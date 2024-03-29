package org.recefi.exam.server;

import com.google.gson.Gson;
import org.recefi.exam.ActionEnum;
import org.recefi.exam.Model;
import org.recefi.exam.Msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SController implements Runnable {
    int id;
    Socket sock;
    DataInputStream dis;
    DataOutputStream dos;
    Model m = Model.build();
    Gson gson = new Gson();

    public SController(int id, Socket sock) {
        this.id = id;
        this.sock = sock;
        try {
            dos = new DataOutputStream(sock.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void notifyClient() {
        try {
            Msg msg = new Msg(ActionEnum.NOTIFY);
            String str_msg = gson.toJson(msg);
            dos.writeUTF(str_msg);
        } catch (Exception ex) {
            Logger.getLogger(SController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void respondClient() {
        try {
            Msg msg = new Msg(ActionEnum.CHANGE_PNT, m.getPnt());
            String str_msg = gson.toJson(msg);
            dos.writeUTF(str_msg);
        } catch (IOException ex) {
            Logger.getLogger(SController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(sock.getInputStream());
            System.out.println("Client" + id + " thread started");

            notifyClient();
            while (true) {
                String str_msg = dis.readUTF();
                System.out.println("Client" + id + ": " + str_msg);
                Msg msg = gson.fromJson(str_msg, Msg.class);
                if (msg.act == ActionEnum.NOTIFY)
                    respondClient();
                else if (msg.act == ActionEnum.CHANGE_PNT)
                    m.setPnt(msg.pnt);
                else
                    throw new IOException(str_msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(SController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
