import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameField extends JPanel {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;

    private Image dot;
    private Image apple;

    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];

    private int appleX;
    private int appleY;

    private int dots;
    private Timer timer;

    private boolean inGame = true;

    public void loadImage(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    public void createApple(){
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            appleX = random.nextInt(20) * DOT_SIZE;
            appleY = random.nextInt(20) * DOT_SIZE;
        }
    }
}
