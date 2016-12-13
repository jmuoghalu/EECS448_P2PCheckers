package com.groupone.p2pgame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;





public class GameDriver extends JPanel
{



        private JFrame frame;

        private JPanel beginningLabelsPanel;
        private JPanel beginningButtonsPanel;
        private JButton beginningConnectPlayerOne;
        private JButton beginningConnectPlayerTwo;


        private JPanel endMainPanel;
        private JButton endRestartButton;
        private JButton endExitButton; // will close the program

        private JTextField addressField;
        private JTextField portField;

        public GameDriver()
        {
                this.frame = new JFrame();
                this.beginningLabelsPanel = new JPanel();
                this.beginningButtonsPanel = new JPanel();
                this.endMainPanel = new JPanel();

                this.beginningConnectPlayerOne = new JButton();
                this.beginningConnectPlayerTwo = new JButton();
                this.endRestartButton = new JButton();
                this.endExitButton = new JButton();
        }



        public void gameBeginningScreen()
        {

                this.frame = new JFrame("Checkers");
                frame.setSize(700,450);

                this.beginningLabelsPanel = new JPanel();
                beginningLabelsPanel.setLayout( new GridLayout(2,1) );

                this.beginningButtonsPanel = new JPanel();


                this.beginningConnectPlayerOne = new JButton("Connect As Player One");
                beginningConnectPlayerOne.setPreferredSize( new Dimension(250,200) );
                beginningConnectPlayerOne.addActionListener(gameBeginningButtonListener());

                this.beginningConnectPlayerTwo = new JButton("Connect As Player Two");
                beginningConnectPlayerTwo.setPreferredSize( new Dimension(250,200) );
                beginningConnectPlayerTwo.addActionListener(gameBeginningButtonListener());



                beginningButtonsPanel.add(beginningConnectPlayerOne);
                beginningButtonsPanel.add( new JPanel() ); // empty space
                beginningButtonsPanel.add(beginningConnectPlayerTwo);

                JLabel mainLabel = new JLabel("Welcome to Checkers!", SwingConstants.CENTER);
                mainLabel.setFont( new Font("Serif", Font.PLAIN, 50) );

                JLabel instruction = new JLabel("(Both players must connect for the game to begin.)", SwingConstants.CENTER);
                instruction.setFont( new Font("Serif", Font.PLAIN, 15) );

                beginningLabelsPanel.add(mainLabel);
                beginningLabelsPanel.add(instruction);


		JPanel serverInfo = new JPanel(new GridLayout(2, 2));
		JLabel addressLabel = new JLabel("Address");
		serverInfo.add(addressLabel);
		this.addressField = new JTextField(15);
		this.addressField.setText("69.23.122.239");
		serverInfo.add(this.addressField);
		JLabel portLabel = new JLabel("Port");
		serverInfo.add(portLabel);
		this.portField = new JTextField(6);
		this.portField.setText("10200");
		serverInfo.add(this.portField);
		frame.add(serverInfo, BorderLayout.SOUTH);

                frame.add(beginningLabelsPanel, BorderLayout.NORTH);
                frame.add(beginningButtonsPanel, BorderLayout.CENTER);


                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }


        public void gameEndingScreen()
        {

                this.frame = new JFrame("Checkers");
                frame.setSize(700,300);


                this.endMainPanel = new JPanel();


                this.endRestartButton = new JButton();
                this.endRestartButton.setText("Restart");
                endRestartButton.setPreferredSize( new Dimension(180, 180) );
                endRestartButton.setFont( new Font("Times-Roman" , Font.PLAIN, 30) );
                endRestartButton.addActionListener(gameEndButtonListener());


                this.endExitButton = new JButton();
                this.endExitButton.setText("Exit");
                endExitButton.setPreferredSize( new Dimension(180, 180) );
                endExitButton.setFont( new Font("Times-Roman" , Font.PLAIN, 30) );
                endExitButton.addActionListener(gameEndButtonListener());



                endMainPanel.add(endRestartButton);
                endMainPanel.add( new JPanel() ); // empty space
                endMainPanel.add(endExitButton);

                JLabel mainLabel = new JLabel("Game Over!", SwingConstants.CENTER);
                mainLabel.setFont( new Font("Serif", Font.PLAIN, 60) );

                frame.add(mainLabel, BorderLayout.NORTH);
                frame.add( new JPanel(), BorderLayout.CENTER ); // empty space
                frame.add(endMainPanel, BorderLayout.CENTER);


                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }





        private ActionListener gameBeginningButtonListener()
        {


                JButton oneButton = this.beginningConnectPlayerOne;
                JButton twoButton = this.beginningConnectPlayerTwo;
                JButton whichButton;


                if( oneButton.getModel().isPressed() )
                {
                        whichButton = oneButton;
                }
                else // otherwise, it was player two's button that was pressed
                {
                        whichButton = twoButton;
                }



		GameDriver self = this;
                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent event)
                        {

                                try
                                {
					String address = self.addressField.getText();
				        String port = self.portField.getText();

				        Client client = new Client(address, Integer.parseInt(port));
                                        CheckerBoard checkersGame = new CheckerBoard(client);
                                }
                                catch (Exception e)
                                {
                                        System.out.println("Fatal error: " + e);
                                }

                        }

                };


                return listener;


        }





                // return to GameBeginning screen
        private ActionListener gameEndButtonListener()
        {

                GameDriver self = this;


                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent event)
                        {

                                if( event.getSource() == self.endRestartButton )
                                {
                                        self.frame.dispose();
                                        self.gameBeginningScreen();
                                }

                                else // the button pressed was the exit button
                                {
                                        self.frame.dispose();
                                }

                        }

                };


                return listener;


        }

















        /**
           Initialize the checker board as a main program.
         */
        public static void main(String[] args)
        {


                try
                {


                        //Server.connectToServer(); // connect this instance to the server
                        GameDriver mainGame = new GameDriver();

                        mainGame.gameBeginningScreen();



                }

                catch (Exception e)
                {
                        System.out.println("Fatal error: " + e);

                }


        }


}
