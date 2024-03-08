/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labs;

public class Pnt {
    int x; int y;

    public Pnt(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public String toString() {
        return "Pnt{" + "x=" + x + ", y=" + y + '}';
    }
}
