import java.util.Arrays;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;

import javax.swing.*;


public class MineSweeper extends Arcade implements MouseListener, KeyListener
{
	Graphics frame;
	private int width = 1800;
	private int height = 900;
	private int startcol = -1;
	private int startrow = -1;
	private int numrows = 30;
	private int numcols = 30;
	private int boxs = 30;
	private int nummines = 50;
	private int selectX;
	private int selectY;
	private Color gray = new Color(100,100,100);
	private boolean[][] minethere;
	private boolean[][] checked;
	private int[][] xcoords;
	private int[][] ycoords;
	private boolean[][] flagged;
	private boolean setupchoices = false;
	private boolean grid = false;
	private boolean userclicked = false;
	private boolean minesset = false;
	private boolean update = false;
	private boolean clickedmine = false;
	private boolean gamesuccess = false;
	private boolean spacepressed = false;
	private String endstatus = "";
	private int curboxx = -1;
	private int curboxy = -1;
	private double minelowfact = 0.1;
	private double minehighfact = 0.25;
	public MineSweeper()
	{
		/*
		 * Sets the frame settings, background color, what the frame does when it closes, and makes it visible on screen
		 */
		pack();
		setSize(width,height);
		setBackground(Color.BLACK);
		addKeyListener(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		repaint();
	}
	public boolean getInfo()
	{
		JTextField rows = new JTextField();
		JTextField cols = new JTextField();
		JTextField mines = new JTextField();
		Object[] message = {
		    "Rows:", rows,
		    "Columns:", cols,
		    "Mines:", mines,
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		
		
		
		//get info from textfields
		if (option == JOptionPane.OK_OPTION) {
			String r = rows.getText();
			String c = cols.getText();
			String m = mines.getText();
			int rnum;
			int cnum;
			int mnum;
			try {
				rnum = Integer.parseInt(r);
				cnum = Integer.parseInt(c);
				mnum = Integer.parseInt(m);
				if(rnum > 10 && rnum < 50)
			    {
			    	if(cnum > 10 && cnum < 50)
		    		{
		    			if(mnum > minelowfact*numrows && mnum < minehighfact*numrows)
		    			{
		    				System.out.println("it works");
		    				return true;
		    			}
		    		}
			    }
			}catch(Exception e)
			{
				
			}
		    
		} else {
		    //Go back to arcade
		}
		return false;
	}
	public void setup()
	{
		JOptionPane options = new JOptionPane();
		JOptionPane errorpane = new JOptionPane();
		int temprows = 20;
		int tempcols = 20;
		int tempmines = 50;
//		boolean setrows = false;
//		boolean setcols = false;
//		boolean setmines = false;
		
		numrows = temprows;
		numcols = tempcols;
		nummines = tempmines;
		setupchoices = true;
		minethere = new boolean[numcols][numrows];
		checked = new boolean[numcols][numrows];
		xcoords = new int[numrows][numcols];
		ycoords = new int[numrows][numcols];
		flagged = new boolean[numrows][numcols];
		/*
		 * Sets all checked, flagged, and minethere to false
		 */
		for(int r = 0; r<numrows; r++)
		{
			for (int c = 0; c<numcols; c++)
			{
				minethere[r][c] = false;
				checked[r][c] = false;
				flagged[r][c] = false;
			}
		}
		
//		if(getInfo())
//		{
//			setupchoices = true;
//			numrows = temprows;
//			numcols = tempcols;
//			nummines = tempmines;
//			System.out.println(numrows+" "+numcols+" "+nummines);
//			minethere = new boolean[numcols][numrows];
//			checked = new boolean[numcols][numrows];
//			xcoords = new int[numrows][numcols];
//			ycoords = new int[numrows][numcols];
//			flagged = new boolean[numrows][numcols];
//			/*
//			 * Sets all checked, flagged, and minethere to false
//			 */
//			for(int r = 0; r<numrows; r++)
//			{
//				for (int c = 0; c<numcols; c++)
//				{
//					minethere[r][c] = false;
//					checked[r][c] = false;
//					flagged[r][c] = false;
//				}
//			}
//		}
	
		
	}
	public void placeMines(Graphics window)
	{
		if(userclicked && !minesset)
		{
			minesset = true;
			for(int minestoplace = nummines; minestoplace>0; minestoplace--)
			{
				int tempcol = (int)(Math.random()*(numcols));
				int temprow = (int)(Math.random()*(numrows));
				if((tempcol < startcol-1) || (tempcol > startcol+1) || (temprow < startrow-1) || (temprow > startrow+1))
				{
					if(!minethere[tempcol][temprow])
					{
						minethere[tempcol][temprow] = true;
					}
					else {minestoplace++;}
				}
				else{minestoplace++;}
			}
		}
		
	}
	public void makeGrid(Graphics window)
	{
		window.setColor(gray);
		for(int rows = 0; rows<numrows; rows++)
		{
			for(int cols = 0; cols<numcols; cols++)
			{
				int xcoord = cols*boxs+width/2-numcols*25/2;
				int ycoord = rows*boxs+50;
				window.drawRect(xcoord, ycoord, boxs, boxs);
				if(!grid)
				{
					xcoords[rows][cols] = xcoord;
					ycoords[rows][cols] = ycoord;
				}
			}
		}
		grid = true;
	}
	public void paint(Graphics window)
	{
		frame = window;
		if(setupchoices == false)
		{
			setup();
		}
		if(setupchoices == true && !grid)
		{
			makeGrid(window);
		}
		if(setupchoices && grid && userclicked && !minesset)
		{
			placeMines(frame);
			showTiles(frame, startcol, startrow);
			makeGrid(window);
		}
		if(update == true)
		{
			update = false;
			if(spacepressed)
			{
				placeFlag(window);
			}
			else
			{
				showTiles(frame, curboxx, curboxy);
			}
			makeGrid(window);
		}
		if (spacepressed)
		{
			placeFlag(window);
		}
//		int counter = 0;
//		for(int row = 0; row<numrows; row++)
//		{
//			for(int col = 0; col<numcols; col++)
//			{
//				if (minethere[row][col] == true)
//				{
//					counter++;
//				}
//			}
//		}
		window.setColor(gray);
		if(clickedmine == true)
		{
			clickedMine(window);
			endGame(window);
		}
		gamesuccess = checkComplete();
		if(gamesuccess == true)
		{
			gameComplete(window);
		}
	}
	public void placeFlag(Graphics window)
	{
		if(!checked[curboxy][curboxx])
		{
			if(flagged[curboxx][curboxy])
			{
				flagged[curboxx][curboxy] = false;
				window.setColor(Color.BLACK);
				window.fillRect(xcoords[curboxy][curboxx], ycoords[curboxy][curboxx], boxs, boxs);
			}
			else
			{
				flagged[curboxx][curboxy] = true;
				window.setColor(Color.WHITE);
				window.fillRect(xcoords[curboxy][curboxx], ycoords[curboxy][curboxx], boxs, boxs);
			}
		}
		spacepressed = false;
	}
	public void showTiles(Graphics window, int col, int row)
	{
		if(flagged[col][row])
		{
		}
		if(isMine(col, row) && !flagged[col][row])
		{
			clickedmine = true;
		}
		if(!checked[row][col] && !flagged[col][row])
		{
			checked[row][col] = true;
			if(!hasMine(col, row))
			{
				if(row<numrows-1)
					showTiles(window, col, row+1);
				if(col<numcols-1)
					showTiles(window, col+1, row);
				if(row>0)
					showTiles(window, col, row-1);
				if(col>0)
					showTiles(window, col-1, row);
				if(col>0 && row<numrows-1)
					showTiles(window, col-1, row+1);
				if(col>0 && row>0)
					showTiles(window, col-1, row-1);
				if(col<numcols-1 && row<numrows-1)
					showTiles(window, col+1, row+1);
				if(col<numcols-1 && row>0)
					showTiles(window, col+1, row-1);
				if(getNumMines(col, row) == 0)
				{
					window.setColor(Color.GREEN);
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
				}
			}
			else
			{
				int nummines = getNumMines(col, row);
				switch(nummines)
				{
				case 1:
					window.setColor(Color.gray);
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				case 2:
					window.setColor(new Color(150, 0, 255));
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				case 3:
					window.setColor(Color.yellow);
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				case 4:
					window.setColor(new Color(175, 0, 0));
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				case 5:
					window.setColor(Color.blue);
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				case 6:
					window.setColor(Color.pink);
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				case 7:
					window.setColor(new Color(0,100,0));
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				case 8:
					window.setColor(Color.orange);
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
					break;
				}
			}
		}
		else 
		{
			if(col == curboxx && row == curboxy)
			{
				System.out.println(curboxx+","+curboxy+" has already been checked...");
			}
			return;
		}
	}
	public void showMines(Graphics window)
	{
		for(int row = 0; row<numrows; row++)
		{
			for(int col = 0; col<numcols; col++)
			{
				if(minethere[col][row] == true)
				{
					window.setColor(Color.RED);
					window.fillRect(xcoords[row][col], ycoords[row][col], boxs, boxs);
				}
			}
		}
	}
	public int getNumMines(int col, int row)
	{
		int counter = 0;
		if(row>0 && isMine(col, row-1))
		{
			counter++;
		}
		if(row<numrows-1 && isMine(col, row+1))		//they're not mutually exclusive, so I need all of them to be ifs...
		{
			counter++;
		}
		if(col>0 && isMine(col-1, row))
		{
			counter++;
		}
		if(col<numcols-1 && isMine(col+1, row))
		{
			counter++;
		}
		if(col>0 && row>0 && isMine(col-1, row-1))
		{
			counter++;
		}
		if(col<numcols-1 && row>0 && isMine(col+1, row-1))
		{
			counter++;
		}
		if(col>0 && row<numrows-1 && isMine(col-1, row+1))
		{
			counter++;
		}
		if(col<numcols-1 && row<numrows-1 && isMine(col+1, row+1))
		{
			counter++;
		}
		return counter;
	}
	public boolean hasMine(int col, int row)
	{
		boolean allfalse = false;
		if(row>0 && isMine(col, row-1))
		{
			return true;
		}
		if(row<numrows-1 && isMine(col, row+1))		//they're not mutually exclusive, so I need all of them to be ifs...
		{
			return true;
		}
		if(col>0 && isMine(col-1, row))
		{
			return true;
		}
		if(col<numcols-1 && isMine(col+1, row))
		{
			return true;
		}
		if(col>0 && row>0 && isMine(col-1, row-1))
		{
			return true;
		}
		if(col<numcols-1 && row>0 && isMine(col+1, row-1))
		{
			return true;
		}
		if(col>0 && row<numrows-1 && isMine(col-1, row+1))
		{
			return true;
		}
		if(col<numcols-1 && row<numrows-1 && isMine(col+1, row+1))
		{
			return true;
		}
		return allfalse;
	}
	public boolean isMine(int col, int row)
	{
		if(minethere[col][row])
		{
			return true;
		}
		return false;
	}
	public void mouseClicked(MouseEvent arg0) {
		selectX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		selectY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		//System.out.println(selectX);
		//System.out.println(selectY);
		
		if(selectX>xcoords[0][0] && selectX<boxs+xcoords[numcols-1][numrows-1] && selectY>ycoords[0][0] && selectY<ycoords[numcols-1][numrows-1]+boxs && setupchoices && grid && !userclicked && !minesset){
			userclicked = true;
			startcol = getBoxX();
			startrow = getBoxY();
			//System.out.println(startcol+", "+startrow);
			//placeMines(frame);
			repaint();
		}
		if(selectX>xcoords[0][0] && selectX<boxs+xcoords[numcols-1][numrows-1] && selectY>ycoords[0][0] && selectY<ycoords[numcols-1][numrows-1]+boxs && setupchoices && grid && userclicked && minesset)
		{
			curboxx = getBoxX();
			curboxy = getBoxY();
			update = true;
			repaint();
		}
	}
	public String getBox()
	{
		int foundX = 0;
		int foundY = 0;
		String returnNum = "";
		for(int col = boxs-1; col>=0; col--)
		{
			if(selectX>xcoords[0][col] && foundX == 0)
			{
				foundX = 1;
				returnNum += col;
			}
		}
		for(int row = boxs-1; row>=0; row--)
		{
			if(selectY>ycoords[row][0] && foundY == 0)
			{
				foundY = 1;
				returnNum += row;
			}
		}
		return returnNum;
	}
	public int getBoxX()
	{
		int returnNum = 0;
		int foundIt = 0;
		for(int col = numcols-1; col>=0; col--)
		{
			if(selectX>xcoords[0][col] && foundIt == 0)
			{
				foundIt = 1;
				returnNum = col;
			}
		}
		//System.out.println(returnNum);
		return returnNum;
	}
	public int getBoxY()
	{
		int returnNum = 0;
		int foundIt = 0;
		for(int row = numrows-1; row>=0; row--)
		{
			if(selectY>ycoords[row][0] && foundIt == 0)
			{
				foundIt = 1;
				returnNum = row;
			}
		}
		//System.out.println(returnNum);
		return returnNum;
	}
	public boolean checkComplete()
	{
		/*
		 * I might want a clicked or already revealed 2d array to know if all except the mines have been checked or clicked/already revealed.
		 * would checked work?
		 */
		for(int row = 0; row<numrows; row++)
		{
			for(int col = 0; col<numrows; col++)
			{
				if(!isMine(col,row))
				{
					if(!checked[row][col])
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	public void clickedMine(Graphics window)
	{
		endstatus = "You have stepped on a mine!  Game Over...";
		showMines(window);
	}
	public void endGame(Graphics window)
	{
		for(int row = 0; row<numrows; row++)
		{
			for(int col = 0; col<numcols; col++)
			{
				minethere[row][col] = false;
				checked[row][col] = false;
			}
		}
		JFrame frameplayagain = new JFrame();
		frameplayagain.setSize(700, 400);
		frameplayagain.pack();
		frameplayagain.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frameplayagain.setLocation(400, 400);
		frameplayagain.pack();
		//frameplayagain.setVisible(true);
		JOptionPane nextstep = new JOptionPane();
		int choice = 0;
		choice = JOptionPane.showConfirmDialog(frameplayagain, endstatus+"\nWould you like to play again?", null, JOptionPane.YES_NO_OPTION);
		frameplayagain.setVisible(false);
		switch(choice)
		{
		case 0:
			setVisible(false);
			//Does setting it to false actually get rid of the background processes or does it just make it invisible to the user?
			//How do I properly dispose of a frame?
			//I should move the console/ "dialog" box to be able to see what the game messages are... either the game message or the actual dialog box/frame
			//Then I should just do the graphics for the mastermind game...
			new MineSweeper();
			return;
		case 1:
			setVisible(false);
			new Arcade();
			return;
		}
		//nextstep.setVisible(true);
		//playagain.addActionListener();
		
	}
	public void gameComplete(Graphics window)
	{
		endstatus = "You have won! Congratulations!";
		endGame(window);
	}
	public void backToArcade()
	{
		setVisible(false);
		new Arcade();
	}
	public void mouseEntered(MouseEvent arg0) {
		
	}
	public void mouseExited(MouseEvent arg0) {
		
	}
	public void mousePressed(MouseEvent arg0) {
		
	}
	public void mouseReleased(MouseEvent arg0) {
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == 32)
		{
			//System.out.println("key registered");
			spacepressed = true;
			selectX = (int) MouseInfo.getPointerInfo().getLocation().getX();
			selectY = (int) MouseInfo.getPointerInfo().getLocation().getY();
			curboxx = getBoxX();
			curboxy = getBoxY();
			repaint();
		}
		
	}
	public void keyReleased(KeyEvent e)
	{
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}

	
}