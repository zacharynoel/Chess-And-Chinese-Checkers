
import java.awt.Color;

public class RunRobot implements Runnable {

    Robot robot;
    Snapshot snap;
    Team color = new Team();
    CCHUI ui;
    
    RunRobot(Robot r, Snapshot s, Team c, CCHUI u){
        robot = r;
        snap = s;
        color = c;
        ui = u;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {}
        
        while(true){
            CCHModel.lock.lock();
            
            try {
                if(snap.winner != null)
                    break;
                
                if(snap.currentPlayer.c == color.c){
                    Move m = robot.getPlay(snap);
                    if(snap.currentPlayer.c != color.c)
                        continue;
                    snap.history.add(new Snapshot(snap));
                    snap.colors.set(m.newPosition, color.c);
                    snap.colors.set(m.selectedMarble, Color.black);
                    CCHModel.update = true;
                    if(snap.currentPlayer.c == Color.blue)
                        snap.currentPlayer.c = Color.magenta;
                    else if(snap.currentPlayer.c == Color.magenta)
                        snap.currentPlayer.c = Color.yellow;
                    else if(snap.currentPlayer.c == Color.yellow)
                        snap.currentPlayer.c = Color.red;
                    else if(snap.currentPlayer.c == Color.red)
                        snap.currentPlayer.c = Color.green;
                    else if(snap.currentPlayer.c == Color.green)
                        snap.currentPlayer.c = Color.cyan;
                    else
                        snap.currentPlayer.c = Color.blue;
                    Move.checkWin(m.newPosition, snap);
                }
            } finally {
                CCHModel.lock.unlock();
            }
            
            if(!ui.frame.isShowing())
                break;
        }
    }
}
