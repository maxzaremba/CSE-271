import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Snek {
	
	private int x, y, size;
	
	/**
	 * @param size
	 * Sets the size of the snake
	 */
	public Snek(int size) {
		this.size  = size;
	}
	
	/**
	 * @return
	 * returns the x position of the snake
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return
	 * returns the y position of the snake
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @param x
	 * Sets the x position of the snake
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * @param y
	 * sets the y position of the snake
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @param x
	 * @param y
	 * Sets the x and y position of the snake
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param dx
	 * @param dy
	 * Sets the dx and dy positions of the snake
	 */
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	/**
	 * @return
	 * Returns the bounds of a snake body rectangle
	 */
	public Rectangle getBound() {
		return new Rectangle(x, y, size, size);
	}
	
	/**
	 * @param x
	 * @return
	 * Checks for collision
	 */
	public boolean isCollision(Snek x) {
		if (x == this) {
			return false;
		}
		return getBound().intersects(x.getBound());
	}
	
	/**
	 * @param g2d
	 * Creates rectangles for the snake's body
	 */
	public void render(Graphics2D g2d) {
		// Make clean line instead?
		g2d.fillRect(x + 1, y + 1, size - 2, size - 2);
		
	}

}
