/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labs;

public class Msg {
    public ActionEnum act;
    public Pnt pnt;

    public Msg(ActionEnum act) {
        this.act = act;
    }
    
    public Msg(ActionEnum act, Pnt pnt) {
        this.act = act;
        this.pnt = pnt;
    }
 
    @Override
    public String toString() {
        return "MsgPnt{" + "act=" + act + ",pnt=" + pnt + "}";
    }
}
