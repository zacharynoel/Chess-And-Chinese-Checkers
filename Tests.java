

import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.SpriteWindow;
import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import edu.calpoly.testy.Testy;
import java.io.IOException;


public class Tests implements Pieces {
    
    public static void test() throws IOException{
        Size gridSize = new Size(8, 8);
        SpriteWindow window = new SpriteWindow("testWindow", gridSize);
        Size tileSize = new Size(100, 100);
        window.setTileSize(tileSize);

        Size gridSize2 = new Size(22, 33);
        SpriteWindow window2 = new SpriteWindow("testWindow2", gridSize2);
        Size tileSize2 = new Size(56, 72);
        window2.setTileSize(tileSize2);

        Size gridSize3 = new Size(33, 22);
        SpriteWindow window3 = new SpriteWindow("testWindow3", gridSize3);
        Size tileSize3 = new Size(72, 56);
        window3.setTileSize(tileSize3);

        Testy.run(
            () -> assertEquals(tileSize.height, 100),
            () -> assertEquals(tileSize.width, 100),
            () -> assertEquals(tileSize2.height, 72),
            () -> assertEquals(tileSize2.width, 56),
            () -> assertEquals(tileSize3.height, 56),
            () -> assertEquals(tileSize3.width, 72),
            () -> assertTrue(black_queen.exists()),
            () -> assertTrue(black_king.exists()),
            () -> assertTrue(black_rook.exists()),
            () -> assertTrue(black_knight.exists()),
            () -> assertTrue(black_bishop.exists()),
            () -> assertTrue(black_pawn.exists()),
            () -> assertTrue(white_queen.exists()),
            () -> assertTrue(white_king.exists()),
            () -> assertTrue(white_rook.exists()),
            () -> assertTrue(white_knight.exists()),
            () -> assertTrue(white_bishop.exists()),
            () -> assertTrue(white_pawn.exists()),
            () -> assertTrue(one.exists()),
            () -> assertTrue(two.exists()),
            () -> assertTrue(three.exists()),
            () -> assertTrue(four.exists()),
            () -> assertTrue(five.exists()),
            () -> assertTrue(six.exists()),
            () -> assertTrue(seven.exists()),
            () -> assertTrue(eight.exists()),
            () -> assertTrue(a.exists()),
            () -> assertTrue(b.exists()),
            () -> assertTrue(c.exists()),
            () -> assertTrue(d.exists()),
            () -> assertTrue(e.exists()),
            () -> assertTrue(f.exists()),
            () -> assertTrue(g.exists()),
            () -> assertTrue(h.exists()),
            () -> assertTrue(kibbitzer.exists())
        );
    }
}
