package com.groupone.p2pgame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;




public class GameDriver extends JPanel
{



        private JFrame frame;

        private JPanel beginningLabelsPanel;
        private JPanel beginningButtonsPanel;


        private JButton beginningFirstConnectButton;
        private JButton beginningSecondConnectButton;
        private JButton runTestSuite;


        private JPanel endMainPanel;
        private JButton endRestartButton;
        private JButton endExitButton; // will close the program

        private JTextField addressField;
        private JTextField portField;


        private CheckerBoard firstGame;
        private CheckerBoard secondGame;

        private boolean playerOneConnected;
        private boolean playerTwoConnected;

        private String connectMessageOne;
        private String connectMessageTwo;


        /**
                initializes the GameDriver member variables
        */
        public GameDriver()
        {
                this.frame = new JFrame();
                this.beginningLabelsPanel = new JPanel();
                this.beginningButtonsPanel = new JPanel();
                this.endMainPanel = new JPanel();

                this.beginningFirstConnectButton = new JButton();
                this.beginningSecondConnectButton = new JButton();
                this.endRestartButton = new JButton();
                this.endExitButton = new JButton();

                this.playerOneConnected = false;
                this.playerTwoConnected = false;

                this.connectMessageOne = "";
                this.connectMessageTwo = "";

                this.firstGame = null;
                this.secondGame = null;

        }


        /**
                Displays a starting for the users, with two buttons for connecting two players and a button for running tests
        */
        public void gameBeginningScreen()
        {

                this.frame = new JFrame("Checkers");
                frame.setSize(700,550);

                this.beginningLabelsPanel = new JPanel();
                beginningLabelsPanel.setLayout( new GridLayout(2,1) );

                this.beginningButtonsPanel = new JPanel();


                        // player connection buttons
                this.beginningFirstConnectButton = new JButton("Connect");
                beginningFirstConnectButton.setPreferredSize( new Dimension(250,200) );
                beginningFirstConnectButton.addActionListener(gameBeginningButtonListener());

                this.beginningSecondConnectButton = new JButton("Connect");
                beginningSecondConnectButton.setPreferredSize( new Dimension(250,200) );
                beginningSecondConnectButton.addActionListener(gameBeginningButtonListener());


                        // test suite option
                this.runTestSuite = new JButton("Run Tests");
                runTestSuite.setPreferredSize( new Dimension(300, 100) );
                runTestSuite.addActionListener(testSuiteButtonListener());


                        // organizes the three buttons within a JPanel
                beginningButtonsPanel.add(beginningFirstConnectButton);
                beginningButtonsPanel.add( new JPanel() ); // empty space
                beginningButtonsPanel.add(beginningSecondConnectButton);
                beginningButtonsPanel.add( new JPanel() ); // empty space
                beginningButtonsPanel.add(runTestSuite);
                beginningButtonsPanel.add( new JPanel() ); // empty space



                        // main title of screen
                JLabel mainLabel = new JLabel("Welcome to Checkers!", SwingConstants.CENTER);
                mainLabel.setFont( new Font("Serif", Font.PLAIN, 50) );

                JLabel instruction = new JLabel("(Both players must connect for the game to begin.)", SwingConstants.CENTER);
                instruction.setFont( new Font("Serif", Font.PLAIN, 15) );

                beginningLabelsPanel.add(mainLabel);
                beginningLabelsPanel.add(instruction);






                        // server information that is used in connecting clients
		JPanel serverInfo = new JPanel(new GridLayout(2, 2));
		JLabel addressLabel = new JLabel("Address");
		serverInfo.add(addressLabel);
		this.addressField = new JTextField(15);


                this.addressField.setText("localhost");
		//this.addressField.setText("69.23.122.239");

                serverInfo.add(this.addressField);
		JLabel portLabel = new JLabel("Port");
		serverInfo.add(portLabel);
		this.portField = new JTextField(6);
		this.portField.setText("10200");
                serverInfo.add(this.portField);


                        // adding the panels to the JFrame
                frame.add(beginningLabelsPanel, BorderLayout.NORTH);
                frame.add(beginningButtonsPanel, BorderLayout.CENTER);
                frame.add(serverInfo, BorderLayout.SOUTH);


                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }




