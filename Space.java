//The Space class has a row and column which provide the row and column
//numbers of the space on the board. The color attribute tells what color
//the space is ('r' for red, 'b' for black, ' ' for no color). The sideLength
//attribute gives the graphical side length of a space on the board.
//Dongpeng Xia

package a5;

import java.awt.*;

//Space
public class Space
{
	int row, column;  //index in the array in Board
	char color; //'r' for red, 'b' for black, ' ' for unfilled space
	static int sideLength = 75; //graphical side length of space
   
	//constructor for empty space with row (r) and column (c) location in the Board
	public Space( int r, int c)
	{
		row = r;
		column = c;
		color = ' ';
		
   	}//end Space(int, int)
   
	//returns true if and only if space is empty
	public boolean empty()
	{
		return color == ' ';
		
	}//end empty()
	
	//calcX() gives the graphical x-coordinate of the top-left of a space in the board
	public int calcX()
	{
		return sideLength * ( 1 + row );
		
	}//end calcX()
	
	//calcY() gives the graphical y-coordinate of the top-left of a space in the board
	public int calcY()
	{
		return sideLength * ( 1 + column );
		
	}//end calcY()
   
	//drawMe draws the space (with a colored filled circle inside if it is occupied)
	public void drawMe( Graphics g )
	{
		//draw sides of the space
		g.drawRect( calcX(), calcY(), sideLength, sideLength );
      
		//color the space if occupied
		if(color == 'r')
		{
			g.setColor(Color.RED);
			g.fillOval( calcY(), calcX(), sideLength, sideLength );
		}
		else if(color == 'b')
		{
			g.setColor(Color.BLACK);
			g.fillOval( calcY(), calcX(), sideLength, sideLength );
		}
		
		g.setColor(Color.BLACK);
		
	}//end drawMe(Graphics)
   
	//returns true if and only if Space sp is adjacent to current space
	public boolean adjacent(Space sp)
	{
		return (Math.abs(sp.row - row) <= 1 && Math.abs(sp.column - column) <= 1);
		
	}//end adjacent(Space)
	
	//setters and getters
	public static int getSideLength() { return sideLength; }
	public char getColor() { return color; }
	public int getRow() { return row; }
	public int getColumn() { return column; }
	public void setColor( char c ) { color = c; }   
	private void setRow( int r ) { row = r; }
	private void setColumn( int c ) {column = c; }
	
}//end Space