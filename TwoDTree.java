import java.io.*;
import java.util.*;
public class TwoDTree
{
	private TreeNode root;
	private int size;
	
	private class TreeNode
	{
		TreeNode r,l;
		Point p;
		
		public TreeNode()
		{
			this(null,null,null);
		}
		
		public TreeNode(Point p)
		{
			this(null,null,p);
		}

		public TreeNode(TreeNode r,TreeNode l,Point p)
		{
			this.r = r;
			this.l = l;
			this.p = p;
		}
		
	}
	public TwoDTree() // construct an empty tree
	{
		this.root = null;
		this.size = 0;
	}
	
	public boolean isEmpty() // is the tree empty?
	{
		return root == null;
	}
	
	public int size() // number of points in the tree
	{
		return size;
	}
	
	public void insert(Point p) // inserts the point p to the tree
	{
		root = insert(root,p);
	}
	
	private TreeNode insert(TreeNode t,Point p)
	{
		if(t == null)
		{
			t = new TreeNode(p);
			size++;
			return t;
		}
		if(t.p.x() <= p.x())
		{
			TreeNode next = t.r;
			if(next == null)
			{
				t.r = new TreeNode(p);
				size++;
			}				
			else if(next.p.y() <= p.y())
			{
				t.r.r = insert(next.r,p);
			}
			else
				t.r.l = insert(next.l,p);
		}
		else if(t.p.x() > p.x())
		{
			TreeNode next = t.l;
			if(next == null)
			{
				t.l = new TreeNode(p);
				size++;
			}
			else if(next.p.y() <= p.y())
			{
				t.l.r = insert(next.r,p);
			}
			else
				t.l.l = insert(next.l,p);
		}		
		return t;
	}	
	
	public boolean search(Point p) // does the tree contain p?
	{		
		return search(p,root);
	 }
	 
	private boolean search(Point p,TreeNode t)
	{
		if(t == null)
		{
			return false;
		}
		if(t.p.x() == p.x() && t.p.y() == p.y())  
		{
			return true; 
		}
		if(t.p.x() < p.x())
		{
			TreeNode next = t.r;
			if(next == null)
			{
				return false;
			}
			else if(next.p.x() == p.x() && next.p.y() == p.y())    
			{
				return true; 
			}
			else if(next.p.y() < p.y())
			{
				return search(p,next.r);
			}
			else
				return search(p,next.l);
		}
		else if(t.p.x() > p.x())
		{
			TreeNode next = t.l;
			if(next == null)
			{
				return false;
			}
			else if(next.p.x() == p.x() && next.p.y() == p.y()) 
			{
				return true; 
			}
			else if(next.p.y() < p.y())
			{
				return search(p,next.r);
			}
			else
				return search(p,next.l);
		}
		return false;
	}
	 
	public Point nearestNeighbor(Point p) // point in the tree that is closest to p
	//(null if tree is empty)
	{
		return nearestNeighbor(new Rectangle(0,0,100,100),root,p,root.p);
	}
	
	/* Arguments:
	* rect_level -> is the rectangle of the currenct level.
	* t -> is the TreeNode we are currently working on.
	* p -> is the query Point.
	* min -> is the Point with the minimmum distance from p.
	* 
	* Return value: The Point with the minimum distance from the query Point.
	* 
	* On each recursive call we check the current TreeNode, having as rect_level
	* the rectangle that contains its point.Then we calculate the rectangle
	* that contains the left child acording to the x coordinates of its father
	* and after we perform a number of checks we call two recursions for the left and right
	* subtrees of the child giving as rect_level the correct rectangle acording to the y
	* coordinates updating the min when needed.
	* We repeat the same proccess for the right child of the current TreeNode.
	* 
	* Note:The recuresion starts with the rectangle [0,100]x[0,100] and t = min = the root of the tree.
	* 
	* */
	private Point nearestNeighbor(Rectangle rect_level,TreeNode t,Point p,Point min)
	{
		//end recursion
		if(t == null)return min;
		
		//distance from p to the rectangle
		int dist_rec = rect_level.squareDistanceTo(p);
		//distance to between p and min
		int dist_min = min.squareDistanceTo(p);
		if(dist_rec > dist_min)return min;
		
		min = p.squareDistanceTo(t.p) < p.squareDistanceTo(min) ? t.p : min;
		//rectangles for each subtree/level
		Rectangle rect_right_x,rect_left_x,rect_right_y,rect_left_y;
		
		TreeNode child = t.l;//child node is used to make the code more readable
		if(child != null)//left subtree
		{
			
			//calculate the new rectangle for the left subtree
			rect_left_x = new Rectangle(rect_level.xmin(),rect_level.ymin(),t.p.x(),rect_level.ymax());
			
			//distance from p to the new rectangle
			dist_rec = rect_level.squareDistanceTo(p);
			//distance to between p and min
			dist_min = min.squareDistanceTo(p);
			
			if(dist_rec > dist_min)return min;
			
			//compare min and child.p
			min = p.squareDistanceTo(child.p) < p.squareDistanceTo(min) ? child.p : min;
			
			//now go recursively and check the left and right subtrees
			rect_left_y = new Rectangle(rect_left_x.xmin(),rect_left_x.ymin(),rect_left_x.xmax(),child.p.y());
			min = nearestNeighbor(rect_left_y,child.l,p,min);
			
			rect_right_y = new Rectangle(rect_left_x.xmin(),child.p.y(),rect_left_x.xmax(),rect_left_x.ymax());
			min = nearestNeighbor(rect_right_y,child.r,p,min);
		}	
		
		
		child = t.r;
		if(child != null)//right subtree
		{
			
			//calculate the new rectangle for the right subtree
			rect_right_x = new Rectangle(t.p.x(),rect_level.ymin(),rect_level.xmax(),rect_level.ymax());
			
			//distance from p to the new rectangle
			dist_rec = rect_level.squareDistanceTo(p);
				
			//distance between p and min
			dist_min = min.squareDistanceTo(p);
			
			if(dist_rec > dist_min)return min;
				
			//compare min and child.p
			min = p.squareDistanceTo(child.p) < p.squareDistanceTo(min) ? child.p : min;
			
			//now go recursively and check the left and right subtrees
			rect_left_y = new Rectangle(rect_right_x.xmin(),rect_right_x.ymin(),rect_right_x.xmax(),child.p.y());
			min =  nearestNeighbor(rect_level,child.l,p,min);
			
			rect_right_y = new Rectangle(rect_right_x.xmin(),child.p.y(),rect_right_x.xmax(),rect_right_x.ymax());
			min =  nearestNeighbor(rect_right_y,child.r,p,min);
		}
		
		return min;
	}
	public LinkedList<Point> rangeSearch(Rectangle rect) // Returns a list
	{// with the Points that are contained in the rectangle
		LinkedList<Point> list = new LinkedList<Point>();
		rangeSearch(rect,new Rectangle(0,0,100,100),root,list);
		return list;
	}
	
