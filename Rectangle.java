public class Rectangle 
{
	
	private int xmin,ymin,xmax,ymax;
	
	public Rectangle(int xmin, int ymin, int xmax, int ymax)// construct the rectangle [xmin, ymin] x [xmax, ymax]
	{
		if (xmin >= 0 && xmax <= 100 && xmin <= xmax && ymin >= 0 && ymax <= 100 && ymin <= ymax)
		{
			this.xmin = xmin;
			this.ymin = ymin;
			this.xmax = xmax;
			this.ymax = ymax;
		}
		else
			throw new IndexOutOfBoundsException();
	}
	
	public int xmin()// minimum x-coordinate of rectangle
	{
		return xmin;
	}
	
	public int ymin()// minimum y-coordinate of rectangle
	{
		return ymin;
	}
	
	public int xmax()// maximum x-coordinate of rectangle
	{
		return xmax;
	}
	
	public int ymax()// maximum y-coordinate of rectangle
	{
		return ymax;
	}
	
	public boolean contains(Point p)//does p belong to the rectangle?
	{
		return (p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax);
	}
	
	public boolean intersects(Rectangle that)// do the two rectangles
	// intersect?
	{
		return (that.xmin <= xmax/*xmin*/ && that.xmax >= xmin && that.ymin <= ymax/*ymin*/ && that.ymax >= ymin);
	}
	
	private double distance(int a,int b,int c,Point p)// Euclidean distance from p
	//to closest point in rectangle
	{
		return Math.abs(a*p.x() + b*p.y() + c)/(double)Math.sqrt(a*a + b*b);
	}
	
	public double distanceTo(Point p)// square of Euclidean
	// distance from p to closest point in rectangle
	{
		double d1 = distance(1,0,-xmin,p); // distance from side
		double d2 = distance(1,0,-xmax,p); // distance from side
		double d3 = distance(0,1,-ymin,p); // distance from side
		double d4 = distance(0,1,-ymax,p); // distance from side
		
		Point p1 = new Point(xmin,ymin); // down left
		Point p2 = new Point(xmax,ymin); // down right 
		Point p3 = new Point(xmin,ymax); // upper left
		Point p4 = new Point(xmax,ymax); // upper right
	
		int d5 =	p.squareDistanceTo(p1); // distance from corner
		int d6 = p.squareDistanceTo(p2); // distance from corner
		int d7 = p.squareDistanceTo(p3); // distance from corner
		int d8 = p.squareDistanceTo(p4); // distance from corner

		double distance = 0 ;
		
		if(contains(p)) // E
		{
			return 0.0;
		}
		else if(p.x() < xmin && p.y() >= ymax) //A
		{
			distance = d7;
		}
		else if(p.x() >= xmin && p.x() <= xmax && p.y() > ymax ) //B
		{
			distance = d4;
		}
		else if(p.x() > xmax && p.y() >= ymax) //C
		{
			distance = d8;
		}			
		else if(p.x() < xmin && p.y() >= ymin && p.y() <= ymax) //D
		{
			distance = d1;
		}	
		else if(p.x() > xmax && p.y() >= ymin && p.y() <= ymax) //F
		{
			distance = d2;
		}
		else if(p.x() <= xmin && p.y() <= ymin) //G
		{
			distance = d5;
		}
		else if(p.x() >= xmin && p.x() <= xmax && p.y() < ymin) //H
		{
			distance = d3;
		}
		else if (p.x() >= xmin && p.y() < ymin) //I
		{
			distance = d6;
		}
		
		return distance;
	}
	public int squareDistanceTo(Point p)// string representation:
	// [xmin, xmax] x [ymin, ymax]
	{
		int temp = (int)distanceTo(p);
		return temp*temp;
	}
	
	public String toString()
	{
		return ("[ " + xmin + "," + xmax + " ]" + " x " + "[ " + ymin + "," + ymax + " ]");
	}
		
}
