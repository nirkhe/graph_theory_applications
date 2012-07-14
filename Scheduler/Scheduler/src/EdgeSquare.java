
public class EdgeSquare 
{
	public Edge a, b;
	public boolean direction;
	
	public EdgeSquare(Edge a, Edge b, boolean direction)
	{
		this.a = a; 
		this.b = b;
		this.direction = direction;
	}
	
	public EdgeSquare(Edge a, Edge b) 
	{
		this(a, b, true);
		
	}
	
	public boolean contains(Node n)
	{
		return a.equals(n) || b.equals(n);
	}
	
	public String toString()
	{
		return "Direction " + (direction ? "forward" : "backward") + " between " + a.toString() + " and " + b.toString() ;
	}
	
	public Edge getOther(Edge n)
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
