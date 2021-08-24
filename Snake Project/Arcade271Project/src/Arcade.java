import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//Are we allowed to use awt?
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

public class Arcade extends JFrame implements MouseListener, KeyListener
{
	int width = 1600;
	int height = 900;
	int selectX;
	int selectY;
	Graphics window;
	ArrayList<GameCoords> gamelistcoords = new ArrayList<GameCoords>();
	public Arcade()
	{
		super("Arcade");
		setBackground(Color.BLACK);
		setSize(width,height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().addMouseListener(this);
		getContentPane().addKeyListener(this);
		addKeyListener(this);
		
		//Set up the game coordinates here
		
		
		
		//chooseGame();
		/*
		 * Might not have to call this if the user just clicks on the game to pick it
		 */
	}
	public void paint(Graphics window)
	{
		this.window = window;
		int g1x = 250;
		int g1y = 150;
		int g2x = 600;
		int g2y = 150;
		int w = 250;
		int h = 500;
		window.setColor(Color.RED);
		GameCoords game1 = new GameCoords(g1x,g1y,w,h, "snake");
		gamelistcoords.add(game1);
		window.fillRect(g1x, g1y, w, h);
		window.setColor(Color.BLUE);
		GameCoords game2 = new GameCoords(g2x,g2y,w,h, "mastermind");
		gamelistcoords.add(game2);
		window.fillRect(g2x, g2y, w, h);
		for(GameCoords each: gamelistcoords)
		{
			int[] corners = each.getCorners();
			System.out.println(corners[0]+" "+corners[1]+" "+corners[2]+" "+corners[3]);
		}
	}
	public void chooseGame(String name)
	{
		switch(name)
		{
		case "snake":
			//run the games here and make current selector frame invisible
			SnekMain snakegame = new SnekMain();
			System.out.println("snake selected");
			setVisible(false);
			break;
		case "mastermind":
			setVisible(false);
			MineSweeper game = new MineSweeper();
			System.out.println("mastermind selected");;
			setVisible(false);
			break;
		}
		/*
		 * if click inside a box - need a separate function for that, then add a parameter and a key for the game chosen, 
		 * or just start it from the mouse method
		 * 
		 * Use object list and get coords and check if within the coords - maybe use another method for that 
		 * or do that in the mouse click area, that might make more sense
		 */
		
	}
	public void checkItems(int x, int y)
	{
		/*
		 * but it changes when the user moves the window
		 */
		System.out.println("check Items running");
		for(GameCoords each:gamelistcoords)
		{
			int[] corners = each.getCorners();
			//System.out.println(Arrays.toString(corners));
			if(corners[0] < x && corners[2] > x)
			{
				if(corners[1] < y && corners[3] > y)
				{
					chooseGame(each.getName());
				}
			}
		}
	}
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouse click recognized");
		// TODO Auto-generated method stub
		//get coords and check object locations, go down master object list and check if it is within either of the two games, else, do nothing
		//selectX = (int) MouseInfo.getPointerInfo().getLocation().getX() - arg0.getX();
		//selectY = (int) MouseInfo.getPointerInfo().getLocation().getY() - arg0.getY();
		selectX = arg0.getX()+20;
		selectY = arg0.getY()+40;
		System.out.println(selectX+" "+selectY);
		checkItems(selectX, selectY);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String args[])
	{
		Arcade go = new Arcade();
	}
}