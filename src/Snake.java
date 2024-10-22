import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake extends JPanel implements ActionListener{
    private final int height;
    private final int width;
    private boolean gameStarted=false;
    private boolean gameOver=false;
    private Direction newDirection=Direction.RIGHT;
    private final ArrayList<GamePoint> snake=new ArrayList<>();

    public Snake(final int width, final int height){
        this.height=height;
        this.width=width;

        setPreferredSize(new Dimension(width,height));
        setBackground(Color.black);
        setFocusable(true);
        startGame();
    }
    public void startGame(){
        resetGameData();
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e){
                handleKeyEvent(e);
            }
        });
        Timer timer=new Timer(500, this);
        timer.start();
    }
    public void resetGameData(){
        snake.clear();
        snake.add(new GamePoint(width/2,height/2));
    }
    public void handleKeyEvent(KeyEvent e){
        if (!gameStarted){
            if (e.getKeyCode()==KeyEvent.VK_SPACE) gameStarted=true;
        } else if (!gameOver){
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    newDirection=Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    newDirection=Direction.DOWN;
                    break;
                case KeyEvent.VK_LEFT:
                    newDirection=Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    newDirection=Direction.RIGHT;
                    break;
            }
        }
    }
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        if (!gameStarted){
            graphics.setColor(Color.white);
            String intro="Welcome. Press Space to begin.";
            Font font=new Font("Segoe UI", Font.ITALIC,20);
            FontMetrics metrics=getFontMetrics(font);
            graphics.setFont(font);
            graphics.drawString(intro,(width-metrics.stringWidth(intro))/2,height/2);
        }else{
            graphics.setColor(Color.green);
            for (final var point:snake) graphics.fillRect(point.x, point.y, 10, 10);
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e){
        if (gameStarted && !gameOver) mov();
        repaint();
    }

    private boolean checkCollision(){
        final GamePoint head=snake.get(0);
        boolean checkX=(head.x<0 || head.x>=width);
        boolean checkY=head.y<0 || head.y>=height;
        return checkX || checkY;
    }

    private void mov(){
        final GamePoint currentHead=snake.get(0);
        GamePoint nextHead=new GamePoint(currentHead.x+10, currentHead.y);
        snake.add(0, nextHead); 
        if (checkCollision()){
            gameOver=true;
            snake.remove(0);
        }
        else snake.remove(snake.size()-1);
    }

    public record GamePoint(int x,int y){}
    enum Direction{
        UP,DOWN,LEFT,RIGHT
    }
}