
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class CCHComponent extends Component {
    
    private Graphics2D g;
    private Ellipse2D e;
    public CCHUI ui;
    Snapshot snap;

    int dotCounter = 0;
    
    CCHComponent(CCHUI u, Snapshot s){
        this.ui = u;
        snap = s;
        
        CCHComponent.this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Move.handleClick(e.getPoint(), snap);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    @Override
    public void paint(Graphics gIn) {
        g = (Graphics2D) gIn;
        Dimension size = getSize();
        double componentH = size.getHeight()/18;
        double componentW = size.getWidth()/16;
        double minDimension = Math.min(componentH, componentW);

        g.setColor(snap.background);
        g.fillRect(0, 0, size.width, size.height);

        double x = size.getWidth()/2;
        double w = minDimension;
        double h = minDimension;
        
        e = new Ellipse2D.Double(x-(w/2), 0, w, h);
        fillStar();
        drawTriangle();
        
        snap.eWidth = (int)e.getWidth();
        snap.eHeight= (int) e.getHeight();
        
        String dir = System.getProperty("user.dir");
        File path = new File(dir, "src/images/undo.png");
        Image undo;
        try {
            undo = ImageIO.read(path);
            g.drawImage(undo, (int)x/4, 0, (int)w*2, (int)h*2, null);
            snap.undoLoc = (int)x/4;
            snap.undoW = (int)w*2;
            snap.undoH = (int)h*2;
        } catch (IOException ex) {}
    }
    
    public void drawTriangle(){
        int i, j, k;
        int w = (int)e.getWidth();
        int h = (int)e.getHeight();

        if(snap.currentPlayer.c == Color.blue){
            i = 0;
            j = 8;
            k = 9;
        }
        
        else if (snap.currentPlayer.c == Color.magenta){
            i = 22;
            j = 16;
            k = 55;
        }
        
        else if (snap.currentPlayer.c == Color.yellow){
            i = 110;
            j = 74;
            k = 104;
        }
        
        else if (snap.currentPlayer.c == Color.red){
            i = 120;
            j = 114;
            k = 113;
        }
        
        else if (snap.currentPlayer.c == Color.green){
            i = 109;
            j = 103;
            k = 73;
        }
        
        else{
            i = 21;
            j = 54;
            k = 15;
        }
        
        int topX = snap.marbles.get(i).x;
        int topY = snap.marbles.get(i).y;

        int leftX = snap.marbles.get(j).x;
        int leftY = snap.marbles.get(j).y;

        int rightX = snap.marbles.get(k).x;
        int rightY = snap.marbles.get(k).y;
        
        if(snap.currentPlayer.c == Color.blue){
            topX += w/2;
            topY -= h/2;
            leftX -= w/2;
            leftY += h;
            rightX += w + w/2;
            rightY += h;
        }
        
        else if(snap.currentPlayer.c == Color.magenta){
            topX += w + w/2;
            leftX -= w/3;
            rightY += h + h/2;
            rightX += w/2;
        }
        
        else if(snap.currentPlayer.c == Color.yellow){
            topX += w + w/2;
            topY += h;
            leftX += w/2;
            leftY -= h/2;
            rightX -= w/3;
            rightY += h;
        }
        
        else if(snap.currentPlayer.c == Color.red){
            topX += w/2;
            topY += h + h/2;
            leftX += w + w/2;
            rightX -= w/2;
        }
        
        else if(snap.currentPlayer.c == Color.green){
            topX -= w/2;
            topY += h;
            rightX += w/2;
            leftX += w + w/3;
            leftY += h;
            rightY -= h/2;
        }
        
        else{
            topX -= w/2;
            leftX += w/2;
            leftY += w + w/2;
            rightX += w + w/3;
        }

        int x[] = {topX, leftX, rightX};
        int y[] = {topY, leftY, rightY};

        Color triangle = new Color(snap.currentPlayer.c.getRed(),
            snap.currentPlayer.c.getGreen(), snap.currentPlayer.c.getBlue(),
            snap.currentPlayer.c.getAlpha()/2);

        g.setColor(triangle);
        g.fillPolygon(x, y, 3);
    }
    
    public void fillStar(){  
        //Row 0 - Start
        fillRow(0, 0);
        //Row 1
        fillRow(1, 1);
        //Row 2
        fillRow(2, 2);
        //Row 3
        fillRow(3, 3);
        //Row 4
        fillRow(12, 4);
        //Row 5
        fillRow(11, 5);
        //Row 6
        fillRow(10, 6);
        //Row 7
        fillRow(9, 7);
        
        //Row 8 - Middle
        fillRow(8, 8);
        
        //Row 9
        fillRow(9, 9);
        //Row 10
        fillRow(10, 10);
        //Row 11
        fillRow(11, 11);
        //Row 12
        fillRow(12, 12);
        //Row 13
        fillRow(3, 13);
        //Row 14
        fillRow(2, 14);
        //Row 15
        fillRow(1, 15);
        //Row 16 - End
        fillRow(0, 16);
    }
    
    public void fillRow(int end, int row){
        int start;
        
        if(row%2 == 0)
            start = 0;
        else
            start = 1;
        
        for(int i = start; i <= end; i+=2){
            drawE(left(i), row);
            if(row%2 != 0 || i != start)
                drawE(right(i), row);
        }
    }
    
    public void drawE(double x, double row) {
        double y = e.getMaxY()*row + e.getHeight()/2;
        double w = e.getWidth();
        double h = e.getHeight();
        
        Ellipse2D tempE = new Ellipse2D.Double(x, y, w, h);
        
        if(dotCounter >= 121)
            dotCounter = 0;
        
        g.setColor(Color.black);
        g.fill(tempE);
        
        tempE = new Ellipse2D.Double(x + 1, y + 1, w - 2, h - 2);
        snap.marbles.set(dotCounter, new Point((int)tempE.getX(), (int)tempE.getY()));
        
        g.setColor(snap.colors.get(dotCounter));
        g.fill(tempE);
        dotCounter++;
    }
    
    public double left(double n){
        return e.getX() - (n * (e.getCenterX() - e.getX())) - (n*2);
    }
    
    public double right(double n){
        return e.getX() + (n * (e.getCenterX() - e.getX())) + (n*2);
    }
}
