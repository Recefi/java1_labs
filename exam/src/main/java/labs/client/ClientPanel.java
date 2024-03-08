
package labs.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import labs.IObserver;
import labs.Model;
import labs.Pnt;

public class ClientPanel extends javax.swing.JPanel implements IObserver {
    Model m = Model.build();
    ClientFrame frame;

    public ClientPanel() {
        initComponents();
        m.addObserver(this);
    }

    @Override
    public void refresh() {
        this.repaint();
    }

    public void setFrame(ClientFrame frame) {
        this.frame = frame;
    }

    private void initComponents() {
        setBackground(new java.awt.Color(204, 204, 204));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );
    }

    private void formMouseClicked(java.awt.event.MouseEvent evt) {
        Pnt clickPnt = new Pnt(evt.getX(), evt.getY());
        if (frame != null)
            frame.sendPnt(clickPnt);
    }

    @Override
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));

        g2d.setColor(Color.red);
        
        Pnt pnt = m.getPnt();
        if (pnt != null) {
            int pntX = pnt.getX();
            int pntY = pnt.getY();

            g2d.drawLine(pntX,0, pntX,getHeight());
            g2d.drawLine(0,pntY, getWidth(),pntY);
        }
    }
}
