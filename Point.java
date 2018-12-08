
public class Point
{
	private int x,y;
	public Point(int x,int y)// construct the point (x, y)
	{
		if( x >= 0 && x <= 100 && y >= 0 && y <= 100)
		{
			this.x = x;
			this.y = y;
		}
		else
			throw new IndexOutOfBoundsException();
	}
	
	public int x()// return the x-coordinate
	{
		return x;
	}
	
	public int y()// return the y-coordinate
	{
		return y;
	}
	
	public double distanceTo(Point z)// Euclidean distance
	//between two points
	{
		return Math.sqrt((this.x - z.x)*(this.x - z.x) + (this.y - z.y) * (this.y - z.y));
	}
	
	public int squareDistanceTo(Point z)// square of the Euclidean
	//distance between two points
	{
		return (this.x - z.x)*(this.x - z.x) + (this.y - z.y) * (this.y - z.y);
	}
	
	public String toString()// string representation: (x, y)
	{
		return ("( " + x() + "," + y() + " )");
	}
}
