/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labs;

import java.util.ArrayList;

public class Model {
    Pnt pnt;
    ArrayList<IObserver> observers = new ArrayList<>();

    public Pnt getPnt() { return pnt; }
    
    void refresh() {
        for (IObserver obs : observers)
            obs.refresh();
    }
    
    public void setPnt(Pnt pnt) {
        this.pnt = pnt;
        refresh();
    }
    
    public void addObserver(IObserver obs) {
        observers.add(obs);
    }
}
