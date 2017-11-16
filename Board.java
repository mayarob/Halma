//Board consists of a boardSize x boardSize 2D array made of Spaces
//Dongpeng Xia

package a5;

import java.awt.*;
import java.awt.event.*;

//Board
public class Board
{
	static int boardSize = 6; //# rows and columns on board
	static int initialPieceRow = 3; // 2 * (1 + 2 + 3 + ... + initialPieceRow) = total # of pieces
	Space[][] theSpaces; //boardSize x boardSize array
	char currentTurn; //'r' for red's turn, 'b' for black's turn
	
	//constructor
	public Board()
	{
		currentTurn = 'r'; //have red go first by default
		
		//initialize empty board
		theSpaces = new Space[boardSize][boardSize];
		for ( int row = 0; row < boardSize; row++ )
		{
			for ( int col = 0; col < boardSize; col++ )
			{
				theSpaces[row][col] = new Space(row, col);
			}
		}
		
		//initialize starting red pieces (top left corner)
		for(int row = 0; row < initialPieceRow; row++)
		{
			for(int col = 0; col < initialPieceRow - row; col++)
			{
				theSpaces[row][col].setColor('r');
			}
		} 
		
		//initialize starting black pieces (lower right corner)
		for(int row = boardSize - 1; row > boardSize - initialPieceRow - 1; row--)
		{
			for(int col = boardSize - 1; col > boardSize + boardSize - initialPieceRow - row - 2; col--)
			{
				theSpaces[row][col].setColor('b');
			}
		}
		
	}//end Board()	
   
	//validMove returns true if and only if moving the piece from current to destination is a valid move
	public boolean validMove(Space current, Space destination)
	{
		boolean validMove = false;
		
		//make sure piece is the right color
		if(current.getColor() == currentTurn)
		{
			//if destination is unoccupied
			if(destination.getColor() == ' ')
			{	
				//change column, stay in same row
				if(current.getRow() == destination.getRow())
				{
					if(Math.abs(destination.getColumn() - current.getColumn()) == 1)
					{
						//move piece
						validMove = true;
					}
					else if(Math.abs(destination.getColumn() - current.getColumn()) == 2 && !theSpaces[current.getRow()][(destination.getColumn() + current.getColumn())/2].empty())
					{
						//hop a piece
						validMove = true;
					}
				}
				//change row, stay in same column
				else if(current.getColumn() == destination.getColumn())
				{
					if(Math.abs(destination.getRow() - current.getRow()) == 1)
					{
						//move piece
						validMove = true;
					}
					else if(Math.abs(destination.getRow() - current.getRow()) == 2 && !theSpaces[(current.getRow() + destination.getRow())/2][current.getColumn()].empty())
					{
						//hop a piece
						validMove = true;
					}
				}
				//move diagonally
				else if(Math.abs(current.getColumn() - destination.getColumn()) == 1 && Math.abs(current.getRow() - destination.getRow()) == 1)
				{
					//move piece
					validMove = true;
				}
				else if(Math.abs(current.getColumn() - destination.getColumn()) == 2 && Math.abs(current.getRow() - destination.getRow()) == 2)
				{
					if(!theSpaces[(current.getRow() + destination.getRow())/2][(current.getColumn() + destination.getColumn())/2].empty())
					{
						//hop a piece
						validMove = true;
					}
				}
			}
		}
		return validMove;
		
	}//end validMove(Space, Space)
	
	//movePiece tries to move piece from current to destination
	//returns true if move successfully completed, false otherwise
	public boolean movePiece(Space current, Space destination)
	{
		boolean validMove = validMove(current, destination);
		
		if(validMove)
		{
			//move piece
			destination.setColor(current.getColor());
			current.setColor(' ');
		}
		
		return validMove;
		
	}//end movePiece(Space, Space)
	
	//take the mouse event and return the space that was clicked
	//return null if mouse clicked outside of board
	public Space findSpace( MouseEvent m )
	{
		Space sp = null;
      
		int row = ( m.getY() / Space.getSideLength() ) - 1; //find row that was clicked
		int col = ( m.getX() / Space.getSideLength() ) - 1; //find column that was clicked
      
		if(row >= 0 && col >= 0 && row < boardSize && col < boardSize)
		{
			sp = theSpaces[row][col];
		}
		
		return sp;
		
	}//end findSpace(MouseEvent)
	
	//drawMe method calls drawMe for each space
	public void drawMe( Graphics g )
	{
		for( int row = 0; row < boardSize; row++ )
		{
			for ( int col = 0; col < boardSize; col++ )
			{
				theSpaces[row][col].drawMe(g);
			}
		}
	}//end drawMe(Graphics)
	
	//return the color of the winner if there is one
	public char winner()
	{
		char winner = ' ';
		
		//check top-left corner
		boolean blackWins = true;
		for(int row = 0; row < initialPieceRow; row++)
		{
			for(int col = 0; col < initialPieceRow - row; col++)
			{
				if(theSpaces[row][col].getColor() != 'b')
				{
					blackWins = false;
				}
			}
		} 
		if(blackWins)
		{
			winner = 'b';
		}
		
		//check bottom-right corner
		boolean redWins = true;
		for(int row = boardSize - 1; row > boardSize - initialPieceRow - 1; row--)
		{
			for(int col = boardSize - 1; col > boardSize + boardSize - initialPieceRow - row - 2; col--)
			{
				if(theSpaces[row][col].getColor() != 'r')
				{
					redWins = false;
				}
			}
		}
		if(redWins)
		{
			winner = 'r';
		}
		
		return winner;
		
	}//end winner()
   
	//switch turns to other player's color
	public void switchTurns()
	{
		char temp = '?';
		if(currentTurn == 'r')
		{
			temp = 'b';
		}
		else if(currentTurn == 'b')
		{
			temp = 'r';
		}
		currentTurn = temp;
		
	}//end switchTurns()
	
	//setters and getters
	public static int getBoardSize() { return boardSize; } 
	public static int getInitialPieceRow() { return initialPieceRow; }
	public Space[][] getTheSpaces() { return theSpaces; }
	public char currentTurn() { return currentTurn; }
	private void setTheSpaces( Space[][] sp ) { theSpaces = sp; }
	private void setCurrentTurn( char c ) { currentTurn = c; }
	
}//end Board