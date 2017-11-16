//Halma consists of two Players and a Board.
//A player wins when all of their pieces move to the opposite corner of the board.
//Players take turns moving one piece at a time. On each turn, a piece may move to
//an empty adjacent square or (exclusive or) 'skip' over a sequence of pieces sequentially,
//skipping over one adjacent piece on each 'skip'.
//Dongpeng Xia

package a5;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Halma
public class Halma extends JFrame implements MouseListener, ActionListener
{
	Player[] players; //two players, 'r' is player[0] (red), 'b' is player[1] (black)
   	Board theBoard;   //current board
   	Board savedBoard; //saved board
   	JButton resetGame; //button to reset game
	JButton saveGame;  //button to save game
	//JButton loadPreviousGame; //button to load saved game
	JButton help; //button to show instructions
	JButton switchTurn; //button to switch turns to next player
	Space currentSpace; //pointer to space that contains the piece that will be moved in next mouse click
	boolean firstStep;	//true if piece in currentSpace has not been moved in the current turn, false if piece in currentSpace has already moved at least once
   
	//main
   	public static void main ( String[] args )
   	{
   		//call constructor
   		new Halma();
   		
   	}//end main()
   
   	//constructor
   	public Halma()
   	{
   		firstStep = false;
   		
   		//GUI settings
   		setDefaultCloseOperation(EXIT_ON_CLOSE);
   		setTitle("HALMA");
   		setSize(Space.sideLength * (Board.boardSize + 2), Space.sideLength * (Board.boardSize + 2));
   		getContentPane().setBackground(Color.WHITE);
   		
   		//Introduce Halma
   		showInstructions();
   		
   		//Ask for player names
   		String name1 = JOptionPane.showInputDialog("Please enter player 1's name");
   		String name2 = JOptionPane.showInputDialog("Please enter player 2's name");
   		
   		//Layout settings
   		setLayout(new FlowLayout());

   		//Buttons to reset, save, and load game
   	    JPanel panelOfButtons = new JPanel();
   	    panelOfButtons.setBackground(Color.WHITE);
   	    panelOfButtons.setLayout(new FlowLayout());
   	    resetGame = new JButton("NEW GAME");
   	    resetGame.addActionListener(this);
   	    saveGame = new JButton("QUIT");
   	    saveGame.addActionListener(this); 
   	    /*
   	    loadPreviousGame = new JButton("LOAD GAME");
   	    loadPreviousGame.addActionListener(this);
   	    loadPreviousGame.setEnabled(false); //can not load game until a game is saved*/
   	    help = new JButton("HELP");
   	    help.addActionListener(this);
   	    switchTurn = new JButton("END TURN");
   	    switchTurn.addActionListener(this);
   	    panelOfButtons.add(resetGame);
   	    panelOfButtons.add(saveGame);
   	    //panelOfButtons.add(loadPreviousGame);
   	    panelOfButtons.add(help);
   	    panelOfButtons.add(switchTurn);
   	    add(panelOfButtons);
   		
   	    //initialize two players
   		players = new Player[2];
   		players[0] = new Player( 'r' , name1);
   		players[1] = new Player( 'b' , name2);
   		
   		//initialize Board and start game
   		theBoard = new Board();
   		addMouseListener(this);
   		setVisible(true);
   		
   	}//end Halma()
   	
   	//Handlers for reset, save, and load buttons
   	@Override
    public void actionPerformed( ActionEvent e )
    {
   		if ( e.getSource() == resetGame ) 
   		{
   			//reset the game
    	   		theBoard = new Board();
    	   	}
   		/*else if ( e.getSource() == saveGame ) 
   		{
   			//save current game
   			savedBoard = theBoard;
   			theBoard = new Board();
   			loadPreviousGame.setEnabled(true);
   		}
   		if ( e.getSource() == loadPreviousGame ) 
   		{
   			//load saved game if available
   			if(savedBoard == null)
   			{
   				JOptionPane.showMessageDialog(null, "No saved game available.");
   			}
   			else
   			{
   				theBoard = savedBoard;
   				loadPreviousGame.setEnabled(false);
   			}
   		}*/
   		else if ( e.getSource() == help )
   		{
   			//show instructions for game
   	   		showInstructions();
   		}
   		else if( e.getSource() == switchTurn )
   		{
   			//switch to next player
   			theBoard.switchTurns();
   			currentSpace = null;
   		}
   		repaint();
   		
    }//end actionPerformed(ActionEvent)
   	