        /**
                Handles the display of the game over screen, which prints the game results and allows the player to leave the program or begin a new game
        */
        public void gameEndingScreen(String endText)
        {

                this.frame = new JFrame("Checkers");
                frame.setSize(700,400);


                this.endMainPanel = new JPanel();

                        // for restarting
                this.endRestartButton = new JButton();
                this.endRestartButton.setText("Restart");
                endRestartButton.setPreferredSize( new Dimension(180, 180) );
                endRestartButton.setFont( new Font("Times-Roman" , Font.PLAIN, 30) );
                endRestartButton.addActionListener(gameEndButtonListener());


                        // for leaving the program
                this.endExitButton = new JButton();
                this.endExitButton.setText("Exit");
                endExitButton.setPreferredSize( new Dimension(180, 180) );
                endExitButton.setFont( new Font("Times-Roman" , Font.PLAIN, 30) );
                endExitButton.addActionListener(gameEndButtonListener());



                endMainPanel.add(endRestartButton);
                endMainPanel.add( new JPanel() ); // empty space
                endMainPanel.add(endExitButton);

                JLabel mainLabel = new JLabel(endText, SwingConstants.CENTER);
                mainLabel.setFont( new Font("Serif", Font.PLAIN, 60) );

                frame.add(mainLabel, BorderLayout.NORTH);
                frame.add( new JPanel(), BorderLayout.CENTER ); // empty space
                frame.add(endMainPanel, BorderLayout.CENTER);


                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }



        /**
                This ButtonListener will handle the two clients' initial connections to the server and will open game instances.
                The testing suite's button also uses this for the test files.
        */
        private ActionListener gameBeginningButtonListener()
        {



		final GameDriver self = this;
                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent event)
                        {

                                try
                                {

					String address = self.addressField.getText();
				        String port = self.portField.getText();

                                        Client client = new Client(address, Integer.parseInt(port));
                                        CheckerBoard checkersGame = checkersGame = new CheckerBoard(client, self);


                                                // The strings defined below will inform the user of how the connection is coming along.
                                        if( self.connectMessageOne == "")
                                        {
                                                self.connectMessageOne = "Player One Connected";
                                                self.connectMessageTwo = "Connect As Player Two";
                                        }
                                        else if( connectMessageOne == "Player One Connected") // otherwise, it was player two's button that was pressed
                                        {
                                                self.connectMessageOne = "Player Two Connected";
                                                self.connectMessageTwo = "Player One Connected";
                                        }


                                                // sets the two created Checkers games as member variables for the GameDriver
                                        if( self.firstGame == null)
                                        {
                                                self.firstGame = checkersGame;
                                        }
                                        else
                                        {
                                                self.secondGame = checkersGame;
                                        }




                                                // changes the text on the screen buttons to give the user a hint as to what is going on
                                        if( event.getSource() == self.beginningFirstConnectButton )
                                        {
                                                self.beginningFirstConnectButton.setText(self.connectMessageOne);
                                                self.beginningSecondConnectButton.setText(self.connectMessageTwo);
                                                self.beginningFirstConnectButton.setEnabled(false);
                                        }
                                        else // the "Connect Player Two" button was clicked
                                        {
                                                self.beginningSecondConnectButton.setText(self.connectMessageOne);
                                                self.beginningFirstConnectButton.setText(self.connectMessageTwo);
                                                self.beginningSecondConnectButton.setEnabled(false);
                                        }



                                }
                                catch (Exception e)
                                {
                                        System.out.println("Fatal error: " + e);
                                }


                        }

                };


                return listener;


        }



        /**
                ButtonListener used exclusively for running the testing suite and printing to terminal.
        */
        private ActionListener testSuiteButtonListener()
        {

                final GameDriver self = this;
                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent event)
                        {
                                CheckerBoardStateTest tester = new CheckerBoardStateTest();
                                tester.runTests();
                        }

                };

                return listener;

        }







        /**
                ButtonListener used for the game's ending screen
        */
        private ActionListener gameEndButtonListener()
        {

                final GameDriver self = this;


                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent event)
                        {

                                if( event.getSource() == self.endRestartButton )
                                {
                                        self.frame.dispose();

                                                // resets the text for the beginning screen's buttons
                                        self.connectMessageOne = "";
                                        self.beginningFirstConnectButton.setText("Connect");
                                        self.beginningSecondConnectButton.setText("Connect");

                                                // opens the JFrame for the beginning screen
                                        self.gameBeginningScreen();
                                }

                                else // the button pressed was the exit button
                                {
                                        self.frame.dispose(); //closes the original JFrame
                                }

                        }

                };


                return listener;


        }



        /**
                return GameDriver's JFrame
        */
        public JFrame getFrame()
        {
                return this.frame;
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
                                // The program will begin by displaying the start screen.



                }

                catch (Exception e)
                {
                        System.out.println("Fatal error: " + e);

                }


        }


}
