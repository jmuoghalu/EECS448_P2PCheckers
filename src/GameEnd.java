
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;



public class GameEnd
{


        //public CheckerBoard checkerBoard;
        //public CheckerBoardState checkerBoardState;

        /*
        public Server gameServer;
        public Client playerOneClient;
        public Client playerTwoClient;
        */


        private JFrame frame;
        private JPanel panel;
        private JButton restartButton;
        private JButton exitButton; // will close the program



        public GameEnd()
        {

                this.frame = new JFrame("Checkers");
                frame.setSize(700,300);


                this.panel = new JPanel();


                this.restartButton = new JButton("Restart");
                restartButton.setPreferredSize( new Dimension(180, 180) );
                restartButton.setFont( new Font("Times-Roman" , Font.PLAIN, 30) );
                restartButton.addActionListener(buttonListener());


                this.exitButton = new JButton("Exit");
                exitButton.setPreferredSize( new Dimension(180, 180) );
                exitButton.setFont( new Font("Times-Roman" , Font.PLAIN, 30) );
                exitButton.addActionListener(buttonListener());

                /*
                this.gameServer = null;
                this.playerOneClient = null;
                this.playerTwoClient = null;
                */


                panel.add(restartButton);
                panel.add( new JPanel() ); // empty space
                panel.add(exitButton);

                JLabel mainLabel = new JLabel("Game Over!", SwingConstants.CENTER);
                mainLabel.setFont( new Font("Serif", Font.PLAIN, 60) );

                frame.add(mainLabel, BorderLayout.NORTH);
                frame.add( new JPanel(), BorderLayout.CENTER ); // empty space
                frame.add(panel, BorderLayout.CENTER);


                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }





                // return to GameBeginning screen
        private ActionListener buttonListener()
        {

                JButton whichButton;
                if( this.restartButton.getModel().isPressed() )
                {
                        whichButton = this.restartButton;
                }
                else
                {
                        whichButton = this.exitButton;
                }



                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent e)
                        {

                                if( whichButton.getText().equals("Restart") )
                                {
                                        // open the GameBeginning JFrame
                                }

                                else // the button pressed was the exit button
                                {
                                        // disconnect the server and clients
                                        // close the app
                                }

                        }

                };


                return listener;


        }










        public static void main(String[] args)
        {

                GameEnd GameEnd = new GameEnd();


        }




}
