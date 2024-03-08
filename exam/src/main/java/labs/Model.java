
package labs;

import java.util.ArrayList;

public class Model {
    Pnt pnt;
    ArrayList<IObserver> observers = new ArrayList<>();
    static Model m = new Model();

    static public Model build() {
        return m;
    }

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
