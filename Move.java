
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Move {
    int newPosition;
    int selectedMarble;
    
    Move(){
        newPosition = -1;
    }
    
    Move(int n, int s){
        newPosition = n;
        selectedMarble = s;
    }
    
    static boolean firstClick = true;
    static int selectedIndex;
    static Color selectedColor;
    
    public static void checkWin(int index, Snapshot snap){
        boolean win = true;
        boolean inDestination = false;
        Color winColor = Color.gray;
        
        if(snap.blueDest.contains(index)){
            for(int i : snap.blueDest){
                if(snap.colors.get(i) == Color.black)
                    win = false;
                if(snap.colors.get(i) == Color.red)
                    inDestination = true;
            }
            winColor = Color.red.darker();
            snap.setWinner(Color.red);
        }
        
        else if(snap.redDest.contains(index)){
            for(int i : snap.redDest){
                if(snap.colors.get(i) == Color.black)
                    win = false;
                if(snap.colors.get(i) == Color.blue)
                    inDestination = true;
            }
            winColor = Color.blue.darker();
            snap.setWinner(Color.blue);
        }
        
        else if(snap.magentaDest.contains(index)){
            for(int i : snap.magentaDest){
                if(snap.colors.get(i) == Color.black)
                    win = false;
                if(snap.colors.get(i) == Color.green)
                    inDestination = true;
            }
            winColor = Color.green.darker();
            snap.setWinner(Color.green);
        }
        
        else if(snap.yellowDest.contains(index)){
            for(int i : snap.yellowDest){
                if(snap.colors.get(i) == Color.black)
                    win = false;
                if(snap.colors.get(i) == Color.cyan)
                    inDestination = true;
            }
            winColor = Color.cyan.darker();
            snap.setWinner(Color.cyan);
        }
        
        else if(snap.greenDest.contains(index)){
            for(int i : snap.greenDest){
                if(snap.colors.get(i) == Color.black)
                    win = false;
                if(snap.colors.get(i) == Color.magenta)
                    inDestination = true;
            }
            winColor = Color.magenta.darker();
            snap.setWinner(Color.magenta);
        }
        
        else if(snap.cyanDest.contains(index)){
            for(int i : snap.cyanDest){
                if(snap.colors.get(i) == Color.black)
                    win = false;
                if(snap.colors.get(i) == Color.yellow)
                    inDestination = true;
            }
            winColor = Color.yellow.darker();
            snap.setWinner(Color.yellow);
        }
        
        if(win && inDestination){
            snap.background = winColor;
        }
    }
    
    public static void checkUndo(Point p, Snapshot snap){
        if(p.x >= snap.undoLoc && p.x <= (snap.undoLoc + snap.undoW)){
            if(p.y >= 0 && p.y <= snap.undoH){
                if(snap.history.size() > 0){
                    Snapshot temp = snap.history.get(snap.history.size()-1);
                    snap.colors = temp.colors;
                    snap.marbles = temp.marbles;
                    snap.currentPlayer.c = temp.currentPlayer.c;
                    snap.background = temp.background;
                    snap.blueDest = temp.blueDest;
                    snap.redDest = temp.redDest;
                    snap.greenDest = temp.greenDest;
                    snap.yellowDest = temp.yellowDest;
                    snap.magentaDest = temp.magentaDest;
                    snap.cyanDest = temp.cyanDest;

                    snap.history = temp.history;
                }
                CCHModel.update = true;
            }
        }
    }
    
    public static void clearGray(Snapshot snap){
        for(int i = 0; i < snap.colors.size(); i++)
            if(snap.colors.get(i) == Color.lightGray)
                snap.colors.set(i, Color.black);
    }
    
    public static int findNearestIndex(Point p, Snapshot snap){
        for(int i = 0; i < snap.marbles.size(); i++){
            if(snap.marbles.get(i) != null){
                int xLow = snap.marbles.get(i).x;
                int xHigh = snap.marbles.get(i).x + snap.eWidth;

                int yLow = snap.marbles.get(i).y;
                int yHigh = snap.marbles.get(i).y + snap.eHeight;

                if(p.x > xLow && p.x < xHigh && p.y > yLow && p.y < yHigh)
                    return i;
            }
        }
        
        return -1;
    }
    
    public static List<Move> legalMoves(Snapshot s){
        List<Move> legal = new ArrayList<Move>();
        
        for(int i = 0; i < s.marbles.size(); i++){
            Point point = s.marbles.get(i);
            Point size = new Point(s.eWidth, s.eWidth);

            if(s.colors.get(i) == s.currentPlayer.c){
                ArrayList<Integer> hexagon = new ArrayList<Integer>();
                if(point != null){
                    hexagon.add(Move.findNearestIndex(new Point(point.x + size.x + size.x/2, point.y + size.y/2), s));
                    hexagon.add(Move.findNearestIndex(new Point(point.x - size.x/2, point.y + size.y/2), s));
                    hexagon.add(Move.findNearestIndex(new Point(point.x + size.x, point.y + size.y + size.y/2), s));
                    hexagon.add(Move.findNearestIndex(new Point(point.x - size.x/2, point.y + size.y + size.y/2), s));
                    hexagon.add(Move.findNearestIndex(new Point(point.x + size.x, point.y - size.y/2), s));
                    hexagon.add(Move.findNearestIndex(new Point(point.x - size.x/2, point.y - size.y/2), s));
                }
                for(int j = 0; j < hexagon.size(); j++){
                    if(hexagon.get(j) != -1){
                        if(s.colors.get(hexagon.get(j)) == Color.black){
                            boolean blockLeaveDest = false;
                            boolean blockEnterDest = false;
                            if(s.currentPlayer.c == Color.blue){
                                if(!s.blueDest.contains(i) &&
                                        s.blueDest.contains(hexagon.get(j)))
                                    blockEnterDest = true;
                                if(s.redDest.contains(i) &&
                                        !s.redDest.contains(hexagon.get(j)))
                                    blockLeaveDest = true;
                            }
                            
                            if(s.currentPlayer.c == Color.magenta){
                                if(!s.magentaDest.contains(i) &&
                                        s.magentaDest.contains(hexagon.get(j)))
                                    blockEnterDest = true;
                                if(s.greenDest.contains(i) &&
                                        !s.greenDest.contains(hexagon.get(j)))
                                    blockLeaveDest = true;
                            }
                            
                            if(s.currentPlayer.c == Color.yellow){
                                if(!s.yellowDest.contains(i) &&
                                        s.yellowDest.contains(hexagon.get(j)))
                                    blockEnterDest = true;
                                if(s.cyanDest.contains(i) &&
                                        !s.cyanDest.contains(hexagon.get(j)))
                                    blockLeaveDest = true;
                            }
                            
                            if(s.currentPlayer.c == Color.red){
                                if(!s.redDest.contains(i) &&
                                        s.redDest.contains(hexagon.get(j)))
                                    blockEnterDest = true;
                                if(s.blueDest.contains(i) &&
                                        !s.blueDest.contains(hexagon.get(j)))
                                    blockLeaveDest = true;
                            }
                            
                            if(s.currentPlayer.c == Color.green){
                                if(!s.greenDest.contains(i) &&
                                        s.greenDest.contains(hexagon.get(j)))
                                    blockEnterDest = true;
                                if(s.magentaDest.contains(i) &&
                                        !s.magentaDest.contains(hexagon.get(j)))
                                    blockLeaveDest = true;
                            }
                            
                            if(s.currentPlayer.c == Color.cyan){
                                if(!s.cyanDest.contains(i) &&
                                        s.cyanDest.contains(hexagon.get(j)))
                                    blockEnterDest = true;
                                if(s.yellowDest.contains(i) &&
                                        !s.yellowDest.contains(hexagon.get(j)))
                                    blockLeaveDest = true;
                            }
                            
                            if(!blockEnterDest && !blockLeaveDest){
                                legal.add(new Move(hexagon.get(j), i));
                            }
                        }
                    }
                }
            }
        }
        
        return legal;
    }
    
    public static void handleClick(Point p, Snapshot snap){
        checkUndo(p, snap);
        
        int index = findNearestIndex(p, snap);
        
        if(index != -1){
            if(snap.currentPlayer.c == snap.colors.get(index)){
                if(!firstClick){
                    snap.colors.set(selectedIndex, selectedColor);
                    clearGray(snap);
                }
                
                selectedIndex = index;
                selectedColor = snap.colors.get(index);
                snap.colors.set(index, Color.darkGray);
                
                ArrayList<Integer> hexagon = new ArrayList<Integer>();
                
                Point point = snap.marbles.get(index);
                Point size = new Point(snap.eWidth, snap.eWidth);
                
                hexagon.add(findNearestIndex(new Point(point.x + size.x + size.x/2, point.y + size.y/2), snap));
                hexagon.add(findNearestIndex(new Point(point.x - size.x/2, point.y + size.y/2), snap));
                hexagon.add(findNearestIndex(new Point(point.x + size.x, point.y + size.y + size.y/2), snap));
                hexagon.add(findNearestIndex(new Point(point.x - size.x/2, point.y + size.y + size.y/2), snap));
                hexagon.add(findNearestIndex(new Point(point.x + size.x, point.y - size.y/2), snap));
                hexagon.add(findNearestIndex(new Point(point.x - size.x/2, point.y - size.y/2), snap));
                
                for(int i = 0; i < hexagon.size(); i++){
                    if(hexagon.get(i) != -1){
                        if(snap.colors.get(hexagon.get(i)) == Color.black){
                            boolean blockLeaveDest = false;
                            boolean blockEnterDest = false;
                            if(snap.currentPlayer.c == Color.blue){
                                if(!snap.blueDest.contains(selectedIndex) &&
                                        snap.blueDest.contains(hexagon.get(i)))
                                    blockEnterDest = true;
                                if(snap.redDest.contains(selectedIndex) &&
                                        !snap.redDest.contains(hexagon.get(i)))
                                    blockLeaveDest = true;
                            }
                            
                            if(snap.currentPlayer.c == Color.magenta){
                                if(!snap.magentaDest.contains(selectedIndex) &&
                                        snap.magentaDest.contains(hexagon.get(i)))
                                    blockEnterDest = true;
                                if(snap.greenDest.contains(selectedIndex) &&
                                        !snap.greenDest.contains(hexagon.get(i)))
                                    blockLeaveDest = true;
                            }
                            
                            if(snap.currentPlayer.c == Color.yellow){
                                if(!snap.yellowDest.contains(selectedIndex) &&
                                        snap.yellowDest.contains(hexagon.get(i)))
                                    blockEnterDest = true;
                                if(snap.cyanDest.contains(selectedIndex) &&
                                        !snap.cyanDest.contains(hexagon.get(i)))
                                    blockLeaveDest = true;
                            }
                            
                            if(snap.currentPlayer.c == Color.red){
                                if(!snap.redDest.contains(selectedIndex) &&
                                        snap.redDest.contains(hexagon.get(i)))
                                    blockEnterDest = true;
                                if(snap.blueDest.contains(selectedIndex) &&
                                        !snap.blueDest.contains(hexagon.get(i)))
                                    blockLeaveDest = true;
                            }
                            
                            if(snap.currentPlayer.c == Color.green){
                                if(!snap.greenDest.contains(selectedIndex) &&
                                        snap.greenDest.contains(hexagon.get(i)))
                                    blockEnterDest = true;
                                if(snap.magentaDest.contains(selectedIndex) &&
                                        !snap.magentaDest.contains(hexagon.get(i)))
                                    blockLeaveDest = true;
                            }
                            
                            if(snap.currentPlayer.c == Color.cyan){
                                if(!snap.cyanDest.contains(selectedIndex) &&
                                        snap.cyanDest.contains(hexagon.get(i)))
                                    blockEnterDest = true;
                                if(snap.yellowDest.contains(selectedIndex) &&
                                        !snap.yellowDest.contains(hexagon.get(i)))
                                    blockLeaveDest = true;
                            }
                            
                            if(!blockEnterDest && !blockLeaveDest){
                                snap.colors.set(hexagon.get(i), Color.lightGray);
                            }
                        }
                    }
                }
                
                firstClick = false;
            }
            else if(!firstClick){
                if(index == selectedIndex){
                    snap.colors.set(index, selectedColor);
                    firstClick = true;
                    clearGray(snap);
                }
                else if(snap.colors.get(index) == Color.lightGray){
                    clearGray(snap);
                    snap.history.add(new Snapshot(snap));
                    snap.history.get(snap.history.size()-1).colors.set(selectedIndex, selectedColor);
                    
                    snap.colors.set(index, selectedColor);
                    snap.colors.set(selectedIndex, Color.black);
                    firstClick = true;
                    
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
                }
                
                checkWin(index, snap);
            }
            CCHModel.update = true;
        }
    }
}
