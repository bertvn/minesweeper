package org.bertvn.gui.components;

import org.bertvn.gui.GameState;
import org.bertvn.gui.events.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SmileyPanel extends JPanel implements IObserver {

    private final BufferedImage neutralFace;
    private final BufferedImage holdFace;
    private final BufferedImage winFace;
    private final BufferedImage loseFace;
    private BufferedImage currentFace;

    public SmileyPanel() {
        neutralFace = fetchSmiley("smilies/neutralFaceSmall.png");
        holdFace = fetchSmiley("smilies/holdFaceSmall.png");
        winFace = fetchSmiley("smilies/winFaceSmall.png");
        loseFace = fetchSmiley("smilies/loseFaceSmall.png");
        setSize(40, 40);
        setPreferredSize(new Dimension(40, 40));
        currentFace = neutralFace;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Notifier.getInstance().notify(new GameResetEvent());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(GUIConstants.MAIN_COLOR);
        g.fillRect(0, 0, 40, 40);
        g.drawImage(currentFace, 5, 5, (img, infoFlags, x, y, width, height) -> false);
    }

    private BufferedImage fetchSmiley(String path) {
        try {
            BufferedImage image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
//            image = resizeImage(image, 40, 40);
            return image;
        }
        catch(IOException e) {
            // do nothing
        }
        return null;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    @Override
    public void notify(IGameEvent gameEvent) {
        if(gameEvent instanceof GameFinishEvent finishEvent) {
            if(finishEvent.getState() == GameState.WON) {
                currentFace = winFace;
            }
            else{
                currentFace = loseFace;
            }
            repaint();
        }
        if(gameEvent instanceof GameResetEvent){
            currentFace = neutralFace;
            repaint();
        }
        if(gameEvent instanceof GameMouseEvent mouseEvent) {
            if(mouseEvent.getType() == GameMouseEvent.Type.HOLD){
                currentFace = holdFace;
                repaint();
            }
            else{
                currentFace = neutralFace;
                repaint();
            }
        }
    }
}
