
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;



public class GameBeginning
{


        //public CheckerBoard checkerBoard;
        //public CheckerBoardState checkerBoardState;

        /*
        public Server gameServer;
        public Client playerOneClient;
        public Client playerTwoClient;
        */


        private JFrame frame;
        private JPanel labelsPanel;
        private JPanel buttonsPanel;
        private JButton connectPlayerOne;
        private JButton connectPlayerTwo;



        public GameBeginning()
        {

                this.frame = new JFrame("Checkers");
                frame.setSize(700,450);

                this.labelsPanel = new JPanel();
                labelsPanel.setLayout( new GridLayout(2,1) );

                this.buttonsPanel = new JPanel();


                this.connectPlayerOne = new JButton("Connect As Player One");
                connectPlayerOne.setPreferredSize( new Dimension(250,250) );
                connectPlayerOne.addActionListener(buttonListener());

                this.connectPlayerTwo = new JButton("Connect As Player Two");
                connectPlayerTwo.setPreferredSize( new Dimension(250,250) );
                connectPlayerTwo.addActionListener(buttonListener());

                /*
                this.gameServer = null;
                this.playerOneClient = null;
                this.playerTwoClient = null;
                */


                buttonsPanel.add(connectPlayerOne);
                buttonsPanel.add( new JPanel() ); // empty space
                buttonsPanel.add(connectPlayerTwo);


                JLabel mainLabel = new JLabel("Welcome to Checkers!", SwingConstants.CENTER);
                mainLabel.setFont( new Font("Serif", Font.PLAIN, 50) );

                JLabel instruction = new JLabel("(Both players must connect for the game to begin.)", SwingConstants.CENTER);
                instruction.setFont( new Font("Serif", Font.PLAIN, 15) );

                labelsPanel.add(mainLabel);
                labelsPanel.add(instruction);



                frame.add(labelsPanel, BorderLayout.NORTH);
                frame.add(buttonsPanel, BorderLayout.CENTER);


                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }





        private ActionListener buttonListener()
        {

                JButton oneButton = this.connectPlayerOne;
                JButton twoButton = this.connectPlayerTwo;
                JButton whichButton;


                if( oneButton.getModel().isPressed() )
                {
                        whichButton = oneButton;
                }
                else // otherwise, it was player two's button that was pressed
                {
                        whichButton = twoButton;
                }


                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent e)
                        {



                        }

                };


                return listener;


        }










        public static void main(String[] args)
        {

                GameBeginning GameBeginning = new GameBeginning();


        }




}
