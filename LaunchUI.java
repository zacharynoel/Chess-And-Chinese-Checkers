

import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.SpriteWindow;
import java.io.IOException;
import static java.lang.Thread.sleep;

public class LaunchUI extends RunGame implements Runnable {
    
    boolean isKibbitzer;
    boolean white;
    String textInput = "";
    
    LaunchUI(boolean color, boolean k){
        white = color;
        this.isKibbitzer = k;
    }
    
    public static void updateBoard(String response) throws IOException{
        int counter = 0;
        for(int i = 1; i < gridSize.height; i++){
            for(int j = 1; j < gridSize.width; j++){
                
                int tY = j;
                
                switch (response.charAt(counter)) {
                    case 'k':
                        pieces[i][tY] = new ImageTile(black_king, tileSize, 'k');
                        break;
                    case 'q':
                        pieces[i][tY] = new ImageTile(black_queen, tileSize, 'q');
                        break;
                    case 'b':
                        pieces[i][tY] = new ImageTile(black_bishop, tileSize, 'b');
                        break;
                    case 'n':
                        pieces[i][tY] = new ImageTile(black_knight, tileSize, 'n');
                        break;
                    case 'r':
                        pieces[i][tY] = new ImageTile(black_rook, tileSize, 'r');
                        break;
                    case 'p':
                        pieces[i][tY] = new ImageTile(black_pawn, tileSize, 'p');
                        break;
                    case 'K':
                        pieces[i][tY] = new ImageTile(white_king, tileSize, 'K');
                        break;
                    case 'Q':
                        pieces[i][tY] = new ImageTile(white_queen, tileSize, 'Q');
                        break;
                    case 'B':
                        pieces[i][tY] = new ImageTile(white_bishop, tileSize, 'B');
                        break;
                    case 'N':
                        pieces[i][tY] = new ImageTile(white_knight, tileSize, 'N');
                        break;
                    case 'R':
                        pieces[i][tY] = new ImageTile(white_rook, tileSize, 'R');
                        break;
                    case 'P':
                        pieces[i][tY] = new ImageTile(white_pawn, tileSize, 'P');
                        break;
                    case ' ':
                        pieces[i][tY] = null;
                    default:
                        break;
                }
                counter++;
            }
        }
    }
    
    private void keyTyped(char ch){
        if(ch != '\n')
            textInput += ch;

        else{
            if(textInput.equals("q"))
                System.exit(0);

            if(textInput.length() == 4 && !inPawnSelection){
                int x1 = getIntVal(textInput.charAt(0));
                int x2 = getIntVal(textInput.charAt(2));                
                int y1 = Character.getNumericValue(textInput.charAt(1));
                int y2 = Character.getNumericValue(textInput.charAt(3));
                
                y1 = flip(y1);
                y2 = flip(y2);
                
                makeMove(x1, y1, white);
                makeMove(x2, y2, white);
            }
            
            else if(inPawnSelection && textInput.length() == 1){
                int promotion = getIntVal(textInput.charAt(0));
                if(promotion >= 1 && promotion <= 4)
                    makeMove(promotion, 0, white);
            }
            textInput = "";
        }
    }
    
    private void mouseClicked(int x, int y){
        if(white)
            makeMove(x, y, white);
        else
            makeMove(x, flip(y), white);
    }
    
    @Override
    public void run() {
        SpriteWindow window = new SpriteWindow("Chess", gridSize);
        window.setTileSize(tileSize);
        window.setFps(10);
        try {
            sleep(1000);
        } catch (InterruptedException ex) {}

        if(!isKibbitzer){
            window.setMouseClickedHandler((x, y) -> mouseClicked(x, y));
            window.setKeyTypedHandler(ch -> keyTyped(ch));
        }

        window.start();

        generateBackground(white);
        try {
            generatePieces(white);
        } catch (IOException ex) {}
        
        while(window.isRunning()){
            frame = window.waitForNextFrame();
            if(isKibbitzer){
                setKb();
                fillTiles();
                fillPieces(white);
                window.showNextFrame();
            }
            
            else if(!isKibbitzer){
                setMaroon();
                fillTiles();
                fillPieces(white);
                window.showNextFrame();
            }
        }
        
        if(socket != null){
            try {
                out.writeUTF("end");
            } catch (IOException ex) {}
        }
    }
}
