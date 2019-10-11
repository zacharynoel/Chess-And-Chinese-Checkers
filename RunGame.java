

import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.Tile;
import java.awt.Color;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;

public class RunGame extends Client {
    
    Tile tempTile;
    Tile tempPiece;
    public int tempX;
    public int tempY;
    boolean firstClick = true;
    boolean inPawnSelection = false;
    
    public int getIntVal(char ch){
        switch (ch) {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
            default:
                return -1;
        }
    }
    
    public void makeMove(int x, int y, boolean isWhite){
        Color grey = new Color(211, 211, 211);
        
        if(pieces[x][y] == null && firstClick){}
        
        else if((x == 0 || y == 0) && !inPawnSelection){}
        
        else if(firstClick && !inPawnSelection){            
            tempPiece = pieces[x][y];
            tempX = x;
            tempY = y;
            
            if(!isWhite)
                y = flip(y);
            
            tempTile = tiles[x][y];
            tiles[x][y] = new SolidColorTile(grey, '*');
            firstClick = false;
        }
        else {
            if(!inPawnSelection){
                pieces[x][y] = tempPiece;
                if(tempX != x || tempY != y)
                    pieces[tempX][tempY] = null;
                
                if(!isWhite)
                    tempY = flip(tempY);                
                tiles[tempX][tempY] = tempTile;
                
                if(!isWhite)
                    tempY = flip(tempY);
                
                firstClick = true;
                if(socket != null){
                    try {
                        out.writeUTF("move");
                        out.writeInt(tempX-1);
                        out.writeInt(tempY-1);
                        out.writeInt(x-1);
                        out.writeInt(y-1);
                    } catch (IOException ex) {}
                }
            }
            
            if(inPawnSelection){
                if(x <= 4 && y == 0){
                    pieces[tempX][tempY] = pieces[x][y];
                    
                    if(!isWhite)
                        tempY = flip(tempY);
                    tiles[tempX][tempY] = tempTile;
                    
                    if(!isWhite)
                        tempY = flip(tempY);
                    
                    inPawnSelection = false;                    
                    if(socket != null){
                        try{
                            out.writeUTF("promote pawn");
                            out.writeInt(tempX-1);
                            out.writeInt(tempY-1);
                            out.writeChar(pieces[x][y].getText());
                        } catch (IOException ex) {}
                    }
                    
                    try{
                        replaceLetters();
                    }catch(IOException error){}
                }
            }
            
            else if(pieces[x][y].getText() == 'P' && y == 1){
                inPawnSelection = true;
                tempX = x;
                tempY = y;
                
                if(!isWhite)
                    y = flip(y);
                
                tempTile = tiles[x][y];
                tiles[x][y] = new SolidColorTile(grey, '*');
                try{
                    pawnPromotion(true);
                }catch(IOException error){}
            }
            
            else if(pieces[x][y].getText() == 'p' && y == 8){
                inPawnSelection = true;
                tempX = x;
                tempY = y;
                
                if(!isWhite)
                    y = flip(y);
                
                tempTile = tiles[x][y];
                tiles[x][y] = new SolidColorTile(grey, '*');
                try{
                    pawnPromotion(false);
                }catch(IOException error){}
            }
        }
    }
    
    public void run(ArrayList<String> chessColors){
        Thread[] threads = new Thread[chessColors.size()];
        
        for(int i = 0; i < chessColors.size(); i++){
            if(chessColors.get(i).equals("white"))
                threads[i] = new Thread(new LaunchUI(true, false));
            else if(chessColors.get(i).equals("black"))
                threads[i] = new Thread(new LaunchUI(false, false));
            else
                threads[i] = new Thread(new LaunchUI(true, true));
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {}
            threads[i].start();
        }
    }
}
