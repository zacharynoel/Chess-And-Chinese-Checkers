

import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.Tile;
import java.awt.Color;
import java.io.IOException;

public class ChessBoard implements Pieces {
    static final Size gridSize = new Size(9, 9);
    static final Size tileSize = new Size(100, 100);
    Tile[][] tiles = new Tile[gridSize.width][gridSize.height];
    static Tile[][] pieces = new Tile[gridSize.width][gridSize.height];
    AnimationFrame frame;
    
    public void setKb(){
        try {
            tiles[0][0] = new ImageTile(kibbitzer, tileSize, '!');
        } catch (IOException ex) {}
    }

    public void setMaroon(){
        Color maroon = new Color(128, 0, 0);
        tiles[0][0] = new SolidColorTile(maroon, 'X');
    }
    
    public void fillTiles(){
        for(int i = 0; i < gridSize.height; i++){
            for(int j = 0; j < gridSize.width; j++){
                if(frame != null)
                    if(tiles[i][j] != null)
                        frame.addTile(i, j, tiles[i][j]);
            }
        }
    }
    
    public void fillPieces(boolean isWhite){
        for(int i = 0; i < gridSize.height; i++){
            for(int j = 0; j < gridSize.width; j++){
                if(frame != null)
                    if(pieces[i][j] != null){
                        if(isWhite || j == 0)
                            frame.addTile(i, j, pieces[i][j]);
                        else
                            frame.addTile(i, flip(j), pieces[i][j]);
                    }
            }
        }
    }
    
    public void generateBackground(boolean white){
        Color red = new Color(255, 0, 0);
        Color yellow = new Color(250, 250, 210);
        Color maroon = new Color(128, 0, 0);

        if(!white){
            Color temp = yellow;
            yellow = red;
            red = temp;
        }
        
        for(int i = 0; i < gridSize.height; i++){
            for(int j = 0; j < gridSize.width; j++){
                if(j == 0 || i == 0)
                    tiles[i][j] = new SolidColorTile(maroon, 'X');
                else if(i%2 == 0 && j%2 == 0)
                    tiles[i][j] = new SolidColorTile(yellow, '.');
                else if(j%2 != 0 && i%2 == 0)
                    tiles[i][j] = new SolidColorTile(red, '-');
                else if(j%2 == 0 && i%2 != 0)
                    tiles[i][j] = new SolidColorTile(red, '-');
                else
                    tiles[i][j] = new SolidColorTile(yellow, '.');
            }
        }
    }
    
    public void generatePieces(boolean white) throws IOException{        
        pieces[1][1] = new ImageTile(black_rook, tileSize, 'r');
        pieces[2][1] = new ImageTile(black_knight, tileSize, 'n');
        pieces[3][1] = new ImageTile(black_bishop, tileSize, 'b');

        pieces[4][1] = new ImageTile(black_queen, tileSize, 'q');
        pieces[5][1] = new ImageTile(black_king, tileSize, 'k');

        pieces[6][1] = new ImageTile(black_bishop, tileSize, 'b');
        pieces[7][1] = new ImageTile(black_knight, tileSize, 'n');
        pieces[8][1] = new ImageTile(black_rook, tileSize, 'r');
        
        for(int i = 1; i < 9; i++)
            pieces[i][2] = new ImageTile(black_pawn, tileSize, 'p');

        //----------------
        
        pieces[1][8] = new ImageTile(white_rook, tileSize, 'R');
        pieces[2][8] = new ImageTile(white_knight, tileSize, 'N');
        pieces[3][8] = new ImageTile(white_bishop, tileSize, 'B');

        pieces[4][8] = new ImageTile(white_queen, tileSize, 'Q');
        pieces[5][8] = new ImageTile(white_king, tileSize, 'K');

        pieces[6][8] = new ImageTile(white_bishop, tileSize, 'B');
        pieces[7][8] = new ImageTile(white_knight, tileSize, 'N');
        pieces[8][8] = new ImageTile(white_rook, tileSize, 'R');
        
        for(int i = 1; i < 9; i++)
            pieces[i][7] = new ImageTile(white_pawn, tileSize, 'P');

        //----------------
        
        pieces[0][8] = new ImageTile(one, tileSize, '1');
        pieces[0][7] = new ImageTile(two, tileSize, '2');
        pieces[0][6] = new ImageTile(three, tileSize, '3');
        pieces[0][5] = new ImageTile(four, tileSize, '4');
        pieces[0][4] = new ImageTile(five, tileSize, '5');
        pieces[0][3] = new ImageTile(six, tileSize, '6');
        pieces[0][2] = new ImageTile(seven, tileSize, '7');
        pieces[0][1] = new ImageTile(eight, tileSize, '8');
        
        //----------------

        pieces[1][0] = new ImageTile(a, tileSize, 'a');
        pieces[2][0] = new ImageTile(b, tileSize, 'b');
        pieces[3][0] = new ImageTile(c, tileSize, 'c');
        pieces[4][0] = new ImageTile(d, tileSize, 'd');
        pieces[5][0] = new ImageTile(e, tileSize, 'e');
        pieces[6][0] = new ImageTile(f, tileSize, 'f');
        pieces[7][0] = new ImageTile(g, tileSize, 'g');
        pieces[8][0] = new ImageTile(h, tileSize, 'h');
    }
    
    public void pawnPromotion(boolean isWhite) throws IOException{
        if(isWhite){        
            pieces[1][0] = new ImageTile(white_queen, tileSize, 'Q');
            pieces[2][0] = new ImageTile(white_rook, tileSize, 'R');
            pieces[3][0] = new ImageTile(white_bishop, tileSize, 'B');
            pieces[4][0] = new ImageTile(white_knight, tileSize, 'N');
        }
        else{
            pieces[1][0] = new ImageTile(black_queen, tileSize, 'q');
            pieces[2][0] = new ImageTile(black_rook, tileSize, 'r');
            pieces[3][0] = new ImageTile(black_bishop, tileSize, 'b');
            pieces[4][0] = new ImageTile(black_knight, tileSize, 'n');
        }
    }
    
    public void replaceLetters() throws IOException{
        pieces[1][0] = new ImageTile(a, tileSize, 'a');
        pieces[2][0] = new ImageTile(b, tileSize, 'b');
        pieces[3][0] = new ImageTile(c, tileSize, 'c');
        pieces[4][0] = new ImageTile(d, tileSize, 'd');
    }
    
    public static int flip(int n){
        switch (n) {
            case 1:
                return 8;
            case 2:
                return 7;
            case 3:
                return 6;
            case 4:
                return 5;
            case 5:
                return 4;
            case 6:
                return 3;
            case 7:
                return 2;
            case 8:
                return 1;
            default:
                return 0;
        }
    }
    
    /*
    public void displayBoard(int k){
        for(int total = 1; total <= k; total++){
            System.out.println("Kibbitzer " + total + "\n");
            for(int i = 0; i < tileSize.width; i++){
                for(int j = 0; j < tileSize.height; j++){
                    if(pieces[j][i] != null)
                        System.out.print(pieces[j][i].getText() + "  ");
                    else
                        System.out.print(tiles[j][i].getText() + "  ");
                }
                System.out.println();
            }
            System.out.println("\n-  -  -  -  -  -  -  -  -\n");
        }
    }
    */
}
