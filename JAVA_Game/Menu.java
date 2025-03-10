import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener{
    JLabel P1 , P2 , Puck;
    JButton Play , Instructions;
    Timer timer;
    ImageIcon image;
    JLabel UI;
    JFrame menu;
    int xPosition = 720 , yPosition = 337 , xVelocity = -10 , yVelocity = 0;

    public void ShowMenu() 
    {
        menu = new JFrame();
        menu.setSize(1500,800);
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLayout(null);

        image = new ImageIcon("image.png");
        Image img = image.getImage(); 
        Image resizedImg = img.getScaledInstance(1500, 800, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImg);

        UI = new JLabel(image);
        UI.setBounds(0,0,1500,800);

        P1 = new JLabel();
        P1.setPreferredSize(new Dimension(40,80));
        P1.setOpaque(true);
        P1.setBackground(Color.green);
        P1.setBounds(200,320,40,80);

        P2 = new JLabel();
        P2.setPreferredSize(new Dimension(40,80));
        P2.setOpaque(true);
        P2.setBackground(Color.blue);
        P2.setBounds(1250,320,40,80);

        Puck = new JLabel();
        Puck.setPreferredSize(new Dimension(30,30));
        Puck.setOpaque(true);
        Puck.setBackground(Color.orange);
        Puck.setBounds(720,337,30,30);
        timer = new Timer(1, this);
        timer.start();

        Play = new JButton("Play");
        Play.setPreferredSize(new Dimension(500,60));
        Play.setFocusable(false);
        Play.setBackground(Color.black);
        Play.setForeground(Color.white);
        Play.setFont(new Font("Arial",Font.PLAIN,40));
        Play.setBounds(500,500,500,60);
        Play.addActionListener(this);

        Instructions = new JButton("Instructions");
        Instructions.setPreferredSize(new Dimension(500,60));
        Instructions.setFocusable(false);
        Instructions.setBackground(Color.black);
        Instructions.setForeground(Color.white);
        Instructions.setFont(new Font("Arial",Font.PLAIN,40));
        Instructions.setBounds(500,600,500,60);
        Instructions.addActionListener(this);

        menu.add(P1);
        menu.add(P2);
        menu.add(Puck);
        menu.add(Play);
        menu.add(Instructions);
        menu.add(UI);
        menu.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        xPosition += xVelocity;
        yPosition += yVelocity;
        Puck.setLocation(xPosition , yPosition);
        if(Puck.getX()<=200+P1.getWidth() || Puck.getX()>=1240)
            xVelocity *= -1;
        if(e.getSource()==Play)
        {
            menu.dispose();
            Game game = new Game();
            try {
                game.StartGame();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else if(e.getSource()==Instructions){
            JFrame frame = new JFrame();
            JLabel text = new JLabel("<html><div style='text-align: center;'>"
            + "You will be playing against a Bot.<br>"
            + "You have to move using arrow keys (UP and Down).<br>"
            + "The first to reach 10 points wins the game.<br><br>"
            + "<b>Close the window if you have read the instructions.</b>"
            + "</div></html>", SwingConstants.CENTER);
            text.setPreferredSize(new Dimension(1300,600));
            text.setForeground(Color.white);
            text.setFont(new Font("Arial",Font.PLAIN,40));
            frame.setSize(1300,600);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(Color.BLACK);
            frame.add(text);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }
}