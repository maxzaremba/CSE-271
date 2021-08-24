import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SnekPanel extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	
	private Thread thread;
	private boolean running;
	private long lockOn;
	
	private Graphics2D g2d;
	private BufferedImage image;
	
	Snek head, food;
	ArrayList<Snek> snek;
	private int score;
	private int level;
	private boolean gameOver;
	private int SIZE = 10;
	
	private int dx, dy;
	private boolean up, down, left, right, start, exitgame;
	
	
	/**
	 * SnekPanel constructor
	 */
	public SnekPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		
	}
	
	/**
	 *Calls the thread constructor and starts it
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * @param fps
	 * @return
	 * Sets the level frame rates
	 */
	private int setFPS(int fps) {
		lockOn = 1000/fps;
		return 0;
		
	}
	
	/**
	 *Registers key pressed action events
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		
		if (k == KeyEvent.VK_UP) {
			up = true;
		} 
		
		if (k == KeyEvent.VK_DOWN) {
			down = true;
		}
		
		if (k == KeyEvent.VK_LEFT) {
			left = true;
		}
		
		if (k == KeyEvent.VK_RIGHT) {
			right = true;
		}
		
		if (k == KeyEvent.VK_ENTER) {
			start = true;
		}
		if (k == KeyEvent.VK_X) {
			exitgame = true;
		}
	}

	/**
	 *Registers key released action events
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		
		if (k == KeyEvent.VK_UP) {
			up = false;
		} 
		
		if (k == KeyEvent.VK_DOWN) {
			down = false;
		}
		
		if (k == KeyEvent.VK_LEFT) {
			left = false;
		}
		
		if (k == KeyEvent.VK_RIGHT) {
			right = false;
		}
		
		if (k == KeyEvent.VK_ENTER) {
			start = false;
		}
		if (k == KeyEvent.VK_X) {
			exitgame = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 *initializes an creates aspects of the game's runtime
	 */
	@Override
	public void run() {
		if (running) {
			return;
		}
		initialize();
		long startTime;
		long elapse;
		long hold;
		
		while(running) {
			startTime = System.nanoTime();
			
			update();
			requestRender();
			
			elapse = System.nanoTime() - startTime;
			hold = lockOn - elapse / 1000000;
			
			if (hold > 0) {
				try {
					Thread.sleep(hold);
				}
				catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * Restarts level if game over, updates the snake's position, checks for wall or food collision
	 */
	private void update() {
		if(gameOver) {
			if(start) {
				setLevel();
			}
			if(exitgame) {
				endGame();
				exitgame = false;
			}
			return;
		}
		if (up && dy == 0) {
			dy = -SIZE;
			dx = 0;
		}
		
		if (down && dy == 0) {
			dy = SIZE;
			dx = 0;
		}
		
		if (left && dx == 0) {
			dy = 0;
			dx = -SIZE;
		}
		
		if (right && dx == 0 && dy != 0 ) {
			dy = 0;
			dx = SIZE;
		}
		
		if (dx != 0 || dy != 0) {
			for (int i = snek.size() - 1; i > 0; i--) {
				snek.get(i).setPosition(snek.get(i - 1).getX(), snek.get(i - 1).getY());
			}
			head.move(dx, dy);
		}
		
		for(Snek s : snek) {
			if(s.isCollision(head)) {
				gameOver = true;
				break;
			}
		}
		
		if(food.isCollision(head)) {
			score++;
			setFood();
			
			Snek s = new Snek(SIZE);
			s.setPosition(-100, -100);
			snek.add(s);
			
			if(score % 10 == 0) {
				level++;
				if (level > 10) {
					level = 10;
				}
				setFPS(level * 10);
			}
		}
		
		if(head.getX() < 0) {
			head.setX(WIDTH);
		}
		
		if(head.getY() < 0) {
			head.setY(HEIGHT);
		}
		
		if(head.getX() > WIDTH) {
			head.setX(0);
		}
		
		if(head.getY() > HEIGHT) {
			head.setY(0);
		}
		
	}
	public void endGame()
	{
		
		JFrame frameplayagain = new JFrame();
		frameplayagain.setSize(700, 400);
		frameplayagain.pack();
		frameplayagain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameplayagain.setLocation(400, 400);
		frameplayagain.pack();
		//frameplayagain.setVisible(true);
		JOptionPane nextstep = new JOptionPane();
		int choice = 0;
		choice = JOptionPane.showConfirmDialog(frameplayagain,"\nWould you like to play again?", null, JOptionPane.YES_NO_OPTION);
		frameplayagain.setVisible(false);
		switch(choice)
		{
		case 0:
			return;
		case 1:
			setVisible(false);
			new Arcade();
			return;
		}
		//nextstep.setVisible(true);
		//playagain.addActionListener();
		
	}
	
	/**
	 * Sets the initial variables at the start of the level
	 */
	public void setLevel() {
		snek = new ArrayList<Snek>();
		head = new Snek(SIZE);
		head.setPosition(WIDTH / 2, HEIGHT / 2);
		snek.add(head);
		
		for(int i = 1; i < 3; i++) {
			Snek s = new Snek(SIZE);
			s.setPosition(head.getX() + (i * SIZE), head.getY());
			snek.add(s);
		}
		
		food = new Snek(SIZE);
		setFood();
		score = 0;
		gameOver = false;
		level = 1;
		dx = dy = 0;
		setFPS(level * 10);
	}
	
	/**
	 * Sets the random position of the food
	 */
	public void setFood() {
		int x = (int)(Math.random() * (WIDTH - SIZE));
		int y = (int)(Math.random() * (HEIGHT - SIZE));
		x = x - (x % SIZE);
		y = y - (y % SIZE);
		
		food.setPosition(x, y);
	}

	/**
	 * Helper method for render, calls render method and draws graphics
	 */
	private void requestRender() {
		render(g2d);
		Graphics graphics = getGraphics();
		graphics.drawImage(image, 0, 0, null);
	}
	
	/**
	 * @param g2d
	 * Draws the snake, food and the pop-up messages "Ready?" and "You Died"
	 */
	public void render(Graphics2D g2d) {
		g2d.clearRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.RED);
		g2d.setBackground(Color.ORANGE);
		for(Snek s : snek) {
			s.render(g2d);
		}
		
		g2d.setColor(Color.DARK_GRAY);
		food.render(g2d);
		
		if(gameOver) {
			g2d.drawString("You Died", 230, 250);
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawString("Score : " + score + "     Level : " + level, 200, 10);
		
		if (dx == 0 && dy == 0) {
			g2d.drawString("Use the arrow keys to move.", 180, 250);
		}
	}

	/**
	 * Initializes aspects of the level, such as game over, the current level and the frame rate
	 */
	private void initialize() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2d = image.createGraphics();
		running = true;
		setLevel();
		gameOver = false;
		level = 1;
		setFPS(level * 10);
	}

}
