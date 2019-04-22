package chess.chessPieceImages;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import java.net.URL;

public class SpriteContainer {
    private Image[][] chessPieceImages;

    public SpriteContainer() {
        chessPieceImages = new Image[2][6];
        createImages();
    }

    public Image getImage(int x, int y) {
        return chessPieceImages[y][x];
    }

    private void createImages() {
        try {
            URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceImages[i][j] = SwingFXUtils.toFXImage(bi.getSubimage(j * 64, i * 64, 64, 64), null);
                }
            }
        } catch (Exception e) {
            System.exit(1);
        }
    }
}