	/* Arguments:
	* rect_level -> is the rectangle of the currenct level.
	* t -> is the TreeNode we are currently working on.
	* rect_input -> is the query rectangle.
	* list -> is the LinkedList that contains all the points inside the range of the rect_input.
	* 
	* Basicaly the same principle with nearestNeighbor() but we performe different kinds of
	* checks.
	* On each recursive call we check the current TreeNode, having as rect_level
	* the rectangle that contains its point.Then we calculate the rectangle
	* that contains the left child acording to the x coordinates of its father
	* and after we perform a number of checks we call two recursions for the left and right
	* subtrees of the child giving as rect_level the correct rectangle acording to the y
	* coordinates updating the list when needed.
	* We repeat the same proccess for the right child of the current TreeNode.
	* 
	* Note:The recuresion starts with the rectangle [0,100]x[0,100] and t = the root of the tree.
	* 
	* */
	private void rangeSearch(Rectangle rect_input,Rectangle rect_level,TreeNode t,LinkedList<Point> list)
	{
		//end the recursion when a leaf is reached
		if(t == null)return;
		
		//we do not have to continue if the two rectangles do not intersect
		if(!rect_level.intersects(rect_input))return;
		
		//rectangles for the left and right subtree based on the x and y coordinates
		Rectangle rect_right_x,rect_left_x,rect_right_y,rect_left_y;
		
		//add the point if it is in the rectangle
		if(rect_input.contains(t.p))list.addToFront(t.p);
		
		rect_left_x = new Rectangle(rect_level.xmin(),rect_level.ymin(),t.p.x(),rect_level.ymax());
		//check if we must continue to the left subtree
		if(rect_left_x.intersects(rect_input) && t.l != null)
		{
			TreeNode child = t.l;
			
			rect_left_y = new Rectangle(rect_left_x.xmin(),rect_left_x.ymin(),rect_left_x.xmax(),child.p.y());
			rect_right_y = new Rectangle(rect_left_x.xmin(),child.p.y(),rect_left_x.xmax(),rect_left_x.ymax());
			
			//add the point if it is in the rectangle
			if(rect_input.contains(child.p))list.addToFront(child.p);
			//search recursively the left and right subtree
			rangeSearch(rect_input,rect_left_y,child.l,list);
			rangeSearch(rect_input,rect_right_y,child.r,list);
		}
		
		rect_right_x = new Rectangle(t.p.x(),rect_level.ymin(),rect_level.xmax(),rect_level.ymax());
		//check if we must continue to the right subtree
		if(rect_right_x.intersects(rect_input) && t.r != null)
		{
			TreeNode child = t.r;
			
			rect_left_y = new Rectangle(rect_right_x.xmin(),rect_right_x.ymin(),rect_right_x.xmax(),child.p.y());
			rect_right_y = new Rectangle(rect_right_x.xmin(),child.p.y(),rect_right_x.xmax(),rect_right_x.ymax());
			
			//add the point if it is in the rectangle
			if(rect_input.contains(child.p))list.addToFront(child.p);
			//search recursively the left and right subtree
			rangeSearch(rect_input,rect_right_y,child.r,list);
			rangeSearch(rect_input,rect_left_y,child.l,list);
		}
		
	}
	public String toString()
	{
		LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
		String out = "";
		queue.addToBack(root);
		while(!queue.isEmpty())
		{
			TreeNode current = queue.removeFromFront();
			out += current.p + " ";
			if(current.l != null)queue.addToBack(current.l);;
			if(current.r != null) queue.addToBack(current.r);;
		}
		return out;
    }    
	
