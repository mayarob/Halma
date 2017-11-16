S//The Player class contains the color attribute which is the color
//of the player's pieces. Color can be 'r' for red or 'b' for black.
//Each player also has a name attribute, which is a string.
//Dongpeng Xia

package a5;

//Player
public class Player
{
   char color;  //this is the player's piece color, (r) red or (b) black
   String name; //this is the player's name during the game
   
   //constructor
   public Player( char c, String nm )
   {
      color = c;
      name = nm;
      
   }//end Player(char, String)
   
   //setters and getters
   public char getColor() { return color; }
   public String getName() { return name; }
   private void setColor( char c ) { color = c; }
   private void setName( String nm ) { name = nm; }
   
}//end Player