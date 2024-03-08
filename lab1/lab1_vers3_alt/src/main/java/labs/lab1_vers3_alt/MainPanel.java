/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package labs.lab1_vers3_alt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MainPanel extends javax.swing.JPanel {

    int xArr = 120, yArr = 150;
    int xAim1 = 300, yAim1 = 150, r1 = 40;
    int xAim2 = 450, yAim2 = 150, r2 = 20;
    
    
    /**
     * Creates new form MainPanel
     */
    public MainPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(2));
        
        g2d.drawPolygon(new int[] {25, 45, 25}, new int[] {getHeight()/2 - 20, getHeight()/2, getHeight()/2 + 20}, 3);
        g2d.drawLine(70, 0, 70, getHeight());
        
        g2d.drawLine(xArr - 50, yArr, xArr, yArr);
        g2d.drawPolygon(new int[] {xArr - 10, xArr, xArr - 10}, new int[] {yArr - 10, yArr, yArr + 10}, 3);
        
        g2d.drawLine(xAim1, 0, xAim1, getHeight());
        g2d.drawOval(xAim1 - r1, yAim1 - r1, 2 * r1, 2 * r1);
        g2d.setColor(Color.white);
        g2d.fillOval(xAim1 - r1, yAim1 - r1, 2 * r1, 2 * r1);
        g2d.setColor(Color.red);
        g2d.fillOval(xAim1 - r1/5, yAim1 - r1/5, 2 * r1/5, 2 * r1/5);
        
        g2d.setColor(Color.black);
        g2d.drawLine(xAim2, 0, xAim2, getHeight());
        g2d.drawOval(xAim2 - r2, yAim2 - r2, 2 * r2, 2 * r2);
        g2d.setColor(Color.white);
        g2d.fillOval(xAim2 - r2, yAim2 - r2, 2 * r2, 2 * r2);
        g2d.setColor(Color.red);
        g2d.fillOval(xAim2 - r2/5, yAim2 - r2/5, 2 * r2/5, 2 * r2/5);
    }

    public void shiftArrow() {
        xArr += 2; 
        repaint();
    }
    
    public void shiftAims() {
        yAim1 += 1;
        if (yAim1 + r1 >= getHeight())
            yAim1 = 0;
        yAim2 += 2;
        if (yAim2 + r2 >= getHeight())
            yAim2 = 0;
        repaint();
    }
    
    public boolean hitAim1() {
        if (xArr >= xAim1 - r1 && xArr <= xAim1 + r1 && yArr >= yAim1 - r1 && yArr <= yAim1 + r1) {
            xArr = 120;
            return true;
        }
        return false;
    }
    
    public boolean hitAim2() {
        if (xArr >= xAim2 - r2 && xArr <= xAim2 + r2 && yArr >= yAim2 - r2 && yArr <= yAim2 + r2) {
            xArr = 120;
            return true;
        }
        return false;
    }
    
    public boolean arrowOutOfRange() {
        if (xArr >= getWidth()) {
            xArr = 120;
            return true;
        }
        return false;
    }
    
    public void reset() {
        xArr = 120; yArr = 150;
        xAim1 = 300; yAim1 = 150;
        xAim2 = 450; yAim2 = 150;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
