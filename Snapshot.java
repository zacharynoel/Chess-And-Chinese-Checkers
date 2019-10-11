

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Snapshot {
    ArrayList<Color> colors;
    ArrayList<Point> marbles;
    Team currentPlayer = new Team();
    Team winner = null;
    Color background;
    ArrayList<Integer> blueDest;
    ArrayList<Integer> redDest;
    ArrayList<Integer> greenDest;
    ArrayList<Integer> yellowDest;
    ArrayList<Integer> magentaDest;
    ArrayList<Integer> cyanDest;
    int undoLoc;
    int undoW;
    int undoH;
    int eWidth;
    int eHeight;
    
    ArrayList<Snapshot> history;
    
    Snapshot(){
        colors = new ArrayList<Color>();
        marbles = new ArrayList<Point>();
        currentPlayer.c = Color.blue;
        background = Color.gray;
        blueDest = new ArrayList<Integer>();
        redDest = new ArrayList<Integer>();
        greenDest = new ArrayList<Integer>();
        yellowDest = new ArrayList<Integer>();
        magentaDest = new ArrayList<Integer>();
        cyanDest = new ArrayList<Integer>();
        
        history = new ArrayList<Snapshot>();
    }
    
    Snapshot(Snapshot snap){
        colors = new ArrayList<Color>(snap.colors);
        marbles = new ArrayList<Point>(snap.marbles);
        currentPlayer.c = snap.currentPlayer.c;
        background = snap.background;
        blueDest = new ArrayList<Integer>(snap.blueDest);
        redDest = new ArrayList<Integer>(snap.redDest);
        greenDest = new ArrayList<Integer>(snap.greenDest);
        yellowDest = new ArrayList<Integer>(snap.yellowDest);
        magentaDest = new ArrayList<Integer>(snap.magentaDest);
        cyanDest = new ArrayList<Integer>(snap.cyanDest);
        
        history = new ArrayList<Snapshot>(snap.history);
    }
    
    public void setPlayer(Color c){
        currentPlayer.c = c;
    }
    
    public void setWinner(Color c){
        winner = new Team(c);
    }
}
