package org.recefi.exam.client;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.recefi.exam.ActionEnum;
import org.recefi.exam.Model;
import org.recefi.exam.Msg;
import org.recefi.exam.Pnt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CController {
    InetAddress ip = null;
    int port = 7474;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
    Model m = Model.build();
    Gson gson = new Gson();

    @FXML
    private Button connectBtn;
    @FXML
    private Label pntLbl;
    @FXML
    private Pane mainPanel;

    void updLbl() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                pntLbl.setText("(" + m.getPnt().getX() + ", " + m.getPnt().getY() + ")");
            }
        });
    }
    void rePntPnt() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                mainPanel.getChildren().clear();

                Canvas canvas = new Canvas(mainPanel.getWidth(), mainPanel.getHeight());
                mainPanel.getChildren().add(canvas);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setLineWidth(3);
                gc.setStroke(Color.RED);

                Pnt pnt = m.getPnt();
                if (pnt != null) {
                    gc.strokeLine(pnt.getX(), 0, pnt.getX(), canvas.getHeight());
                    gc.strokeLine(0, pnt.getY(), canvas.getWidth(), pnt.getY());
                }
            }
        });
    }

    void reqPnt() {
        try {
            String s_msg = gson.toJson(new Msg(ActionEnum.NOTIFY));
            dos.writeUTF(s_msg);
        } catch (IOException ex) {
            Logger.getLogger(CController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void sendPnt(Pnt pnt) {
        try {
            String s_msg = gson.toJson(new Msg(ActionEnum.CHANGE_PNT, pnt));
            dos.writeUTF(s_msg);
        } catch (IOException ex) {
            Logger.getLogger(CController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void connect() {
        connectBtn.setDisable(true);
        try {
            ip = InetAddress.getLocalHost();
            sock = new Socket(ip, port);
            m.addObserver(this::updLbl);
            m.addObserver(this::rePntPnt);

            new Thread(() -> {
                try {
                    dis = new DataInputStream(sock.getInputStream());
                    dos = new DataOutputStream(sock.getOutputStream());
                    while (true) {
                        String str_msg = dis.readUTF();
                        System.out.println("Server: " + str_msg);
                        Msg msg = gson.fromJson(str_msg, Msg.class);
                        if (msg.act == ActionEnum.NOTIFY)
                            reqPnt();
                        else if (msg.act == ActionEnum.CHANGE_PNT)
                            m.setPnt(msg.pnt);
                        else
                            throw new IOException(str_msg);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        } catch (IOException ex) {
            Logger.getLogger(CController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void movePnt(javafx.scene.input.MouseEvent evt) {
        Pnt clickPnt = new Pnt(evt.getX(), evt.getY());
        sendPnt(clickPnt);
    }
}