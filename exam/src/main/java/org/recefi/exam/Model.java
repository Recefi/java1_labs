package org.recefi.exam;

import java.util.ArrayList;

public class Model {
    Pnt pnt;
    ArrayList<ObserverInt> observers = new ArrayList<>();
    static Model m = new Model();

    static public Model build() {
        return m;
    }

    public Pnt getPnt() { return pnt; }

    void refresh() {
        for (ObserverInt obs : observers)
            obs.refresh();
    }

    public void setPnt(Pnt pnt) {
        this.pnt = pnt;
        refresh();
    }

    public void addObserver(ObserverInt obs) {
        observers.add(obs);
    }
}