   	//showInstructions opens up a dialog box with a description of Halma
   	private void showInstructions()
   	{
   		JOptionPane.showMessageDialog(null, "                             Welcome!\n" +
											"Halma is a strategy game where the objective is to move\n" + 
											"pieces from one corner of the board to the opposite\n" +
											"corner. Players take turns moving one piece at a time.\n" +
											"A piece can be moved one space in any direction or may \n"+ 
											"'hop' over a series of pieces. The board has " + Board.boardSize +
											" rows and " + Board.boardSize + " columns. "
											);
   	}//end showInstructions()
   
   	//MouseClick Handler
   	@Override public void mouseClicked ( MouseEvent m )
   	{	
   		//find space that was clicked
   		Space sp = theBoard.findSpace( m );
        
   		//check if space exists
   		if(sp == null)
   		{
   			//reset piece to be moved if click was outside board
   			if(firstStep)
   			{
   				currentSpace = null;
   			}
   		}
   		else
   		{
   			//attempting to move a piece to an empty square
   			if ( sp.getColor() == ' ' && currentSpace != null)
   			{
   				//move piece to adjacent space, then switch turns
   				if(firstStep && currentSpace.adjacent(sp))
   				{
   					//space is blank, see if we can move a piece
   	   				if(theBoard.movePiece(currentSpace, sp))
   	   				{
   	   					currentSpace = sp;
   	   					firstStep = false;
   	   				}
   	   				theBoard.switchTurns();
   				}
   				else if(!currentSpace.adjacent(sp))
   				{
   					//space is blank, see if we can move a piece (by skipping an adjacent piece)
   	   				if(theBoard.movePiece(currentSpace, sp))
   	   				{
   	   					currentSpace = sp;
   	   					firstStep = false;
   	   				}
   				}
   			}
   			else if( sp.getColor() == theBoard.currentTurn() && (currentSpace == null || (currentSpace != null && currentSpace.getColor() != theBoard.currentTurn()))) //last check is to make sure its the selection step
   			{
   				//we've selected a piece to be moved in the next click
   				currentSpace = sp;
   				firstStep = true;
   			}
   				
   			//check for winner
   			char winner = theBoard.winner();
   				
   			//if there is a winner
   			if(winner != ' ')
   			{
   				String winnerName = "";
   					
   				//red wins
	   			if(winner == 'r')
	   			{
	   				winnerName = players[0].getName();
	   				if(winnerName == null || winnerName.equals(""))
	   				{
	   					winnerName = "Player 1";
	   				}
	   			}
	   			//black wins
	   			else if(winner == 'b')
	   			{
	   				winnerName = players[1].getName();
	   				if(winnerName == null || winnerName.equals(""))
	   				{
	   					winnerName = "Player 2";
	   				}
	   			}
	   				
	   			//show winner message
	   			JOptionPane.showMessageDialog(null, "" + winnerName + " wins!"); 
	   			
	   			//reset the game
	   			theBoard = new Board();
	   			currentSpace = null;
	   			firstStep = false;
   			}
   		}
   		repaint();
   }//end mouseClicked(MouseEvent)
   
   //Overridden paint draws the board
   @Override
   public void paint( Graphics g )
   {
      super.paint(g);
      theBoard.drawMe(g);
      
   }//end paint(Graphics)
   
   //Other Mouse Handlers
   @Override
   public void mousePressed(MouseEvent e) {}
   @Override
   public void mouseReleased(MouseEvent e) {}
   @Override
   public void mouseEntered(MouseEvent e) {}
   @Override
   public void mouseExited(MouseEvent e) {}
   
}//end Halma