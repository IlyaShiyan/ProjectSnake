import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;

    private Image dot;
    private Image apple;

    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];

    private int appleX;
    private int appleY;
    private int appleX1;
    private int appleY1;
    private int appleX2;
    private int appleY2;

    private int dots;
    private Timer timer;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean inGame = true;

    private int count = 0;

    public void loadImage() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    public void createApple() {
        Random random = new Random();
        count = 3;
        appleX = random.nextInt(20) * DOT_SIZE;
        appleY = random.nextInt(20) * DOT_SIZE;
        appleX1 = random.nextInt(20) * DOT_SIZE;
        appleY1 = random.nextInt(20) * DOT_SIZE;
        appleX2 = random.nextInt(20) * DOT_SIZE;
        appleY2 = random.nextInt(20) * DOT_SIZE;
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 48;
            x[i] = 48 - i * DOT_SIZE;
        }
        timer = new Timer(150, this);
        timer.start();
        createApple();
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            count--;
            appleX=-16;
            appleY=-16;
        }
        if (x[0] == appleX1 && y[0] == appleY1) {
            dots++;
            count--;
            appleX1=-16;
            appleY1=-16;
        }
        if (x[0] == appleX2 && y[0] == appleY2) {
            dots++;
            count--;
            appleX2=-16;
            appleY2=-16;
        }
        if (count<=0)
            createApple();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            g.drawImage(apple, appleX1, appleY1, this);
            g.drawImage(apple, appleX2, appleY2, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.CYAN);
            g.drawString(str, SIZE / 3, SIZE / 2);
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if (x[0] > SIZE)
            x[0] = 0;
        if (x[0] < 0)
            x[0] = SIZE;
        if (y[0] > SIZE || y[0] < 0)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent a) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public GameField() {
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left)
            x[0] -= DOT_SIZE;
        if (right)
            x[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;
    }

    class FiledKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent k) {
            super.keyPressed(k);
            int key = k.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                left = false;
                right = false;
                up = true;
            }

            if (key == KeyEvent.VK_DOWN && !up) {
                left = false;
                right = false;
                down = true;
            }
        }
    }
}
