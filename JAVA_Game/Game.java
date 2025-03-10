import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game implements ActionListener , KeyListener
{
    JFrame gameFrame;
    JPanel rightSide;
    JPanel leftSide;
    JPanel mainArea;
    JLabel player1;
    JLabel player2;
    Timer timer;
    JLabel Puck;
    int xPosition = 720 , yPosition = 370;
    int xVelocity = -10 , yVelocity = 0 , p2Velocity = -90;
    int P2X = 1245 , P2Y = 345;
    int p1Points = 0 , p2Points = 0;
    JLabel P1 , P2;
    Random speed = new Random();
    boolean moveUp = false , moveDown = false;

    public void StartGame() throws InterruptedException
    {
        gameFrame = new JFrame("GAME");
        gameFrame.setSize(1500,800);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(null);

        gameFrame.addKeyListener(this);
        gameFrame.setFocusable(true);
        gameFrame.requestFocus();

        rightSide = new JPanel();
        rightSide.setBounds(0,0,200,800);
        rightSide.setBackground(new Color(0, 100, 0));

        leftSide = new JPanel();
        leftSide.setBounds(1300,0,200,800);
        leftSide.setBackground(new Color(0, 0,139));

        mainArea = new JPanel();
        mainArea.setBounds(200,0,1100,800);
        mainArea.setBackground(Color.BLACK);

        player1 = new JLabel();
        player1.setPreferredSize(new Dimension(50,100));
        player1.setBackground(Color.green);
        player1.setOpaque(true);
        player1.setBounds(205,345,50,100);

        player2 = new JLabel();
        player2.setPreferredSize(new Dimension(50,100));
        player2.setBackground(Color.yellow);
        player2.setOpaque(true);
        player2.setBounds(1245,345,50,100);

        Puck = new JLabel();
        Puck.setPreferredSize(new Dimension(30,30));
        Puck.setBackground(Color.ORANGE);
        Puck.setOpaque(true);
        Puck.setBounds(720,370,30,30);
        timer = new Timer(1, this);
        timer.start();

        P1 = new JLabel();
        P1.setOpaque(true);
        P1.setPreferredSize(new Dimension(80,80));
        P1.setBackground(Color.white);
        P1.setBounds(60,350,80,80);
        P1.setFont(new Font("Arial",Font.BOLD , 40));
        P1.setText("  "+String.valueOf(p1Points));

        P2 = new JLabel();
        P2.setOpaque(true);
        P2.setPreferredSize(new Dimension(80,80));
        P2.setBackground(Color.white);
        P2.setBounds(1354,350,80,80);
        P2.setFont(new Font("Arial",Font.BOLD , 40));
        P2.setText("  "+String.valueOf(p2Points));

        gameFrame.add(P1);
        gameFrame.add(P2);
        gameFrame.add(Puck);
        gameFrame.add(player1);
        gameFrame.add(player2);
        gameFrame.add(rightSide);
        gameFrame.add(leftSide);
        gameFrame.add(mainArea);

        gameFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (moveUp && player1.getY() > 0) {
            player1.setLocation(player1.getX(), player1.getY() - 10);
        }
        if (moveDown && player1.getY() + player1.getHeight() < 760) {
            player1.setLocation(player1.getX(), player1.getY() + 10);
        }
        xPosition += xVelocity;
        yPosition += yVelocity;
        P2Y += p2Velocity;
        Puck.setLocation(xPosition, yPosition);
        player2.setLocation(P2X, P2Y);  
        if(Puck.getX()<player1.getX()+player1.getWidth() && (Puck.getY()+Puck.getHeight()>=player1.getY() && (Puck.getY()+Puck.getHeight()<=player1.getY()+player1.getHeight() || Puck.getY()<=player1.getY()+player1.getHeight()))){
            xVelocity = speed.nextInt(5)+10;
            if(Puck.getY()>player1.getY()+player1.getHeight()/2)
                yVelocity = speed.nextInt(5)+10;
            else
                yVelocity = (speed.nextInt(5)+10)*-1;
        }
        else if(Puck.getX()<=200 || Puck.getX()>=1242+Puck.getWidth()){
            if(Puck.getX()<=200){
                p2Points++;
                P2.setText("  "+String.valueOf(p2Points));
            }
            else if(Puck.getX()>=1242+Puck.getWidth()){
                p1Points++;
                P1.setText("  "+String.valueOf(p1Points));
            }
            timer.stop();
            new SwingWorker<Void, Void>() {
                protected Void doInBackground() throws Exception {
                    Thread.sleep(2000);
                    return null;
                }
    
                protected void done() {
                    xPosition = 720;
                    yPosition = 370;
                    xVelocity = -10;
                    yVelocity = 0;
                    player1.setLocation(205, 345);
                    player2.setLocation(1245, 345);
                    timer.start(); 
                }
            }.execute();
        }
        else if(Puck.getX()+Puck.getWidth()>player2.getX() && (Puck.getY()+Puck.getHeight()>=player2.getY() && (Puck.getY()+Puck.getHeight()<=player2.getY()+player2.getHeight() || Puck.getY()<=player2.getY()+player2.getHeight())))
            xVelocity = (speed.nextInt(5)+10)*-1;

        if(Puck.getY()<0 || Puck.getY()+Puck.getHeight()>760){
            yVelocity *= -1;
        }

        if(player2.getY()<0 || player2.getY()+player2.getHeight()>760)
            p2Velocity *= -1;

        if(p1Points==10 || p2Points==10)
        {
            timer.stop();
            if(p1Points==10)
                JOptionPane.showMessageDialog(null, "You Win" , "Game Finished" , JOptionPane.INFORMATION_MESSAGE);
            else if(p2Points==10)
                JOptionPane.showMessageDialog(null, "You Lost" , "Game Finished" , JOptionPane.INFORMATION_MESSAGE);
            gameFrame.dispose();
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveDown = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveDown = false;
        }
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}