	public static void main(String[] args)
	{
		TwoDTree tree = readFile("test.txt");
		System.out.println("The file was read successfully");
		int user=0;
		String us = "";
		while(true)
		{
			System.out.println("    Menu    ");
			System.out.println("Press 1 if you want to give a query rectangle.");
			System.out.println("Press 2 if you want to give a query point.");
			System.out.println("Press 3 if you want to exit the program.");
			System.out.print("Choice: ");
			us = System.console().readLine();
			if(isInteger(us))
			{
				user = Integer.parseInt(us);
				if(user == 3)
				{
					System.out.println("You have just exited the program!");
					break;
				}
				else if(user == 1)
				{
					int x1,x2,y1,y2;
					String ax1,ax2,ay1,ay2;
					System.out.print("Give the minimum x coordinate: ");
					ax1 = System.console().readLine();
					System.out.print("Give the minimum y coordinate: ");
					ay1 = System.console().readLine();
					System.out.print("Give the maximum x coordinate: ");
					ax2 = System.console().readLine();
					System.out.print("Give the maximum y coordinate: ");
					ay2 = System.console().readLine();
					if(isInteger(ax1) && isInteger(ax2) && isInteger(ay1) && isInteger(ay2))
					{
						x1 = Integer.parseInt(ax1);
						x2 = Integer.parseInt(ax2);
						y1 = Integer.parseInt(ay1);
						y2 = Integer.parseInt(ay2);
						Rectangle r = new Rectangle(x1,y1,x2,y2);
						System.out.println(tree.rangeSearch(r));
					}
					else
						System.out.println("Please give right coordinates!");
				}
				else if(user == 2)
				{
					int x,y;
					String ax,ay;
					System.out.print("Give x coordinate: ");
					ax = System.console().readLine();
					System.out.print("Give y coordinate: ");
					ay = System.console().readLine();
					if(isInteger(ax) && isInteger(ay))
					{
						x = Integer.parseInt(ax);
						y = Integer.parseInt(ay);
						Point p = new Point(x,y);
						System.out.println(tree.nearestNeighbor(p));
					}
					else
						System.out.println("Please give right coordinates!");
				}
				else
					System.out.println("Please choose an option from the menu!");
			}
			else
				System.out.println("Please give a number!");
	}
		
	}
	
	
	public static boolean isInteger( String input )  //Checking if the input is Integer
	{  
	   try{  
	      Integer.parseInt( input );  
	      return true;  
	   }catch( Exception e ) {  
	      return false;  
	   }  
	}
	
	public static TwoDTree readFile(String data)
	{
		int counter = 0;//helper variable for exceptions
		//also helps to count the number of lines of the file
			
		File f = null;
		BufferedReader reader = null;
		String line;
		TwoDTree tree = new TwoDTree();
		try {
			f = new File(data);//creating the file
		}catch (NullPointerException e) {
			System.err.println("File not found");
		}
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(f)));
		}catch (Exception e) {
			System.err.println("Error opening file!");
			e.printStackTrace();
		}
		try{
			int N=0; // total number of lines
			line=reader.readLine();//reading the 1st line
			counter ++;
			String[] temp = line.split(" ");
			if(isInteger(temp[0]))
			{			
				N = Integer.parseInt(temp[0]);
				if(N <= 0)
				{
					reader.close();
					throw new IllegalArgumentException("Your input is wrong");
				}
			}
			else 
			{
				reader.close();
				throw new IllegalArgumentException("Your input is wrong");
			}
			line=reader.readLine();//reading the 1st line
			int cc = 0; // helper variable to check the number of lines
			while(line!=null)
			{		
				counter ++;
				temp = line.split(" ");
				if(temp.length != 2)
				{
					reader.close();
					throw new IllegalArgumentException("The line must have two integers");
				}
				if(isInteger(temp[0]) && isInteger(temp[1]))//checking if the values are integers
				{
					int x = Integer.parseInt(temp[0]);
					int y = Integer.parseInt(temp[1]);
					Point p = new Point(x,y);
					if(tree.search(p))
					{
						reader.close();
						throw new IllegalArgumentException("The point is already!");
					}
					tree.insert(p);
					}
					else
					{
						reader.close();
						throw new IllegalArgumentException("You have to give right inputs.Check again.");
					}
					if(counter > N + 1)
					{
						reader.close();
						throw new IllegalArgumentException("Your input is wrong");
					}
					line=reader.readLine();//reading the 1st line						
				}
				if(counter <= N)
				{
					reader.close();
						throw new IllegalArgumentException("Your input is wrong");
				}
				
			}
			catch(IOException e){
			System.err.println("Error reading line" + counter + ".");
			}
			try{
				reader.close();
			}
			catch(IOException e1){
				System.out.println("Error closing file.");
			}
			return tree;
		
	}
}