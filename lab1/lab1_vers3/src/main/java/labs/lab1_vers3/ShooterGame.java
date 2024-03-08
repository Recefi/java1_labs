/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labs.lab1_vers3;

public class ShooterGame extends Thread {
    Object o = new Object();
    MainPanel mp;
    SidePanel sp;
    
    int score = 0;
    int count = 0;
    boolean gameOver = false;
    boolean shot = false;
    boolean pause = false;

    public ShooterGame(MainPanel mp, SidePanel sp) { 
        this.mp = mp;
        this.sp = sp;
    }
    
    @Override
    public void run() {
        mp.reset();
        sp.setShtLbl("" + count);
        sp.setScrLbl("" + score);
        while(!gameOver) {
            mp.shiftAims();
            if (shot) mp.shiftArrow();
            
            if (mp.hitAim1()) {
                score+=1;
                sp.setScrLbl("" + score);
                shot = false;
            }
            if (mp.hitAim2()) {
                score+=2;
                sp.setScrLbl("" + score);
                shot = false;
            }
            if (mp.arrowOutOfRange())
                shot = false;

            if (pause) {
                synchronized(o) {
                    try {
                        o.wait();
                        pause = false;  // after notify
                    } catch (InterruptedException ex) { gameOver = true; }  // catching "interrupt()" during the pause
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) { gameOver = true; }  // catching "interrupt()"
        }
    }
    
    public void shot() {
        if (!shot) {
            shot = true;
            count++;
            sp.setShtLbl("" + count);
//            System.out.println(this.currentThread());
        }
    }
    
    public void pause() {
        if (pause)
            synchronized(o) { o.notifyAll(); }
        else 
            pause = true;
    }
}
