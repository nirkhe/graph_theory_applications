import java.util.LinkedList;


public class Edge {

	public Node a, b;
	public int color;
	public String classname;
	
	public Edge(Node a, Node b, int color)
	{
		this.a = a; 
		this.b = b;
		this.color = color;
	}
	
	public LinkedList<EdgeSquare> connections = new LinkedList<EdgeSquare> ();
	
	
	public Edge(Node a, Node b) 
	{
		this(a, b, 0);
		
	}
	
	public boolean contains(Node n)
	{
		return a.equals(n) || b.equals(n);
	}
	
	public String toString()
	{
		return "Color = " + color + " between " + a.toString() + " and " + b.toString() + " with Class " + classname;
	}
	
	public Node getOther(Node n)
	{
		if (n.equals(a))
		{
			return b;
		}
		else if (n.equals(b))
		{
			return a;
		}
		else
			return null;
	}
	
}
