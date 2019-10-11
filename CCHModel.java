
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class CCHModel implements Runnable {
    
    static ReentrantLock lock = new ReentrantLock();
    Snapshot snap = new Snapshot();
    static volatile boolean update = false;
    String name;
    ArrayList<String> robots = new ArrayList<String>();
    
    CCHModel(String s, ArrayList<String> r){
        name = s;
        robots = r;
    }
    
    public Color getTeam(int t){
        switch (t) {
            case 1:
                return Color.blue;
            case 2:
                return Color.magenta;
            case 3:
                return Color.yellow;
            case 4:
                return Color.red;
            case 5:
                return Color.green;
            case 6:
                return Color.cyan;
            default:
                break;
        }
        return null;
    }

    @Override
    public void run(){
        
        lock.lock();
        try {
            initArrays();
        } finally {
            lock.unlock();
        }
        
        CCHUI ui = new CCHUI(snap, name);
        ui.setFrameVisible(true);

        for(int i = 0; i < robots.size(); i++){
            String[] sp = robots.get(i).split(",");
            if(sp.length < 4)
                continue;
            if(!sp[0].equals("robot"))
                continue;
            if(!sp[1].equals(name))
                continue;
            double time = Double.parseDouble(sp[2]);
            int counter = 3;
            
            while(counter < sp.length){
                Color player = getTeam(Integer.parseInt(sp[counter]));
                if(player == null)
                    break;
                Thread t = new Thread(new RunRobot(new Robot(time), snap, new Team(player), ui));
                t.start();
                counter++;
            }
        }
        while(true){
            
            if(update){
                ui.frame.remove(ui.contents);
                ui.contents = new CCHComponent(ui, snap);
                ui.frame.add(ui.contents);
                ui.frame.revalidate();
                ui.frame.repaint();
                update = false;
            }
            
            if(!ui.frame.isShowing())
                break;
        }
    }
    
    public void initArrays(){
        Color c;
        for(int i = 0; i < 121; i++){
            c = defaultPosition(i);
            snap.colors.add(c);
            snap.marbles.add(null);
            if(c == Color.blue)
                snap.blueDest.add(i);
            else if(c == Color.magenta)
                snap.magentaDest.add(i);
            else if(c == Color.yellow)
                snap.yellowDest.add(i);
            else if(c == Color.green)
                snap.greenDest.add(i);
            else if(c == Color.red)
                snap.redDest.add(i);
            else if(c == Color.cyan)
                snap.cyanDest.add(i);
        }
    }
    
    public Color defaultPosition(int n){
        if(n < 10)
            return Color.blue;

        if(n >= 15 && n <= 22){
            if(n%2 == 1)
                return Color.cyan;
            else
                return Color.magenta;
        }
        
        if(n >= 29 && n <= 34){
            if(n%2 == 1)
                return Color.cyan;
            else
                return Color.magenta;
        }
        
        if(n >= 42 && n <= 45){
            if(n%2 == 0)
                return Color.cyan;
            else
                return Color.magenta;
        }
        
        if(n >= 54 && n <= 55){
            if(n%2 == 0)
                return Color.cyan;
            else
                return Color.magenta;
        }
        
        if(n >= 73 && n <= 74){
            if(n%2 == 1)
                return Color.green;
            else
                return Color.yellow;
        }
        
        if(n >= 82 && n <= 85){
            if(n%2 == 0)
                return Color.green;
            else
                return Color.yellow;
        }
        
        if(n >= 92 && n <= 97){
            if(n%2 == 0)
                return Color.green;
            else
                return Color.yellow;
        }
        
        if(n >= 103 && n <= 110){
            if(n%2 == 1)
                return Color.green;
            else
                return Color.yellow;
        }
        
        if(n > 110)
            return Color.red;
        
        else
            return Color.black;
    }
}
