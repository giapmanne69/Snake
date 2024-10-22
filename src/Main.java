import java.awt.Dimension;

import javax.swing.JFrame;

public class Main{
    public static void main(String[] args){
        final JFrame frame=new JFrame("MY SNAKE GAME");
        Snake game=new Snake(400,600);
        frame.add(game);
        frame.setSize(new Dimension(400,600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        game.startGame();
    }
}