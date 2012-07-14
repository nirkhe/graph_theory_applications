import java.util.LinkedList;


public class Node 
{

	public LinkedList<Edge> neighbors;
	public Node.Classification classification;
	public String name;
	public LinkedList<String> classes;
	
	public Node(Node.Classification classification, String name)
	{
		this(classification, name, new LinkedList<String> ());
	}
	
	public Node(Node.Classification classification, String name, LinkedList<String> classes)
	{
		this(new LinkedList<Edge> (), classification, name, classes);
	}
	
	public Node(LinkedList<Edge> neighbors, Node.Classification classification, String name, LinkedList<String> classes)
	{
		this.neighbors = neighbors;
		this.classification = classification;
		this.name = name;
		this.classes = classes;
	}
	
	public static enum Classification
	{
		TEACHER, STUDENT;
	}
	
	public void add(Node n, int color)
	{
		if( n.classification != this.classification)
		{
			Edge e = new Edge(this, n, color);
			this.neighbors.add(e);
			n.neighbors.add(e);
		}
	}
		
	public boolean equals(Object obj)
	{
		if (obj.getClass() == this.getClass())
		{
			Node oth = (Node)obj;
			return oth.name.equals(this.name);
		}
		return false;
	}
	
	public Edge getEdge(Node n)
	{
		for(Edge e : this.neighbors)
		{
			if(e.contains(n))
			{
				return e;
			}
		}
		return null;
	}
	
	public String toString()
	{
		return name;
	}
	
	public String getCourse(int color)
	{
		for(Edge e: neighbors)
		{
			if (e.color == color)
			{
				return e.classname;
			}
		}
		return "";
	}
	
	public Edge getEdge(int color)
	{
		for(Edge e: neighbors)
		{
			if (e.color == color)
			{
				return e;
			}
		}
		return null;
	}
	
}
