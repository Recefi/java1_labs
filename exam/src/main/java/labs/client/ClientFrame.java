
package labs.client;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import labs.ActionEnum;
import labs.Msg;
import labs.Pnt;
import labs.Model;

public class ClientFrame extends javax.swing.JFrame {
    InetAddress ip = null;
    int port = 7474;
    Socket sock;
    
    DataOutputStream dos;
    DataInputStream dis;
    
    Model m = Model.build();
    Gson gson = new Gson();

    public ClientFrame() {
        initComponents();
        clientPanel1.setFrame(this);
    }

    private void initComponents() {
        clientPanel1 = new labs.client.ClientPanel();
        ConnectBtn = new javax.swing.JButton();
        PntLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout clientPanel1Layout = new javax.swing.GroupLayout(clientPanel1);
        clientPanel1.setLayout(clientPanel1Layout);
        clientPanel1Layout.setHorizontalGroup(
            clientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );
        clientPanel1Layout.setVerticalGroup(
            clientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );

        ConnectBtn.setText("Присоединиться");
        ConnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectBtnActionPerformed(evt);
            }
        });

        PntLbl.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        PntLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PntLbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PntLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(clientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(ConnectBtn)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PntLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ConnectBtn)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }

    void reqPnt() {
        try {
            String s_msg = gson.toJson(new Msg(ActionEnum.NOTIFY));
            dos.writeUTF(s_msg);
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void sendPnt(Pnt pnt) {
        try {
            String s_msg = gson.toJson(new Msg(ActionEnum.CHANGE_PNT, pnt));
            dos.writeUTF(s_msg);
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ConnectBtnActionPerformed(java.awt.event.ActionEvent evt) {
        ConnectBtn.setEnabled(false);
        try {
            ip = InetAddress.getLocalHost();
            sock = new Socket(ip, port);
            m.addObserver(() -> { PntLbl.setText("(" + m.getPnt().getX() + ", " + m.getPnt().getY() + ")"); });
            
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
                    Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }

    private javax.swing.JButton ConnectBtn;
    private javax.swing.JLabel PntLbl;
    private labs.client.ClientPanel clientPanel1;
}
