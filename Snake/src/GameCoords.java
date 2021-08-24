
public class GameCoords {
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	public String name = "";
	public GameCoords()
	{
		
	}
	public GameCoords(int x, int y, int w, int h, String name)
	{
		//create object with certain coords and then have methods to return those coordinates
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public int[] getCorners()
	{
		int[] corners = new int[4];
		corners[0] = x;
		corners[1] = y;
		corners[2] = x+w;
		corners[3] = y+h;
		return corners;
	}
}
