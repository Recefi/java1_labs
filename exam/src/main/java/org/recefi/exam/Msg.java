package org.recefi.exam;

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
